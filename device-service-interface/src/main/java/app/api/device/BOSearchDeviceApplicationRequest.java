package app.api.device;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author beckl
 */
public class BOSearchDeviceApplicationRequest {
    @Property(name = "name")
    public String name;

    @Property(name = "device_id_list")
    public List<Long> deviceIdList;

    @Property(name = "staff_id")
    public Long staffId;

    @Property(name = "status")
    public DeviceApplicationStatusView status;

    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;
}
