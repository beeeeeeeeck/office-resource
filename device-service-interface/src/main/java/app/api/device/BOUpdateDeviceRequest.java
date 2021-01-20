package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class BOUpdateDeviceRequest {
    @NotNull
    @Property(name = "status")
    public DeviceStatusView status;
}
