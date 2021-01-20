package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author beckl
 */
public class BOGetDeviceProcurementResponse {
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "device_name")
    public String deviceName;

    @NotNull
    @Property(name = "quantity")
    public Long quantity;

    @NotNull
    @Property(name = "purchased_date")
    public LocalDate purchasedDate;

    @Property(name = "expired_date")
    public LocalDate expiredDate;

    @NotNull
    @Property(name = "devices")
    public List<DeviceView> devices;

    public static class DeviceView {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "name")
        public String name;

        @NotNull
        @Property(name = "status")
        public DeviceStatusView status;
    }
}
