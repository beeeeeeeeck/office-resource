package app.device.web;

import app.api.DeviceApplicationWebService;
import app.api.device.CreateDeviceApplicationRequest;
import app.api.device.CreateDeviceApplicationResponse;
import app.api.device.GetDeviceApplicationResponse;
import app.api.device.SearchDeviceApplicationRequest;
import app.api.device.SearchDeviceApplicationResponse;
import app.device.service.DeviceApplicationService;
import core.framework.inject.Inject;

/**
 * @author beckl
 */
public class DeviceApplicationWebServiceImpl implements DeviceApplicationWebService {
    @Inject
    DeviceApplicationService deviceApplicationService;

    @Override
    public CreateDeviceApplicationResponse create(CreateDeviceApplicationRequest request) {
        return deviceApplicationService.create(request);
    }

    @Override
    public GetDeviceApplicationResponse get(Long id) {
        return deviceApplicationService.get(id);
    }

    @Override
    public void returnDevice(Long id) {
        deviceApplicationService.returnDevice(id);
    }

    @Override
    public SearchDeviceApplicationResponse search(SearchDeviceApplicationRequest request) {
        return deviceApplicationService.search(request);
    }
}
