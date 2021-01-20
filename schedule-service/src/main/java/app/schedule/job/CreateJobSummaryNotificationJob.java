package app.schedule.job;

import app.api.staff.SearchStaffResponse;
import app.schedule.service.StaffService;
import core.framework.inject.Inject;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author beckl
 */
public class CreateJobSummaryNotificationJob implements Job {
    public static final int DAYS_BEFORE_ANNIVERSARY = 5;
    private final Logger logger = LoggerFactory.getLogger(CreateJobSummaryNotificationJob.class);
    @Inject
    StaffService staffService;

    @Override
    public void execute(JobContext context) {
        long total;
        int skip = 0;
        do {
            SearchStaffResponse response = staffService.listBulkStaffs(skip, 100);
            if (response.total == 0 || response.staffs.isEmpty()) {
                break;
            }
            skip += response.staffs.size();
            total = response.total;
            LocalDateTime timeThreshold = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
            response.staffs.stream().filter(staff -> {
                LocalDate anniversary = staff.startedDate.withYear(timeThreshold.plusDays(DAYS_BEFORE_ANNIVERSARY).getYear());
                return timeThreshold.until(anniversary, ChronoUnit.DAYS) == DAYS_BEFORE_ANNIVERSARY;
            }).forEach(staff -> {
                // MOCK - sending notification to staff for creating job summary
                logger.info("Hi {} {}, please create your job summary for next period!", staff.firstName, staff.lastName);
            });
        } while (total > skip);
    }
}
