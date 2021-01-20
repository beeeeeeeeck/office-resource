package app.bo.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;

/**
 * @author beckl
 */
public class CreateDeviceAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "device_name")
    public String deviceName;

    @NotNull
    @Min(1)
    @Property(name = "quantity")
    public Long quantity;

    @NotNull
    @Property(name = "purchased_date")
    public LocalDate purchasedDate;

    @Property(name = "expired_date")
    public LocalDate expiredDate;
}
