package app.api;

import app.api.device.CreateDeviceApplicationRequest;
import app.api.device.CreateDeviceApplicationResponse;
import app.api.device.GetDeviceApplicationResponse;
import app.api.device.SearchDeviceApplicationRequest;
import app.api.device.SearchDeviceApplicationResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface DeviceApplicationWebService {
    @POST
    @Path("/device-application")
    @ResponseStatus(HTTPStatus.CREATED)
    CreateDeviceApplicationResponse create(CreateDeviceApplicationRequest request);

    @GET
    @Path("/device-application/:id")
    GetDeviceApplicationResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/device-application/:id/return")
    void returnDevice(@PathParam("id") Long id);

    @PUT
    @Path("/device-application")
    SearchDeviceApplicationResponse search(SearchDeviceApplicationRequest request);
}
