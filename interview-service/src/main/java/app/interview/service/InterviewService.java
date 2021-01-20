package app.interview.service;

import app.api.interview.BOCreateInterviewRequest;
import app.api.interview.BOCreateInterviewResponse;
import app.api.interview.GetInterviewResponse;
import app.api.interview.SearchInterviewRequest;
import app.api.interview.SearchInterviewResponse;
import app.api.interview.UpdateInterviewRequest;
import app.interview.domain.Interview;
import app.interview.domain.InterviewStatus;
import app.interview.domain.Interviewee;
import core.framework.db.Database;
import core.framework.db.Query;
import core.framework.db.Repository;
import core.framework.db.Transaction;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class InterviewService {
    @Inject
    Repository<Interview> interviewRepository;
    @Inject
    IntervieweeService intervieweeService;
    @Inject
    Database database;

    public BOCreateInterviewResponse create(BOCreateInterviewRequest request) {
        Interviewee interviewee = intervieweeService.getInterviewee(request.intervieweeId);
        Interview interview = new Interview();
        interview.jobPosition = Strings.isBlank(request.jobPosition) ? interviewee.jobPosition : request.jobPosition;
        interview.intervieweeId = interviewee.id;
        interview.staffId = request.staffId;
        interview.appointedTime = request.appointedTime;
        interview.completed = Boolean.FALSE;
        interview.createdTime = LocalDateTime.now();
        interview.updatedTime = LocalDateTime.now();
        BOCreateInterviewResponse response = new BOCreateInterviewResponse();
        try (Transaction transaction = database.beginTransaction()) {
            response.id = interviewRepository.insert(interview).orElseThrow();
            interviewee.status = InterviewStatus.ASSIGNED;
            interviewee.updatedTime = LocalDateTime.now();
            intervieweeService.updateInterviewee(interviewee);
            transaction.commit();
        }
        response.jobPosition = interview.jobPosition;
        response.intervieweeId = interview.intervieweeId;
        response.staffId = interview.staffId;
        response.appointedTime = interview.appointedTime;
        response.completed = interview.completed;
        return response;
    }

    private Interview getInterview(Long id) {
        return interviewRepository.get(id).orElseThrow(() -> new NotFoundException("Interview not found by id = " + id));
    }

    public void update(Long id, UpdateInterviewRequest request) {
        Interview interview = getInterview(id);
        interview.comment = request.comment;
        interview.completed = Boolean.TRUE;
        interview.updatedTime = LocalDateTime.now();
        interviewRepository.update(interview);
    }

    public GetInterviewResponse get(Long id) {
        Interview interview = getInterview(id);
        GetInterviewResponse response = new GetInterviewResponse();
        response.id = interview.id;
        response.jobPosition = interview.jobPosition;
        response.intervieweeId = interview.intervieweeId;
        response.staffId = interview.staffId;
        response.appointedTime = interview.appointedTime;
        response.completed = interview.completed;
        response.comment = interview.comment;
        return response;
    }

    public SearchInterviewResponse search(SearchInterviewRequest request) {
        Query<Interview> query = interviewRepository.select();
        query.orderBy("appointed_time ASC, updated_time DESC");
        query.skip(request.skip);
        query.limit(request.limit);
        if (!Strings.isBlank(request.jobPosition)) {
            query.where("job_position LIKE ?", request.jobPosition + "%");
        }
        if (request.intervieweeId != null) {
            query.where("interviewee_id = ?", request.intervieweeId);
        }
        if (request.staffId != null) {
            query.where("staff_id = ?", request.staffId);
        }
        if (request.isCompleted != null) {
            query.where("completed = ?", request.isCompleted ? 1 : 0);
        }
        if (request.isIntervieweeAssigned != null && request.isIntervieweeAssigned) {
            query.where("EXISTS (SELECT 1 FROM interviewees iee WHERE interviewee_id = iee.id AND iee.status = ?)", "ASSIGNED");
        }
        SearchInterviewResponse result = new SearchInterviewResponse();
        result.interviews = query.fetch().stream().map(this::recordToView).collect(Collectors.toList());
        result.total = query.count();
        return result;
    }

    private SearchInterviewResponse.InterviewView recordToView(Interview interview) {
        var view = new SearchInterviewResponse.InterviewView();
        view.id = interview.id;
        view.jobPosition = interview.jobPosition;
        view.intervieweeId = interview.intervieweeId;
        view.staffId = interview.staffId;
        view.appointedTime = interview.appointedTime;
        view.comment = interview.comment;
        view.completed = interview.completed;
        return view;
    }
}
