package app.device.domain;

import core.framework.db.DBEnumValue;

/**
 * @author beckl
 */
public enum DeviceApplicationStatus {
    @DBEnumValue("CREATED")
    CREATED,
    @DBEnumValue("APPROVED")
    APPROVED,
    @DBEnumValue("RETURNED")
    RETURNED,
    @DBEnumValue("CANCELLED")
    CANCELLED
}
