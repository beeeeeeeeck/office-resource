package app.device.domain;

import core.framework.db.DBEnumValue;

/**
 * @author beckl
 */
public enum DeviceStatus {
    @DBEnumValue("IN_STOCK")
    IN_STOCK,
    @DBEnumValue("IN_SERVE")
    IN_SERVE,
    @DBEnumValue("OUT_OF_SERVE")
    OUT_OF_SERVE
}
