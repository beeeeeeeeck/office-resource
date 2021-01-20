package app.bo.web;

import app.api.user.BOCreateUserRequest;
import app.api.user.BOCreateUserResponse;
import app.bo.api.UserAJAXWebService;
import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import app.bo.service.UserService;
import app.common.web.interceptor.LoginRequired;
import core.framework.inject.Inject;

import static app.bo.ajax.LoginController.SESSION_USER_LOGIN_KEY;

/**
 * @author beckl
 */
public class UserAJAXWebServiceImpl implements UserAJAXWebService {
    @Inject
    UserService userService;

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public CreateUserAJAXResponse create(CreateUserAJAXRequest request) {
        BOCreateUserRequest createUserRequest = new BOCreateUserRequest();
        createUserRequest.userName = request.userName;
        createUserRequest.password = request.password;
        BOCreateUserResponse createUserResponse = userService.create(createUserRequest);
        CreateUserAJAXResponse response = new CreateUserAJAXResponse();
        response.id = createUserResponse.id;
        response.active = createUserResponse.active;
        return response;
    }
}
