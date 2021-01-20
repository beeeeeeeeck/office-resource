package app.schedule.service;

import app.api.InterviewWebService;
import app.api.interview.SearchInterviewRequest;
import app.api.interview.SearchInterviewResponse;
import core.framework.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author beckl
 */
public class InterviewService {
    @Inject
    InterviewWebService interviewWebService;

    public List<SearchInterviewResponse.InterviewView> listUpcomingInterviews() {
        SearchInterviewRequest searchInterviewRequest = new SearchInterviewRequest();
        searchInterviewRequest.isCompleted = Boolean.FALSE;
        SearchInterviewResponse searchInterviewResponse = interviewWebService.search(searchInterviewRequest);
        return Objects.requireNonNullElseGet(searchInterviewResponse.interviews, ArrayList::new);
    }
}
