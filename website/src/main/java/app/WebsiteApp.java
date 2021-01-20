package app;

import app.api.DeviceApplicationWebService;
import app.api.DeviceWebService;
import app.api.InterviewWebService;
import app.api.IntervieweeWebService;
import app.api.StaffWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author beckl
 */
public class WebsiteApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");

        api().client(IntervieweeWebService.class, requiredProperty("app.interview.serviceURL"));
        api().client(InterviewWebService.class, requiredProperty("app.interview.serviceURL"));
        api().client(DeviceWebService.class, requiredProperty("app.device.serviceURL"));
        api().client(DeviceApplicationWebService.class, requiredProperty("app.device.serviceURL"));
        api().client(StaffWebService.class, requiredProperty("app.staff.serviceURL"));

        load(new WebsiteModule());
    }
}