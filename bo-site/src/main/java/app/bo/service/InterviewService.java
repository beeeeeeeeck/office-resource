package app.bo.service;

import app.api.BOInterviewWebService;
import app.api.BOIntervieweeWebService;
import app.api.interview.BOCreateInterviewRequest;
import app.api.interview.BOCreateInterviewResponse;
import app.api.interview.BOSearchInterviewRequest;
import app.api.interview.BOSearchInterviewResponse;
import app.api.interviewee.BOSearchIntervieweeRequest;
import app.api.interviewee.BOSearchIntervieweeResponse;
import app.api.interviewee.BOUpdateIntervieweeRequest;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class InterviewService {
    @Inject
    BOIntervieweeWebService intervieweeWebService;
    @Inject
    BOInterviewWebService interviewWebService;

    public BOSearchIntervieweeResponse searchInterviewee(BOSearchIntervieweeRequest request) {
        return intervieweeWebService.search(request);
    }

    public BOCreateInterviewResponse createInterview(BOCreateInterviewRequest request) {
        return interviewWebService.create(request);
    }

    public BOSearchInterviewResponse searchInterview(BOSearchInterviewRequest request) {
        return interviewWebService.search(request);
    }

    public void updateIntervieweeStatus(Long id, BOUpdateIntervieweeRequest request) {
        intervieweeWebService.update(id, request);
    }
}
