package app.device.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author beckl
 */
@Table(name = "devices")
public class Device {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "device_procurement_id")
    public Long deviceProcurementId;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "name")
    public String name;

    @NotNull
    @Column(name = "status")
    public DeviceStatus status;

    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
