package app.bo.api;

import app.bo.api.device.CreateDeviceAJAXRequest;
import app.bo.api.device.CreateDeviceAJAXResponse;
import app.bo.api.device.SearchDeviceAJAXRequest;
import app.bo.api.device.SearchDeviceAJAXResponse;
import app.bo.api.device.SearchDeviceApplicationAJAXRequest;
import app.bo.api.device.SearchDeviceApplicationAJAXResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface DeviceAJAXWebService {
    @POST
    @Path("/device")
    @ResponseStatus(HTTPStatus.CREATED)
    CreateDeviceAJAXResponse create(CreateDeviceAJAXRequest request);

    @PUT
    @Path("/device")
    SearchDeviceAJAXResponse search(SearchDeviceAJAXRequest request);

    @PUT
    @Path("/device-application")
    SearchDeviceApplicationAJAXResponse searchApplication(SearchDeviceApplicationAJAXRequest request);

    @PUT
    @Path("/device-application/:id/approval")
    void approveApplication(@PathParam("id") Long id);
}
