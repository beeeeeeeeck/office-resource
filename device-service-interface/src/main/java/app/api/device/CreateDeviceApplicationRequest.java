package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;

/**
 * @author beckl
 */
public class CreateDeviceApplicationRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 200)
    @Property(name = "purpose")
    public String purpose;

    @NotNull
    @Property(name = "device_id")
    public Long deviceId;

    @NotNull
    @Property(name = "staff_id")
    public Long staffId;
}
