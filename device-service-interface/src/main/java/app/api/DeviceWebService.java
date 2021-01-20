package app.api;

import app.api.device.ListDeviceSummaryRequest;
import app.api.device.ListDeviceSummaryResponse;
import app.api.device.SearchDeviceRequest;
import app.api.device.SearchDeviceResponse;
import core.framework.api.web.service.PUT;
import core.framework.api.web.service.Path;

/**
 * @author beckl
 */
public interface DeviceWebService {
    @PUT
    @Path("/device")
    SearchDeviceResponse search(SearchDeviceRequest request);

    @PUT
    @Path("/device-summary")
    ListDeviceSummaryResponse listDeviceSummaries(ListDeviceSummaryRequest request);
}
