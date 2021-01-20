package app.bo;

import app.bo.ajax.LoginController;
import app.bo.api.DeviceAJAXWebService;
import app.bo.api.InterviewAJAXWebService;
import app.bo.api.StaffAJAXWebService;
import app.bo.api.UserAJAXWebService;
import app.bo.service.DeviceService;
import app.bo.service.InterviewService;
import app.bo.service.StaffService;
import app.bo.service.UserService;
import app.bo.web.DeviceAJAXWebServiceImpl;
import app.bo.web.InterviewAJAXWebServiceImpl;
import app.bo.web.StaffAJAXWebServiceImpl;
import app.bo.web.UserAJAXWebServiceImpl;
import app.common.web.interceptor.LoginVerificationInterceptor;
import core.framework.module.Module;

import static core.framework.http.HTTPMethod.GET;

/**
 * @author beckl
 */
public class BackOfficeModule extends Module {
    @Override
    protected void initialize() {
        http().intercept(bind(LoginVerificationInterceptor.class));

        bind(StaffService.class);
        bind(DeviceService.class);
        bind(InterviewService.class);
        bind(UserService.class);

        api().service(StaffAJAXWebService.class, bind(StaffAJAXWebServiceImpl.class));
        api().service(DeviceAJAXWebService.class, bind(DeviceAJAXWebServiceImpl.class));
        api().service(InterviewAJAXWebService.class, bind(InterviewAJAXWebServiceImpl.class));
        api().service(UserAJAXWebService.class, bind(UserAJAXWebServiceImpl.class));

        LoginController loginController = bind(LoginController.class);
        http().route(GET, "/ajax/user/login", loginController::login);
        http().route(GET, "/ajax/user/logout", loginController::logout);
    }
}