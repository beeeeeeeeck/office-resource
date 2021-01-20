package app.service;

import app.api.DeviceApplicationWebService;
import app.api.DeviceWebService;
import app.api.device.CreateDeviceApplicationRequest;
import app.api.device.CreateDeviceApplicationResponse;
import app.api.device.SearchDeviceApplicationRequest;
import app.api.device.SearchDeviceApplicationResponse;
import app.api.device.SearchDeviceRequest;
import app.api.device.SearchDeviceResponse;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class DeviceService {
    @Inject
    DeviceWebService deviceWebService;
    @Inject
    DeviceApplicationWebService deviceApplicationWebService;

    public SearchDeviceResponse searchAvailableDevices(SearchDeviceRequest request) {
        return deviceWebService.search(request);
    }

    public CreateDeviceApplicationResponse applyDevice(CreateDeviceApplicationRequest request) {
        return deviceApplicationWebService.create(request);
    }

    public SearchDeviceApplicationResponse searchDevice4Staff(SearchDeviceApplicationRequest request) {
        return deviceApplicationWebService.search(request);
    }
}
