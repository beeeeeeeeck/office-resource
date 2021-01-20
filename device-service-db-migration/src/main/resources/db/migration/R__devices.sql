CREATE TABLE IF NOT EXISTS devices
(
    id                         INT AUTO_INCREMENT,
    device_procurement_id      INT(10)                     NOT NULL,
    name                       VARCHAR(100)                NOT NULL,
    status                     VARCHAR(20)                 NOT NULL,
    created_time               DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time               DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY `ix_devices_name_status` (`name`, `status`) USING BTREE,
    KEY `ix_devices_status` (`status`) USING BTREE,
    KEY `ix_devices_device_procurement_id` (`device_procurement_id`) USING BTREE
) ENGINE = InnoDB;