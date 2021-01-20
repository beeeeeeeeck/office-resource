package app.web;

import app.api.InterviewAJAXWebService;
import app.api.interview.CreateIntervieweeResumeAJAXRequest;
import app.api.interview.CreateIntervieweeResumeAJAXResponse;
import app.api.interview.GetIntervieweeResumeAJAXResponse;
import app.api.interview.IntervieweeLoginAJAXRequest;
import app.api.interview.IntervieweeLoginAJAXResponse;
import app.api.interview.RegisterIntervieweeAJAXRequest;
import app.api.interview.RegisterIntervieweeAJAXResponse;
import app.api.interview.resume.JobExperienceAJAXView;
import app.api.interview.resume.ProjectExperienceAJAXView;
import app.api.interviewee.CreateIntervieweeRequest;
import app.api.interviewee.CreateIntervieweeResponse;
import app.api.interviewee.GetResumeResponse;
import app.api.interviewee.IntervieweeLoginRequest;
import app.api.interviewee.IntervieweeLoginResponse;
import app.api.interviewee.SaveResumeRequest;
import app.api.interviewee.SaveResumeResponse;
import app.api.interviewee.resume.JobExperienceView;
import app.api.interviewee.resume.ProjectExperienceView;
import app.common.web.interceptor.LoginRequired;
import app.exception.ResourceNotAccessibleException;
import app.service.InterviewService;
import app.service.SessionService;
import core.framework.inject.Inject;
import core.framework.web.WebContext;

import java.util.Optional;
import java.util.stream.Collectors;

import static app.service.SessionService.SESSION_LOGIN_INTERVIEWEE_ID_KEY;

/**
 * @author beckl
 */
public class InterviewAJAXWebServiceImpl implements InterviewAJAXWebService {
    @Inject
    InterviewService interviewService;
    @Inject
    SessionService sessionService;
    @Inject
    WebContext webContext;

    @Override
    public RegisterIntervieweeAJAXResponse registerAsInterviewee(RegisterIntervieweeAJAXRequest request) {
        CreateIntervieweeRequest createIntervieweeRequest = new CreateIntervieweeRequest();
        createIntervieweeRequest.mobilePhone = request.mobilePhone;
        createIntervieweeRequest.password = request.password;
        createIntervieweeRequest.jobPosition = request.jobPosition;
        CreateIntervieweeResponse createIntervieweeResponse = interviewService.registerAsInterviewee(createIntervieweeRequest);
        webContext.request().session().set(SESSION_LOGIN_INTERVIEWEE_ID_KEY, String.valueOf(createIntervieweeResponse.intervieweeId));
        RegisterIntervieweeAJAXResponse response = new RegisterIntervieweeAJAXResponse();
        response.intervieweeId = createIntervieweeResponse.intervieweeId;
        return response;
    }

    @LoginRequired(SESSION_LOGIN_INTERVIEWEE_ID_KEY)
    @Override
    public CreateIntervieweeResumeAJAXResponse createIntervieweeResume(CreateIntervieweeResumeAJAXRequest request) {
        String intervieweeIdValue = sessionService.getLoginIntervieweeId().orElseThrow(ResourceNotAccessibleException::new);
        SaveResumeRequest saveResumeRequest = convert2SaveResumeRequest(request);
        SaveResumeResponse saveResumeResponse = interviewService.createIntervieweeResume(Long.valueOf(intervieweeIdValue), saveResumeRequest);
        CreateIntervieweeResumeAJAXResponse response = new CreateIntervieweeResumeAJAXResponse();
        response.resumeId = saveResumeResponse.resumeId;
        response.intervieweeId = saveResumeResponse.intervieweeId;
        return response;
    }

    @LoginRequired(SESSION_LOGIN_INTERVIEWEE_ID_KEY)
    @Override
    public GetIntervieweeResumeAJAXResponse getIntervieweeResume() {
        String intervieweeIdValue = sessionService.getLoginIntervieweeId().orElseThrow(ResourceNotAccessibleException::new);
        GetResumeResponse getResumeResponse = interviewService.getResume(Long.valueOf(intervieweeIdValue));
        GetIntervieweeResumeAJAXResponse response = new GetIntervieweeResumeAJAXResponse();
        response.firstName = getResumeResponse.firstName;
        response.lastName = getResumeResponse.lastName;
        response.jobPosition = getResumeResponse.jobPosition;
        Optional.ofNullable(getResumeResponse.jobExperiences).ifPresent(jobExperiences -> response.jobExperiences = jobExperiences.stream().map(exp -> {
            JobExperienceAJAXView jobExp = new JobExperienceAJAXView();
            jobExp.companyName = exp.companyName;
            jobExp.position = exp.position;
            jobExp.endDate = exp.endDate;
            jobExp.startedDate = exp.startedDate;
            jobExp.description = exp.description;
            return jobExp;
        }).collect(Collectors.toList()));
        Optional.ofNullable(getResumeResponse.projectExperiences).ifPresent(projectExperiences -> response.projectExperiences = projectExperiences.stream().map(exp -> {
            ProjectExperienceAJAXView projectExp = new ProjectExperienceAJAXView();
            projectExp.name = exp.name;
            projectExp.description = exp.description;
            return projectExp;
        }).collect(Collectors.toList()));
        return response;
    }

    private SaveResumeRequest convert2SaveResumeRequest(CreateIntervieweeResumeAJAXRequest request) {
        SaveResumeRequest saveResumeRequest = new SaveResumeRequest();
        saveResumeRequest.firstName = request.firstName;
        saveResumeRequest.lastName = request.lastName;
        saveResumeRequest.dob = request.dob;
        Optional.ofNullable(request.jobExperiences).ifPresent(jobExperiences -> saveResumeRequest.jobExperiences = jobExperiences.stream().map(exp -> {
            JobExperienceView jobExp = new JobExperienceView();
            jobExp.companyName = exp.companyName;
            jobExp.position = exp.position;
            jobExp.endDate = exp.endDate;
            jobExp.startedDate = exp.startedDate;
            jobExp.description = exp.description;
            return jobExp;
        }).collect(Collectors.toList()));
        Optional.ofNullable(request.projectExperiences).ifPresent(projectExperiences -> saveResumeRequest.projectExperiences = projectExperiences.stream().map(exp -> {
            ProjectExperienceView projectExp = new ProjectExperienceView();
            projectExp.name = exp.name;
            projectExp.description = exp.description;
            return projectExp;
        }).collect(Collectors.toList()));
        return saveResumeRequest;
    }

    @Override
    public IntervieweeLoginAJAXResponse login(IntervieweeLoginAJAXRequest request) {
        IntervieweeLoginRequest intervieweeLoginRequest = new IntervieweeLoginRequest();
        intervieweeLoginRequest.mobilePhone = request.mobilePhone;
        intervieweeLoginRequest.password = request.password;
        IntervieweeLoginResponse intervieweeLoginResponse = interviewService.login(intervieweeLoginRequest);
        if (intervieweeLoginResponse.success && intervieweeLoginResponse.intervieweeId != null) {
            webContext.request().session().set(SESSION_LOGIN_INTERVIEWEE_ID_KEY, String.valueOf(intervieweeLoginResponse.intervieweeId));
        }
        IntervieweeLoginAJAXResponse response = new IntervieweeLoginAJAXResponse();
        response.success = intervieweeLoginResponse.success;
        response.intervieweeId = intervieweeLoginResponse.intervieweeId;
        response.errorMessage = intervieweeLoginResponse.errorMessage;
        return response;
    }

    @LoginRequired(SESSION_LOGIN_INTERVIEWEE_ID_KEY)
    @Override
    public void logout() {
        webContext.request().session().invalidate();
    }
}
