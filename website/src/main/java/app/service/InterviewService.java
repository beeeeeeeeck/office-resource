package app.service;

import app.api.InterviewWebService;
import app.api.IntervieweeWebService;
import app.api.interview.SearchInterviewRequest;
import app.api.interview.SearchInterviewResponse;
import app.api.interview.UpdateInterviewRequest;
import app.api.interviewee.CreateIntervieweeRequest;
import app.api.interviewee.CreateIntervieweeResponse;
import app.api.interviewee.GetResumeResponse;
import app.api.interviewee.IntervieweeLoginRequest;
import app.api.interviewee.IntervieweeLoginResponse;
import app.api.interviewee.SaveResumeRequest;
import app.api.interviewee.SaveResumeResponse;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class InterviewService {
    @Inject
    IntervieweeWebService intervieweeWebService;
    @Inject
    InterviewWebService interviewWebService;

    public CreateIntervieweeResponse registerAsInterviewee(CreateIntervieweeRequest request) {
        return intervieweeWebService.create(request);
    }

    public SaveResumeResponse createIntervieweeResume(Long id, SaveResumeRequest request) {
        return intervieweeWebService.saveResume(id, request);
    }

    public SearchInterviewResponse search(SearchInterviewRequest request) {
        return interviewWebService.search(request);
    }

    public void update(Long id, UpdateInterviewRequest request) {
        interviewWebService.update(id, request);
    }

    public GetResumeResponse getResume(Long id) {
        return intervieweeWebService.getResume(id);
    }

    public IntervieweeLoginResponse login(IntervieweeLoginRequest request) {
        return intervieweeWebService.login(request);
    }
}
