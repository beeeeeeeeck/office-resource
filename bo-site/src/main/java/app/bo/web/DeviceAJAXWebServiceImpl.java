package app.bo.web;

import app.api.device.BOCreateDeviceProcurementRequest;
import app.api.device.BOCreateDeviceProcurementResponse;
import app.api.device.BOSearchDeviceApplicationRequest;
import app.api.device.BOSearchDeviceApplicationResponse;
import app.api.device.BOSearchDeviceRequest;
import app.api.device.BOSearchDeviceResponse;
import app.api.device.DeviceApplicationStatusView;
import app.api.device.DeviceStatusView;
import app.bo.api.DeviceAJAXWebService;
import app.bo.api.device.CreateDeviceAJAXRequest;
import app.bo.api.device.CreateDeviceAJAXResponse;
import app.bo.api.device.DeviceApplicationStatusAJAXView;
import app.bo.api.device.DeviceStatusAJAXView;
import app.bo.api.device.SearchDeviceAJAXRequest;
import app.bo.api.device.SearchDeviceAJAXResponse;
import app.bo.api.device.SearchDeviceApplicationAJAXRequest;
import app.bo.api.device.SearchDeviceApplicationAJAXResponse;
import app.bo.service.DeviceService;
import app.common.web.interceptor.LoginRequired;
import core.framework.inject.Inject;

import java.util.Optional;
import java.util.stream.Collectors;

import static app.bo.ajax.LoginController.SESSION_USER_LOGIN_KEY;

/**
 * @author beckl
 */
public class DeviceAJAXWebServiceImpl implements DeviceAJAXWebService {
    @Inject
    DeviceService deviceService;

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public CreateDeviceAJAXResponse create(CreateDeviceAJAXRequest request) {
        BOCreateDeviceProcurementRequest createDeviceRequest = new BOCreateDeviceProcurementRequest();
        createDeviceRequest.deviceName = request.deviceName;
        createDeviceRequest.quantity = request.quantity;
        createDeviceRequest.purchasedDate = request.purchasedDate;
        createDeviceRequest.expiredDate = request.expiredDate;
        BOCreateDeviceProcurementResponse createDeviceResponse = deviceService.create(createDeviceRequest);
        CreateDeviceAJAXResponse response = new CreateDeviceAJAXResponse();
        response.id = createDeviceResponse.id;
        return response;
    }

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public SearchDeviceAJAXResponse search(SearchDeviceAJAXRequest request) {
        BOSearchDeviceRequest searchDeviceRequest = new BOSearchDeviceRequest();
        searchDeviceRequest.name = request.name;
        if (request.status != null) {
            searchDeviceRequest.status = DeviceStatusView.valueOf(request.status.name());
        }
        searchDeviceRequest.isExpired = request.isExpired;
        searchDeviceRequest.skip = request.skip;
        searchDeviceRequest.limit = request.limit;
        BOSearchDeviceResponse searchDeviceResponse = deviceService.search(searchDeviceRequest);
        SearchDeviceAJAXResponse response = new SearchDeviceAJAXResponse();
        response.total = searchDeviceResponse.total;
        Optional.ofNullable(searchDeviceResponse.devices).ifPresent(devices -> response.devices = devices.stream().map(view -> {
            var deviceView = new SearchDeviceAJAXResponse.DeviceAJAXView();
            deviceView.id = view.id;
            deviceView.name = view.name;
            deviceView.status = DeviceStatusAJAXView.valueOf(view.status.name());
            deviceView.purchasedDate = view.purchasedDate;
            deviceView.expiredDate = view.expiredDate;
            return deviceView;
        }).collect(Collectors.toList()));
        return response;
    }

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public SearchDeviceApplicationAJAXResponse searchApplication(SearchDeviceApplicationAJAXRequest request) {
        BOSearchDeviceApplicationRequest searchDeviceApplicationRequest = new BOSearchDeviceApplicationRequest();
        searchDeviceApplicationRequest.name = request.name;
        if (request.status != null) {
            searchDeviceApplicationRequest.status = DeviceApplicationStatusView.valueOf(request.status.name());
        }
        searchDeviceApplicationRequest.staffId = request.staffId;
        searchDeviceApplicationRequest.skip = request.skip;
        searchDeviceApplicationRequest.limit = request.limit;
        BOSearchDeviceApplicationResponse searchDeviceApplicationResponse = deviceService.searchApplications(searchDeviceApplicationRequest);
        SearchDeviceApplicationAJAXResponse response = new SearchDeviceApplicationAJAXResponse();
        response.total = searchDeviceApplicationResponse.total;
        Optional.ofNullable(searchDeviceApplicationResponse.applications).ifPresent(applications -> response.applications = applications.stream().map(application -> {
            var applicationView = new SearchDeviceApplicationAJAXResponse.DeviceApplicationAJAXView();
            applicationView.id = application.id;
            applicationView.purpose = application.purpose;
            applicationView.deviceId = application.deviceId;
            applicationView.deviceName = application.deviceName;
            applicationView.staffId = application.staffId;
            applicationView.status = DeviceApplicationStatusAJAXView.valueOf(application.status.name());
            return applicationView;
        }).collect(Collectors.toList()));
        return response;
    }

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    @Override
    public void approveApplication(Long id) {
        deviceService.approveApplication(id);
    }
}
