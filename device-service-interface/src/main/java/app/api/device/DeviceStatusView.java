package app.api.device;

import core.framework.api.json.Property;

/**
 * @author beckl
 */
public enum DeviceStatusView {
    @Property(name = "IN_STOCK")
    IN_STOCK,
    @Property(name = "IN_SERVE")
    IN_SERVE,
    @Property(name = "OUT_OF_SERVE")
    OUT_OF_SERVE
}
