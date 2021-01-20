package app.schedule.job;

import app.api.device.ListDeviceSummaryResponse;
import app.api.user.ListUserResponse;
import app.schedule.service.DeviceService;
import app.schedule.service.UserService;
import core.framework.inject.Inject;
import core.framework.scheduler.Job;
import core.framework.scheduler.JobContext;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class InsufficientDeviceAlertJob implements Job {
    public static final int WEEKS_BEFORE_EXPIRED = 1;
    public static final int DEVICE_INSUFFICIENT_PERCENTAGE = 10;
    private final Logger logger = LoggerFactory.getLogger(InsufficientDeviceAlertJob.class);
    @Inject
    DeviceService deviceService;
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
            ListDeviceSummaryResponse response = deviceService.listDeviceSummaries(skip, 100);
            if (response.total == 0 || response.deviceSummaries.isEmpty()) {
                break;
            }
            skip += response.deviceSummaries.size();
            total = response.total;
            LocalDate timeThreshold = LocalDate.now().plusWeeks(WEEKS_BEFORE_EXPIRED);
            List<String> deviceFiltered = response.deviceSummaries.stream()
                    .map(device -> {
                        int ratio = (int) ((device.quantityInStock / device.quantity) * 100);
                        if (ratio <= DEVICE_INSUFFICIENT_PERCENTAGE) {
                            return Strings.format("Device insufficient - {} : {} in stock / {} total", device.deviceName, device.quantityInStock, device.quantity);
                        }
                        if (device.expiredDate != null && device.expiredDate.isEqual(timeThreshold)) {
                            return Strings.format("Device expired in {} week - {} : {}", WEEKS_BEFORE_EXPIRED, device.deviceName, device.expiredDate);
                        }
                        return "";
                    })
                    .filter(msg -> !Strings.isBlank(msg))
                    .collect(Collectors.toList());
            usersResponse.users.forEach(admin -> notifyAdmin(admin.userName, deviceFiltered));
        } while (total > skip);
    }

    private void notifyAdmin(String userName, List<String> devices) {
        // MOCK - sending insufficient device list to admin
        logger.info("Send email to admin - {}", userName);
        logger.info(String.join("\n", devices));
    }
}
