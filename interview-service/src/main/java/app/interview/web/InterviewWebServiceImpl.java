package app.interview.web;

import app.api.InterviewWebService;
import app.api.interview.GetInterviewResponse;
import app.api.interview.SearchInterviewRequest;
import app.api.interview.SearchInterviewResponse;
import app.api.interview.UpdateInterviewRequest;
import app.interview.service.InterviewService;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class InterviewWebServiceImpl implements InterviewWebService {
    @Inject
    InterviewService interviewService;

    @Override
    public void update(Long id, UpdateInterviewRequest request) {
        interviewService.update(id, request);
    }

    @Override
    public GetInterviewResponse get(Long id) {
        return interviewService.get(id);
    }

    @Override
    public SearchInterviewResponse search(SearchInterviewRequest request) {
        return interviewService.search(request);
    }
}
