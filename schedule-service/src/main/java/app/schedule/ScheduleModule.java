package app.schedule;

import app.schedule.job.CreateJobSummaryNotificationJob;
import app.schedule.job.InsufficientDeviceAlertJob;
import app.schedule.job.MonthlyBirthdayNotificationJob;
import app.schedule.job.UpcomingInterviewNotificationJob;
import app.schedule.service.DeviceService;
import app.schedule.service.InterviewService;
import app.schedule.service.StaffService;
import app.schedule.service.UserService;
import core.framework.module.Module;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

/**
 * @author beckl
 */
public class ScheduleModule extends Module {
    @Override
    protected void initialize() {
        bind(InterviewService.class);
        bind(DeviceService.class);
        bind(StaffService.class);
        bind(UserService.class);

        schedule().fixedRate("upcoming-interview-notification", bind(UpcomingInterviewNotificationJob.class), Duration.ofMinutes(1));
        schedule().dailyAt("create-job-summary-notification", bind(CreateJobSummaryNotificationJob.class), LocalTime.of(8, 0));
        schedule().monthlyAt("monthly-birthday-notification", bind(MonthlyBirthdayNotificationJob.class), 1, LocalTime.of(8, 0));
        schedule().weeklyAt("insufficient-devices-alert", bind(InsufficientDeviceAlertJob.class), DayOfWeek.MONDAY, LocalTime.of(8, 0));
    }
}
