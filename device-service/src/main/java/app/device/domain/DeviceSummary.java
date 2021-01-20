package app.device.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;

import java.time.LocalDate;

/**
 * @author beckl
 */
public class DeviceSummary {
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Column(name = "device_name")
    public String deviceName;

    @NotNull
    @Column(name = "quantity")
    public Long quantity;

    @NotNull
    @Column(name = "quantity_in_stock")
    public Long quantityInStock;

    @NotNull
    @Column(name = "purchased_date")
    public LocalDate purchasedDate;

    @Column(name = "expired_date")
    public LocalDate expiredDate;
}
