package app;

import app.api.DeviceAJAXWebService;
import app.api.InterviewAJAXWebService;
import app.api.StaffAJAXWebService;
import app.common.web.interceptor.LoginVerificationInterceptor;
import app.service.DeviceService;
import app.service.InterviewService;
import app.service.StaffService;
import app.service.SessionService;
import app.web.DeviceAJAXWebServiceImpl;
import app.web.InterviewAJAXWebServiceImpl;
import app.web.StaffAJAXWebServiceImpl;
import core.framework.module.Module;

/**
 * @author beckl
 */
public class WebsiteModule extends Module {
    @Override
    protected void initialize() {
        http().intercept(bind(LoginVerificationInterceptor.class));

        bind(SessionService.class);
        bind(InterviewService.class);
        bind(StaffService.class);
        bind(DeviceService.class);

        api().service(InterviewAJAXWebService.class, bind(InterviewAJAXWebServiceImpl.class));
        api().service(StaffAJAXWebService.class, bind(StaffAJAXWebServiceImpl.class));
        api().service(DeviceAJAXWebService.class, bind(DeviceAJAXWebServiceImpl.class));
    }
}