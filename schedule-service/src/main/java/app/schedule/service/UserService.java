package app.schedule.service;

import app.api.UserWebService;
import app.api.user.ListUserResponse;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class UserService {
    @Inject
    UserWebService userWebService;

    public ListUserResponse listUsers() {
        return userWebService.list();
    }
}
