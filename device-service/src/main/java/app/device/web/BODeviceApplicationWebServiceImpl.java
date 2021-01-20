package app.device.web;

import app.api.BODeviceApplicationWebService;
import app.api.device.BOSearchDeviceApplicationRequest;
import app.api.device.BOSearchDeviceApplicationResponse;
import app.api.device.SearchDeviceApplicationRequest;
import app.api.device.SearchDeviceApplicationResponse;
import app.device.service.DeviceApplicationService;
import core.framework.inject.Inject;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class BODeviceApplicationWebServiceImpl implements BODeviceApplicationWebService {
    @Inject
    DeviceApplicationService deviceApplicationService;

    @Override
    public BOSearchDeviceApplicationResponse search(BOSearchDeviceApplicationRequest request) {
        SearchDeviceApplicationRequest searchDeviceApplicationRequest = new SearchDeviceApplicationRequest();
        searchDeviceApplicationRequest.name = request.name;
        searchDeviceApplicationRequest.staffId = request.staffId;
        searchDeviceApplicationRequest.deviceIdList = request.deviceIdList;
        searchDeviceApplicationRequest.status = request.status;
        searchDeviceApplicationRequest.skip = request.skip;
        searchDeviceApplicationRequest.limit = request.limit;
        SearchDeviceApplicationResponse searchDeviceApplicationResponse = deviceApplicationService.search(searchDeviceApplicationRequest);
        BOSearchDeviceApplicationResponse response = new BOSearchDeviceApplicationResponse();
        response.total = searchDeviceApplicationResponse.total;
        Optional.ofNullable(searchDeviceApplicationResponse.applications).ifPresent(applications -> response.applications = applications.stream().map(view -> {
            var applicationView = new BOSearchDeviceApplicationResponse.BODeviceApplicationView();
            applicationView.id = view.id;
            applicationView.deviceId = view.deviceId;
            applicationView.deviceName = view.deviceName;
            applicationView.purpose = view.purpose;
            applicationView.staffId = view.staffId;
            applicationView.status = view.status;
            return applicationView;
        }).collect(Collectors.toList()));
        return response;
    }

    @Override
    public void approve(Long id) {
        deviceApplicationService.approve(id);
    }
}
