package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author beckl
 */
public class SearchDeviceApplicationResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "applications")
    public List<DeviceApplicationView> applications;

    public static class DeviceApplicationView {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "purpose")
        public String purpose;

        @NotNull
        @Property(name = "device_id")
        public Long deviceId;

        @NotNull
        @Property(name = "device_name")
        public String deviceName;

        @NotNull
        @Property(name = "staff_id")
        public Long staffId;

        @NotNull
        @Property(name = "status")
        public DeviceApplicationStatusView status;
    }
}
