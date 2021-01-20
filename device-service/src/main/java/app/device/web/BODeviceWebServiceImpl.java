package app.device.web;

import app.api.BODeviceWebService;
import app.api.device.BOCreateDeviceProcurementRequest;
import app.api.device.BOCreateDeviceProcurementResponse;
import app.api.device.BOGetDeviceProcurementResponse;
import app.api.device.BOSearchDeviceRequest;
import app.api.device.BOSearchDeviceResponse;
import app.api.device.BOUpdateDeviceRequest;
import app.api.device.SearchDeviceRequest;
import app.api.device.SearchDeviceResponse;
import app.device.service.DeviceService;
import core.framework.inject.Inject;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class BODeviceWebServiceImpl implements BODeviceWebService {
    @Inject
    DeviceService deviceService;

    @Override
    public BOCreateDeviceProcurementResponse create(BOCreateDeviceProcurementRequest request) {
        return deviceService.create(request);
    }

    @Override
    public BOGetDeviceProcurementResponse get(Long id) {
        return deviceService.get(id);
    }

    @Override
    public BOSearchDeviceResponse search(BOSearchDeviceRequest request) {
        SearchDeviceRequest searchDeviceRequest = new SearchDeviceRequest();
        searchDeviceRequest.name = request.name;
        searchDeviceRequest.status = request.status;
        searchDeviceRequest.isExpired = request.isExpired;
        searchDeviceRequest.skip = request.skip;
        searchDeviceRequest.limit = request.limit;
        SearchDeviceResponse searchDeviceResponse = deviceService.search(searchDeviceRequest);
        BOSearchDeviceResponse response = new BOSearchDeviceResponse();
        response.total = searchDeviceResponse.total;
        Optional.ofNullable(searchDeviceResponse.devices).ifPresent(devices -> response.devices = devices.stream().map(view -> {
            var deviceView = new BOSearchDeviceResponse.BODeviceView();
            deviceView.id = view.id;
            deviceView.name = view.name;
            deviceView.status = view.status;
            deviceView.purchasedDate = view.purchasedDate;
            deviceView.expiredDate = view.expiredDate;
            return deviceView;
        }).collect(Collectors.toList()));
        return response;
    }

    @Override
    public void update(Long id, BOUpdateDeviceRequest request) {
        deviceService.update(id, request);
    }
}
