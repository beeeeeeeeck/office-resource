package app.bo.web;

import app.api.interview.BOCreateInterviewRequest;
import app.api.interview.BOCreateInterviewResponse;
import app.api.interview.BOSearchInterviewRequest;
import app.api.interview.BOSearchInterviewResponse;
import app.api.interviewee.BOSearchIntervieweeRequest;
import app.api.interviewee.BOSearchIntervieweeResponse;
import app.api.interviewee.BOUpdateIntervieweeRequest;
import app.api.interviewee.InterviewStatusView;
import app.bo.api.InterviewAJAXWebService;
import app.bo.api.interview.AssignInterviewAJAXRequest;
import app.bo.api.interview.AssignInterviewAJAXResponse;
import app.bo.api.interview.InterviewStatusAJAXView;
import app.bo.api.interview.SearchInterviewAJAXRequest;
import app.bo.api.interview.SearchInterviewAJAXResponse;
import app.bo.api.interview.SearchIntervieweeAJAXRequest;
import app.bo.api.interview.SearchIntervieweeAJAXResponse;
import app.bo.api.interview.UpdateIntervieweeStatusAJAXRequest;
import app.bo.service.InterviewService;
import app.common.web.interceptor.LoginRequired;
import core.framework.inject.Inject;

import java.util.Optional;
import java.util.stream.Collectors;

import static app.bo.ajax.LoginController.SESSION_USER_LOGIN_KEY;

/**
 * @author beckl
 */
public class InterviewAJAXWebServiceImpl implements InterviewAJAXWebService {
    @Inject
    InterviewService interviewService;

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public SearchIntervieweeAJAXResponse search(SearchIntervieweeAJAXRequest request) {
        BOSearchIntervieweeRequest searchIntervieweeRequest = new BOSearchIntervieweeRequest();
        searchIntervieweeRequest.mobilePhone = request.mobilePhone;
        if (request.status != null) {
            searchIntervieweeRequest.status = InterviewStatusView.valueOf(request.status.name());
        }
        searchIntervieweeRequest.skip = request.skip;
        searchIntervieweeRequest.limit = request.limit;
        BOSearchIntervieweeResponse searchIntervieweeResponse = interviewService.searchInterviewee(searchIntervieweeRequest);
        SearchIntervieweeAJAXResponse response = new SearchIntervieweeAJAXResponse();
        response.total = searchIntervieweeResponse.total;
        Optional.ofNullable(searchIntervieweeResponse.interviewees).ifPresent(interviewees -> response.interviewees = interviewees.stream().map(view -> {
            var intervieweeView = new SearchIntervieweeAJAXResponse.IntervieweeAJAXView();
            intervieweeView.id = view.id;
            intervieweeView.mobilePhone = view.mobilePhone;
            intervieweeView.jobPosition = view.jobPosition;
            intervieweeView.status = InterviewStatusAJAXView.valueOf(view.status.name());
            return intervieweeView;
        }).collect(Collectors.toList()));
        return response;
    }

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public AssignInterviewAJAXResponse assign(Long id, AssignInterviewAJAXRequest request) {
        BOCreateInterviewRequest createInterviewRequest = new BOCreateInterviewRequest();
        createInterviewRequest.intervieweeId = id;
        createInterviewRequest.jobPosition = request.jobPosition;
        createInterviewRequest.staffId = request.staffId;
        createInterviewRequest.appointedTime = request.appointedTime;
        BOCreateInterviewResponse createInterviewResponse = interviewService.createInterview(createInterviewRequest);
        AssignInterviewAJAXResponse response = new AssignInterviewAJAXResponse();
        response.id = createInterviewResponse.id;
        response.intervieweeId = createInterviewResponse.intervieweeId;
        response.jobPosition = createInterviewResponse.jobPosition;
        response.staffId = createInterviewResponse.staffId;
        response.appointedTime = createInterviewResponse.appointedTime;
        return response;
    }

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public SearchInterviewAJAXResponse searchInterview4SpecifiedInterviewee(Long id, SearchInterviewAJAXRequest request) {
        BOSearchInterviewRequest searchInterviewRequest = new BOSearchInterviewRequest();
        searchInterviewRequest.intervieweeId = id;
        searchInterviewRequest.staffId = request.staffId;
        searchInterviewRequest.isCompleted = request.isCompleted;
        searchInterviewRequest.skip = request.skip;
        searchInterviewRequest.limit = request.limit;
        searchInterviewRequest.isIntervieweeAssigned = Boolean.TRUE;
        BOSearchInterviewResponse searchInterviewResponse = interviewService.searchInterview(searchInterviewRequest);
        SearchInterviewAJAXResponse response = new SearchInterviewAJAXResponse();
        response.total = searchInterviewResponse.total;
        Optional.ofNullable(searchInterviewResponse.interviews).ifPresent(interviews -> response.interviews = interviews.stream().map(view -> {
            var interviewView = new SearchInterviewAJAXResponse.InterviewAJAXView();
            interviewView.id = view.id;
            interviewView.jobPosition = view.jobPosition;
            interviewView.intervieweeId = view.intervieweeId;
            interviewView.staffId = view.staffId;
            interviewView.appointedTime = view.appointedTime;
            interviewView.completed = view.completed;
            interviewView.comment = view.comment;
            return interviewView;
        }).collect(Collectors.toList()));
        return response;
    }

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public void updateIntervieweeStatus(Long id, UpdateIntervieweeStatusAJAXRequest request) {
        BOUpdateIntervieweeRequest updateIntervieweeRequest = new BOUpdateIntervieweeRequest();
        updateIntervieweeRequest.status = InterviewStatusView.valueOf(request.status.name());
        interviewService.updateIntervieweeStatus(id, updateIntervieweeRequest);
    }
}
