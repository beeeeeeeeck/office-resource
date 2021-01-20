package app.api.device;

import core.framework.api.json.Property;

/**
 * @author beckl
 */
public enum DeviceApplicationStatusAJAXView {
    @Property(name = "CREATED")
    CREATED,
    @Property(name = "APPROVED")
    APPROVED,
    @Property(name = "RETURNED")
    RETURNED,
    @Property(name = "CANCELLED")
    CANCELLED
}
