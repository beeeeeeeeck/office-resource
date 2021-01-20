package app.schedule.service;

import app.api.DeviceWebService;
import app.api.device.ListDeviceSummaryRequest;
import app.api.device.ListDeviceSummaryResponse;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class DeviceService {
    @Inject
    DeviceWebService deviceWebService;

    public ListDeviceSummaryResponse listDeviceSummaries(int skip, int limit) {
        ListDeviceSummaryRequest request = new ListDeviceSummaryRequest();
        request.skip = skip;
        request.limit = limit;
        return deviceWebService.listDeviceSummaries(request);
    }
}
