package app.interview.web;

import app.api.BOInterviewWebService;
import app.api.interview.BOCreateInterviewRequest;
import app.api.interview.BOCreateInterviewResponse;
import app.api.interview.BOSearchInterviewRequest;
import app.api.interview.BOSearchInterviewResponse;
import app.api.interview.SearchInterviewRequest;
import app.api.interview.SearchInterviewResponse;
import app.interview.service.InterviewService;
import core.framework.inject.Inject;
import core.framework.web.exception.BadRequestException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class BOInterviewWebServiceImpl implements BOInterviewWebService {
    @Inject
    InterviewService interviewService;

    @Override
    public BOCreateInterviewResponse create(BOCreateInterviewRequest request) {
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.plusMinutes(5).isAfter(request.appointedTime)) {
            throw new BadRequestException("Appointment time should be at least 5 mins later");
        }
        return interviewService.create(request);
    }

    @Override
    public BOSearchInterviewResponse search(BOSearchInterviewRequest request) {
        SearchInterviewRequest searchInterviewRequest = new SearchInterviewRequest();
        searchInterviewRequest.jobPosition = request.jobPosition;
        searchInterviewRequest.isCompleted = request.isCompleted;
        searchInterviewRequest.staffId = request.staffId;
        searchInterviewRequest.intervieweeId = request.intervieweeId;
        searchInterviewRequest.isIntervieweeAssigned = request.isIntervieweeAssigned;
        searchInterviewRequest.skip = request.skip;
        searchInterviewRequest.limit = request.limit;
        SearchInterviewResponse searchInterviewResponse = interviewService.search(searchInterviewRequest);
        BOSearchInterviewResponse response = new BOSearchInterviewResponse();
        response.total = searchInterviewResponse.total;
        if (searchInterviewResponse.interviews != null) {
            response.interviews = searchInterviewResponse.interviews.stream().map(view -> {
                var interviewView = new BOSearchInterviewResponse.BOInterviewView();
                interviewView.id = view.id;
                interviewView.jobPosition = view.jobPosition;
                interviewView.staffId = view.staffId;
                interviewView.intervieweeId = view.intervieweeId;
                interviewView.appointedTime = view.appointedTime;
                interviewView.comment = view.comment;
                interviewView.completed = view.completed;
                return interviewView;
            }).collect(Collectors.toList());
        }
        return response;
    }
}
