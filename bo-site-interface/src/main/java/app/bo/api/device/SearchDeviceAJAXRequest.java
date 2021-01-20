package app.bo.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class SearchDeviceAJAXRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "status")
    public DeviceStatusAJAXView status;

    @Property(name = "is_expired")
    public Boolean isExpired;

    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;
}
