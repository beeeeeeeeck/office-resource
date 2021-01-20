package app.device.domain;

import core.framework.api.validate.Min;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author beckl
 */
@Table(name = "device_procurements")
public class DeviceProcurement {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "device_name")
    public String deviceName;

    @NotNull
    @Min(1)
    @Column(name = "quantity")
    public Long quantity;

    @NotNull
    @Column(name = "purchased_date")
    public LocalDate purchasedDate;

    @Column(name = "expired_date")
    public LocalDate expiredDate;

    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
