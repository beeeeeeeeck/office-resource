package app.web;

import app.api.DeviceAJAXWebService;
import app.api.device.ApplyDeviceAJAXRequest;
import app.api.device.ApplyDeviceAJAXResponse;
import app.api.device.CreateDeviceApplicationRequest;
import app.api.device.CreateDeviceApplicationResponse;
import app.api.device.DeviceApplicationStatusAJAXView;
import app.api.device.DeviceStatusAJAXView;
import app.api.device.DeviceStatusView;
import app.api.device.SearchAvailableDeviceAJAXRequest;
import app.api.device.SearchAvailableDeviceAJAXResponse;
import app.api.device.SearchDeviceRequest;
import app.api.device.SearchDeviceResponse;
import app.common.web.interceptor.LoginRequired;
import app.exception.ResourceNotAccessibleException;
import app.service.DeviceService;
import app.service.SessionService;
import core.framework.inject.Inject;

import java.util.Optional;
import java.util.stream.Collectors;

import static app.service.SessionService.SESSION_LOGIN_STAFF_ID_KEY;

/**
 * @author beckl
 */
public class DeviceAJAXWebServiceImpl implements DeviceAJAXWebService {
    @Inject
    DeviceService deviceService;
    @Inject
    SessionService sessionService;

    @LoginRequired(SESSION_LOGIN_STAFF_ID_KEY)
    @Override
    public SearchAvailableDeviceAJAXResponse searchAvailableDevices(SearchAvailableDeviceAJAXRequest request) {
        SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
        searchDeviceRequest.name = request.name;
        searchDeviceRequest.status = DeviceStatusView.IN_STOCK;
        searchDeviceRequest.isExpired = Boolean.FALSE;
        searchDeviceRequest.skip = request.skip;
        searchDeviceRequest.limit = request.limit;
        SearchDeviceResponse searchDeviceResponse = deviceService.searchAvailableDevices(searchDeviceRequest);
        SearchAvailableDeviceAJAXResponse response = new SearchAvailableDeviceAJAXResponse();
        response.total = searchDeviceResponse.total;
        Optional.ofNullable(searchDeviceResponse.devices).ifPresent(devices -> response.devices = devices.stream().map(view -> {
            var deviceView = new SearchAvailableDeviceAJAXResponse.DeviceAJAXView();
            deviceView.id = view.id;
            deviceView.name = view.name;
            deviceView.status = DeviceStatusAJAXView.valueOf(view.status.name());
            deviceView.purchasedDate = view.purchasedDate;
            deviceView.expiredDate = view.expiredDate;
            return deviceView;
        }).collect(Collectors.toList()));
        return response;
    }

    @LoginRequired(SESSION_LOGIN_STAFF_ID_KEY)
    @Override
    public ApplyDeviceAJAXResponse applyDevice(ApplyDeviceAJAXRequest request) {
        String staffIdValue = sessionService.getLoginStaffId().orElseThrow(ResourceNotAccessibleException::new);
        CreateDeviceApplicationRequest createDeviceApplicationRequest = new CreateDeviceApplicationRequest();
        createDeviceApplicationRequest.purpose = request.purpose;
        createDeviceApplicationRequest.deviceId = request.deviceId;
        createDeviceApplicationRequest.staffId = Long.valueOf(staffIdValue);
        CreateDeviceApplicationResponse createDeviceApplicationResponse = deviceService.applyDevice(createDeviceApplicationRequest);
        ApplyDeviceAJAXResponse response = new ApplyDeviceAJAXResponse();
        response.id = createDeviceApplicationResponse.id;
        response.purpose = createDeviceApplicationResponse.purpose;
        response.deviceId = createDeviceApplicationResponse.deviceId;
        response.staffId = createDeviceApplicationResponse.staffId;
        response.status = DeviceApplicationStatusAJAXView.valueOf(createDeviceApplicationResponse.status.name());
        return response;
    }
}
