package app.user;

import app.api.BOUserWebService;
import app.api.UserWebService;
import app.user.domain.User;
import app.user.service.UserService;
import app.user.web.BOUserWebServiceImpl;
import app.user.web.UserWebServiceImpl;
import core.framework.module.Module;

/**
 * @author beckl
 */
public class UserModule extends Module {
    @Override
    protected void initialize() {
        db().repository(User.class);

        bind(UserService.class);

        api().service(UserWebService.class, bind(UserWebServiceImpl.class));
        api().service(BOUserWebService.class, bind(BOUserWebServiceImpl.class));
    }
}
