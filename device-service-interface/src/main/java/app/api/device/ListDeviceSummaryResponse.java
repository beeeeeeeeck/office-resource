package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author beckl
 */
public class ListDeviceSummaryResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "device_summaries")
    public List<DeviceSummaryView> deviceSummaries;

    public static class DeviceSummaryView {
        @NotNull
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
        @Property(name = "quantity_in_stock")
        public Long quantityInStock;

        @NotNull
        @Property(name = "purchased_date")
        public LocalDate purchasedDate;

        @Property(name = "expired_date")
        public LocalDate expiredDate;
    }
}