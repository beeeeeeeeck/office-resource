package app.api;

import app.api.user.BOCreateUserRequest;
import app.api.user.BOCreateUserResponse;
import app.api.user.BOUserLoginRequest;
import app.api.user.BOUserLoginResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface BOUserWebService {
    @POST
    @Path("/bo/user")
    @ResponseStatus(HTTPStatus.CREATED)
    BOCreateUserResponse create(BOCreateUserRequest request);

    @POST
    @Path("/bo/user/login")
    BOUserLoginResponse login(BOUserLoginRequest request);
}
