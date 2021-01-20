package app.schedule.job;

import app.api.staff.SearchStaffResponse;
import app.api.user.ListUserResponse;
import app.schedule.service.StaffService;
import app.schedule.service.UserService;
import core.framework.inject.Inject;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class MonthlyBirthdayNotificationJob implements Job {
    private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("MM-dd");
    private final Logger logger = LoggerFactory.getLogger(MonthlyBirthdayNotificationJob.class);
    @Inject
    StaffService staffService;
    @Inject
    UserService userService;

    @Override
    public void execute(JobContext context) {
        ListUserResponse usersResponse = userService.listUsers();
        if (usersResponse.users.isEmpty()) {
            return;
        }
        long total;
        int skip = 0;
        do {
            SearchStaffResponse response = staffService.listBulkStaffs(skip, 100);
            if (response.total == 0 || response.staffs.isEmpty()) {
                break;
            }
            skip += response.staffs.size();
            total = response.total;
            int currentMonth = LocalDateTime.now().getMonthValue();
            List<String> staffsFiltered = response.staffs.stream()
                    .filter(staff -> staff.dob.getMonthValue() == currentMonth)
                    .map(staff -> Strings.format("{} {} - {}", staff.firstName, staff.lastName, staff.dob.format(BIRTHDAY_FORMATTER)))
                    .collect(Collectors.toList());
            if (!staffsFiltered.isEmpty()) {
                usersResponse.users.forEach(admin -> notifyAdmin(admin.userName, staffsFiltered));
            }
        } while (total > skip);
    }

    private void notifyAdmin(String name, List<String> staffs) {
        // MOCK - sending staff list to admin
        logger.info("Send email to admin - {}", name);
        logger.info(String.join("\n", staffs));
    }
}
