package app.api;

import app.api.user.ListUserResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;

/**
 * @author beckl
 */
public interface UserWebService {
    @GET
    @Path("/user")
    ListUserResponse list();
}
