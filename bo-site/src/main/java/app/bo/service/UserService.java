package app.bo.service;

import app.api.BOUserWebService;
import app.api.user.BOCreateUserRequest;
import app.api.user.BOCreateUserResponse;
import app.api.user.BOUserLoginRequest;
import app.api.user.BOUserLoginResponse;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class UserService {
    @Inject
    BOUserWebService userWebService;

    public BOUserLoginResponse login(BOUserLoginRequest request) {
        return userWebService.login(request);
    }

    public BOCreateUserResponse create(BOCreateUserRequest request) {
        return userWebService.create(request);
    }
}
