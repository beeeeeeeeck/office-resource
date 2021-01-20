package app.api;

import app.api.device.BOSearchDeviceApplicationRequest;
import app.api.device.BOSearchDeviceApplicationResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author beckl
 */
public interface BODeviceApplicationWebService {
    @PUT
    @Path("/bo/device-application")
    BOSearchDeviceApplicationResponse search(BOSearchDeviceApplicationRequest request);

    @PUT
    @Path("/bo/device-application/:id/approval")
    void approve(@PathParam("id") Long id);
}
