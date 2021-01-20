package app.interview.service;

import app.api.interviewee.BOSearchIntervieweeRequest;
import app.api.interviewee.BOSearchIntervieweeResponse;
import app.api.interviewee.BOUpdateIntervieweeRequest;
import app.api.interviewee.CreateIntervieweeRequest;
import app.api.interviewee.CreateIntervieweeResponse;
import app.api.interviewee.GetIntervieweeResponse;
import app.api.interviewee.GetResumeResponse;
import app.api.interviewee.InterviewStatusView;
import app.api.interviewee.IntervieweeLoginRequest;
import app.api.interviewee.IntervieweeLoginResponse;
import app.api.interviewee.SaveResumeRequest;
import app.api.interviewee.SaveResumeResponse;
import app.api.interviewee.resume.JobExperienceView;
import app.api.interviewee.resume.ProjectExperienceView;
import app.api.notification.interview.kafka.IntervieweeNotificationMessage;
import app.common.util.PasswordUtils;
import app.interview.domain.InterviewStatus;
import app.interview.domain.Interviewee;
import app.interview.domain.resume.JobExperience;
import app.interview.domain.resume.PersonalInfo;
import app.interview.domain.resume.ProjectExperience;
import app.interview.domain.resume.Resume;
import com.mongodb.ReadPreference;
import com.mongodb.client.model.Filters;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;
import core.framework.mongo.FindOne;
import core.framework.mongo.MongoCollection;
import core.framework.util.Strings;
import core.framework.web.exception.BadRequestException;
import core.framework.web.exception.NotFoundException;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class IntervieweeService {
    private final Logger logger = LoggerFactory.getLogger(IntervieweeService.class);
    @Inject
    Repository<Interviewee> intervieweeRepository;
    @Inject
    MongoCollection<Resume> resumeMongoCollection;
    @Inject
    MessagePublisher<IntervieweeNotificationMessage> publisher;

    public CreateIntervieweeResponse register(CreateIntervieweeRequest request) {
        long existingSameMobilePhone = intervieweeRepository.count("mobile_phone = ?", request.mobilePhone);
        if (existingSameMobilePhone > 0) {
            throw new BadRequestException("Duplicated mobile phone number existed");
        }
        Interviewee interviewee = new Interviewee();
        interviewee.mobilePhone = request.mobilePhone;
        interviewee.passwordSalt = PasswordUtils.getSalt();
        interviewee.password = PasswordUtils.generateSecurePassword(request.password, interviewee.passwordSalt);
        interviewee.jobPosition = request.jobPosition;
        interviewee.status = InterviewStatus.UNASSIGNED;
        interviewee.createdTime = LocalDateTime.now();
        interviewee.updatedTime = LocalDateTime.now();
        CreateIntervieweeResponse response = new CreateIntervieweeResponse();
        response.intervieweeId = intervieweeRepository.insert(interviewee).orElseThrow();
        response.status = InterviewStatusView.valueOf(interviewee.status.name());
        return response;
    }

    public BOSearchIntervieweeResponse search(BOSearchIntervieweeRequest request) {
        Query<Interviewee> query = intervieweeRepository.select();
        query.orderBy("updated_time DESC");
        query.skip(request.skip);
        query.limit(request.limit);
        if (!Strings.isBlank(request.mobilePhone)) {
            query.where("mobile_phone LIKE ?", request.mobilePhone + "%");
        }
        if (request.status != null) {
            query.where("status = ?", request.status.name());
        }
        BOSearchIntervieweeResponse result = new BOSearchIntervieweeResponse();
        result.interviewees = query.fetch().stream().map(this::recordToView).collect(Collectors.toList());
        result.total = query.count();
        return result;
    }

    private BOSearchIntervieweeResponse.IntervieweeView recordToView(Interviewee interviewee) {
        var view = new BOSearchIntervieweeResponse.IntervieweeView();
        view.id = interviewee.id;
        view.mobilePhone = interviewee.mobilePhone;
        view.jobPosition = interviewee.jobPosition;
        view.status = InterviewStatusView.valueOf(interviewee.status.name());
        return view;
    }

    public GetIntervieweeResponse get(Long id) {
        Interviewee interviewee = getInterviewee(id);
        GetIntervieweeResponse response = new GetIntervieweeResponse();
        response.id = interviewee.id;
        response.mobilePhone = interviewee.mobilePhone;
        response.password = interviewee.password;
        response.passwordSalt = interviewee.passwordSalt;
        response.jobPosition = interviewee.jobPosition;
        response.status = InterviewStatusView.valueOf(interviewee.status.name());
        return response;
    }

    public Interviewee getInterviewee(Long id) {
        return intervieweeRepository.get(id).orElseThrow(() -> new NotFoundException("Interviewee not found by id = " + id));
    }

    public void update(Long id, BOUpdateIntervieweeRequest request) {
        Interviewee interviewee = getInterviewee(id);
        interviewee.status = InterviewStatus.valueOf(request.status.name());
        interviewee.updatedTime = LocalDateTime.now();
        intervieweeRepository.update(interviewee);
        notifyInterviewee(interviewee);
    }

    private void notifyInterviewee(Interviewee interviewee) {
        if (interviewee.status != InterviewStatus.PASSED) {
            return;
        }
        IntervieweeNotificationMessage message = new IntervieweeNotificationMessage();
        message.id = interviewee.id;
        message.mobilePhone = interviewee.mobilePhone;
        message.message = "Congrats you! You have passed our interview!";
        publisher.publish("Interviewee - " + message.id, message);
    }

    public void updateInterviewee(Interviewee interviewee) {
        if (interviewee == null) return;
        intervieweeRepository.update(interviewee);
    }

    public SaveResumeResponse saveResume(Long id, SaveResumeRequest request) {
        Interviewee interviewee = getInterviewee(id);
        Bson filter = Filters.eq("interviewee_id", interviewee.id);
        Resume resume = requestToResume(request);
        resume.intervieweeId = id;
        Optional<Resume> existingResume = resumeMongoCollection.findOne(filter);
        if (existingResume.isPresent()) {
            logger.warn("Duplicated resume request to updated");
            resume.id = existingResume.get().id;
            resumeMongoCollection.replace(resume);
        } else {
            resumeMongoCollection.insert(resume);
        }
        existingResume = resumeMongoCollection.findOne(filter);
        return resumeToSavedResponse(id, existingResume.orElseThrow().id.toString());
    }

    private SaveResumeResponse resumeToSavedResponse(Long id, String resumeId) {
        SaveResumeResponse existingResumeResponse = new SaveResumeResponse();
        existingResumeResponse.intervieweeId = id;
        existingResumeResponse.resumeId = resumeId;
        return existingResumeResponse;
    }

    private Resume requestToResume(SaveResumeRequest request) {
        Resume resume = new Resume();
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.firstName = request.firstName;
        personalInfo.lastName = request.lastName;
        personalInfo.dob = request.dob.atStartOfDay();
        resume.personalInfo = personalInfo;
        Optional.ofNullable(request.jobExperiences).ifPresent(jobExperienceList -> resume.jobExperiences = jobExperienceList.stream().map(exp -> {
            JobExperience experience = new JobExperience();
            experience.companyName = exp.companyName;
            experience.position = exp.position;
            experience.description = exp.description;
            experience.startedDate = exp.startedDate;
            experience.endDate = exp.endDate;
            return experience;
        }).collect(Collectors.toList()));
        Optional.ofNullable(request.projectExperiences).ifPresent(projectExperienceList -> resume.projectExperiences = projectExperienceList.stream().map(exp -> {
            ProjectExperience experience = new ProjectExperience();
            experience.name = exp.name;
            experience.description = exp.description;
            return experience;
        }).collect(Collectors.toList()));
        return resume;
    }

    public GetResumeResponse getResume(Long id) {
        Interviewee interviewee = getInterviewee(id);
        FindOne findById = new FindOne();
        findById.filter = Filters.eq("interviewee_id", interviewee.id);
        findById.readPreference = ReadPreference.secondary();
        Optional<Resume> resume = resumeMongoCollection.findOne(findById);
        if (resume.isEmpty()) {
            logger.warn("No resume found for requested interviewee - {}", id);
            throw new NotFoundException("No resume found for requested interviewee");
        }

        GetResumeResponse response = resumeToGetResponse(id, resume.get());
        response.mobilePhone = interviewee.mobilePhone;
        response.jobPosition = interviewee.jobPosition;
        return response;
    }

    private GetResumeResponse resumeToGetResponse(Long id, Resume resume) {
        GetResumeResponse response = new GetResumeResponse();
        response.intervieweeId = id;
        response.resumeId = resume.id.toString();
        Optional.ofNullable(resume.personalInfo).ifPresent(info -> {
            response.firstName = info.firstName;
            response.lastName = info.lastName;
            response.dob = info.dob.toLocalDate();
        });
        Optional.ofNullable(resume.jobExperiences).ifPresent(jobExperienceList -> response.jobExperiences = jobExperienceList.stream().map(exp -> {
            JobExperienceView experience = new JobExperienceView();
            experience.companyName = exp.companyName;
            experience.position = exp.position;
            experience.description = exp.description;
            experience.startedDate = exp.startedDate;
            experience.endDate = exp.endDate;
            return experience;
        }).collect(Collectors.toList()));
        Optional.ofNullable(resume.projectExperiences).ifPresent(projectExperienceList -> response.projectExperiences = projectExperienceList.stream().map(exp -> {
            ProjectExperienceView experience = new ProjectExperienceView();
            experience.name = exp.name;
            experience.description = exp.description;
            return experience;
        }).collect(Collectors.toList()));
        return response;
    }

    public IntervieweeLoginResponse login(IntervieweeLoginRequest request) {
        return intervieweeRepository.selectOne("mobile_phone = ?", request.mobilePhone).map(interviewee -> {
            IntervieweeLoginResponse response = new IntervieweeLoginResponse();
            if (!PasswordUtils.verifyPassword(request.password, interviewee.password, interviewee.passwordSalt)) {
                response.success = Boolean.FALSE;
                response.errorMessage = "Incorrect password";
                return response;
            }
            response.success = Boolean.TRUE;
            response.intervieweeId = interviewee.id;
            return response;
        }).orElseGet(() -> {
            IntervieweeLoginResponse response = new IntervieweeLoginResponse();
            response.success = Boolean.FALSE;
            response.errorMessage = "Incorrect mobile phone";
            return response;
        });
    }
}
