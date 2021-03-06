package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class ApplyDeviceAJAXResponse {
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
    @Property(name = "staff_id")
    public Long staffId;

    @NotNull
    @Property(name = "status")
    public DeviceApplicationStatusAJAXView status;
}
