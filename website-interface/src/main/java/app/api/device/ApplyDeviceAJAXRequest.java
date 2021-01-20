package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class ApplyDeviceAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "purpose")
    public String purpose;

    @NotNull
    @Property(name = "device_id")
    public Long deviceId;
}
