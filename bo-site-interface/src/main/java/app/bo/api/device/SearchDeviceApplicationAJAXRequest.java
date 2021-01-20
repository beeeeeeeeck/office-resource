package app.bo.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class SearchDeviceApplicationAJAXRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "staff_id")
    public Long staffId;

    @Property(name = "status")
    public DeviceApplicationStatusAJAXView status;

    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;
}
