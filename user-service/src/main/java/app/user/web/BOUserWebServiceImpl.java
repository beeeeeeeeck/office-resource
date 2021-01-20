package app.user.web;

import app.api.BOUserWebService;
import app.api.user.BOCreateUserRequest;
import app.api.user.BOCreateUserResponse;
import app.api.user.BOUserLoginRequest;
import app.api.user.BOUserLoginResponse;
import app.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class BOUserWebServiceImpl implements BOUserWebService {
    @Inject
    UserService userService;

    @Override
    public BOCreateUserResponse create(BOCreateUserRequest request) {
        return userService.create(request);
    }

    @Override
    public BOUserLoginResponse login(BOUserLoginRequest request) {
        return userService.login(request);
    }
}
