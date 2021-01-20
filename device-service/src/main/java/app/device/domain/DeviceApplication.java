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
@Table(name = "device_applications")
public class DeviceApplication {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "device_id")
    public Long deviceId;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "device_name")
    public String deviceName;

    @NotNull
    @Column(name = "staff_id")
    public Long staffId;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 200)
    @Column(name = "purpose")
    public String purpose;

    @NotNull
    @Column(name = "status")
    public DeviceApplicationStatus status;

    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
