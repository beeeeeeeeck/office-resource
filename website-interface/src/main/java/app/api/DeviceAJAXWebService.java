package app.api;

import app.api.device.ApplyDeviceAJAXRequest;
import app.api.device.ApplyDeviceAJAXResponse;
import app.api.device.SearchAvailableDeviceAJAXRequest;
import app.api.device.SearchAvailableDeviceAJAXResponse;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.ResponseStatus;

/**
 * @author beckl
 */
public interface DeviceAJAXWebService {
    @PUT
    @Path("/device")
    SearchAvailableDeviceAJAXResponse searchAvailableDevices(SearchAvailableDeviceAJAXRequest request);

    @POST
    @Path("/device-application")
    @ResponseStatus(HTTPStatus.CREATED)
    ApplyDeviceAJAXResponse applyDevice(ApplyDeviceAJAXRequest request);
}
