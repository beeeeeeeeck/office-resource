package app.device;

import app.api.BODeviceApplicationWebService;
import app.api.BODeviceWebService;
import app.api.DeviceApplicationWebService;
import app.api.DeviceWebService;
import app.device.domain.Device;
import app.device.domain.DeviceApplication;
import app.device.domain.DeviceProcurement;
import app.device.domain.DeviceSummary;
import app.device.service.DeviceApplicationService;
import app.device.service.DeviceService;
import app.device.service.RedisService;
import app.device.web.BODeviceApplicationWebServiceImpl;
import app.device.web.BODeviceWebServiceImpl;
import app.device.web.DeviceApplicationWebServiceImpl;
import app.device.web.DeviceWebServiceImpl;
import core.framework.module.Module;

/**
 * @author beckl
 */
public class DeviceModule extends Module {
    @Override
    protected void initialize() {
        db().repository(DeviceProcurement.class);
        db().repository(Device.class);
        db().repository(DeviceApplication.class);
        db().view(DeviceSummary.class);

        bind(RedisService.class);
        bind(DeviceService.class);
        bind(DeviceApplicationService.class);

        api().service(DeviceWebService.class, bind(DeviceWebServiceImpl.class));
        api().service(DeviceApplicationWebService.class, bind(DeviceApplicationWebServiceImpl.class));
        api().service(BODeviceWebService.class, bind(BODeviceWebServiceImpl.class));
        api().service(BODeviceApplicationWebService.class, bind(BODeviceApplicationWebServiceImpl.class));
    }
}
