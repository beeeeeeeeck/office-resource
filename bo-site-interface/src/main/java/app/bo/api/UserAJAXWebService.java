package app.bo.api;

import app.bo.api.user.CreateUserAJAXRequest;
import app.bo.api.user.CreateUserAJAXResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface UserAJAXWebService {
    @POST
    @Path("/user")
    @ResponseStatus(HTTPStatus.CREATED)
    CreateUserAJAXResponse create(CreateUserAJAXRequest request);
}
