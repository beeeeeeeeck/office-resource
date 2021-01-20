package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author beckl
 */
public class BOSearchDeviceResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "devices")
    public List<BODeviceView> devices;

    public static class BODeviceView {
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

        @NotNull
        @Property(name = "purchased_date")
        public LocalDate purchasedDate;

        @Property(name = "expired_date")
        public LocalDate expiredDate;
    }
}
