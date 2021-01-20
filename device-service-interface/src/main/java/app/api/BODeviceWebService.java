package app.api;

import app.api.device.BOCreateDeviceProcurementRequest;
import app.api.device.BOCreateDeviceProcurementResponse;
import app.api.device.BOSearchDeviceRequest;
import app.api.device.BOSearchDeviceResponse;
import app.api.device.BOUpdateDeviceRequest;
import app.api.device.BOGetDeviceProcurementResponse;
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
public interface BODeviceWebService {
    @POST
    @Path("/bo/device-procurement")
    @ResponseStatus(HTTPStatus.CREATED)
    BOCreateDeviceProcurementResponse create(BOCreateDeviceProcurementRequest request);

    @GET
    @Path("/bo/device-procurement/:id")
    BOGetDeviceProcurementResponse get(@PathParam("id") Long id);

    @PUT
    @Path("/bo/device")
    BOSearchDeviceResponse search(BOSearchDeviceRequest request);

    @PUT
    @Path("/bo/device/:id")
    void update(@PathParam("id") Long id, BOUpdateDeviceRequest request);
}
