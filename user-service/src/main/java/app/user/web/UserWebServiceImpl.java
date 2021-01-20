package app.user.web;

import app.api.UserWebService;
import app.api.user.ListUserResponse;
import app.user.service.UserService;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class UserWebServiceImpl implements UserWebService {
    @Inject
    UserService userService;

    @Override
    public ListUserResponse list() {
        return userService.list();
    }
}
