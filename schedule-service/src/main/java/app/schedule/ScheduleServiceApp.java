package app.schedule;

import app.api.DeviceWebService;
import app.api.InterviewWebService;
import app.api.StaffWebService;
import app.api.UserWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author beckl
 */
public class ScheduleServiceApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");

        api().client(InterviewWebService.class, requiredProperty("app.interview.serviceURL"));
        api().client(DeviceWebService.class, requiredProperty("app.device.serviceURL"));
        api().client(StaffWebService.class, requiredProperty("app.staff.serviceURL"));
        api().client(UserWebService.class, requiredProperty("app.user.serviceURL"));

        // biz modules loading
        load(new ScheduleModule());
    }
}
