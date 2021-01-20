package app.schedule.job;

import app.api.interview.SearchInterviewResponse;
import app.schedule.service.InterviewService;
import core.framework.inject.Inject;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author beckl
 */
public class UpcomingInterviewNotificationJob implements Job {
    private final Logger logger = LoggerFactory.getLogger(UpcomingInterviewNotificationJob.class);
    @Inject
    InterviewService interviewService;

    @Override
    public void execute(JobContext context) {
        List<SearchInterviewResponse.InterviewView> interviews = interviewService.listUpcomingInterviews();
        LocalDateTime timeThreshold = LocalDateTime.now().plusMinutes(30).truncatedTo(ChronoUnit.MINUTES);
        interviews.stream().filter(interview -> timeThreshold.isEqual(interview.appointedTime.truncatedTo(ChronoUnit.MINUTES))).forEach(interview -> {
            // MOCK - sending notification to interviewer
            logger.info("Note {}: you have an upcoming interview for {}", interview.staffId, interview.intervieweeId);
        });
    }
}
