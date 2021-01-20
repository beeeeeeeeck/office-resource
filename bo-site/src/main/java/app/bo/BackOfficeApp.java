package app.bo;

import app.api.BODeviceApplicationWebService;
import app.api.BODeviceWebService;
import app.api.BOInterviewWebService;
import app.api.BOIntervieweeWebService;
import app.api.BOStaffWebService;
import app.api.BOUserWebService;
import core.framework.module.App;
import core.framework.module.SystemModule;

/**
 * @author beckl
 */
public class BackOfficeApp extends App {
    @Override
    protected void initialize() {
        load(new SystemModule("sys.properties"));
        loadProperties("app.properties");

        api().client(BOIntervieweeWebService.class, requiredProperty("app.interview.serviceURL"));
        api().client(BOInterviewWebService.class, requiredProperty("app.interview.serviceURL"));
        api().client(BODeviceWebService.class, requiredProperty("app.device.serviceURL"));
        api().client(BODeviceApplicationWebService.class, requiredProperty("app.device.serviceURL"));
        api().client(BOStaffWebService.class, requiredProperty("app.staff.serviceURL"));
        api().client(BOUserWebService.class, requiredProperty("app.user.serviceURL"));

        load(new BackOfficeModule());
    }
}