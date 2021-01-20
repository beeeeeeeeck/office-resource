package app.device.web;

import app.api.DeviceWebService;
import app.api.device.ListDeviceSummaryRequest;
import app.api.device.ListDeviceSummaryResponse;
import app.api.device.SearchDeviceRequest;
import app.api.device.SearchDeviceResponse;
import app.device.service.DeviceService;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class DeviceWebServiceImpl implements DeviceWebService {
    @Inject
    DeviceService deviceService;

    @Override
    public SearchDeviceResponse search(SearchDeviceRequest request) {
        return deviceService.search(request);
    }

    @Override
    public ListDeviceSummaryResponse listDeviceSummaries(ListDeviceSummaryRequest request) {
        return deviceService.listDeviceSummaries(request);
    }
}
