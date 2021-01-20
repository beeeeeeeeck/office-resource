package app.bo.service;

import app.api.BODeviceApplicationWebService;
import app.api.BODeviceWebService;
import app.api.device.BOCreateDeviceProcurementRequest;
import app.api.device.BOCreateDeviceProcurementResponse;
import app.api.device.BOSearchDeviceApplicationRequest;
import app.api.device.BOSearchDeviceApplicationResponse;
import app.api.device.BOSearchDeviceRequest;
import app.api.device.BOSearchDeviceResponse;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class DeviceService {
    @Inject
    BODeviceWebService deviceWebService;
    @Inject
    BODeviceApplicationWebService deviceApplicationWebService;

    public BOCreateDeviceProcurementResponse create(BOCreateDeviceProcurementRequest request) {
        return deviceWebService.create(request);
    }

    public BOSearchDeviceResponse search(BOSearchDeviceRequest request) {
        return deviceWebService.search(request);
    }

    public BOSearchDeviceApplicationResponse searchApplications(BOSearchDeviceApplicationRequest request) {
        return deviceApplicationWebService.search(request);
    }

    public void approveApplication(Long id) {
        deviceApplicationWebService.approve(id);
    }
}