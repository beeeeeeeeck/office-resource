CREATE TABLE IF NOT EXISTS device_procurements
(
    id                         INT AUTO_INCREMENT,
    device_name                VARCHAR(100)                NOT NULL,
    quantity                   INT(5)                      NOT NULL,
    purchased_date             DATETIME                    NOT NULL,
    expired_date               DATETIME                    NULL,
    created_time               DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time               DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY `ix_device_procurements_device_name` (`device_name`) USING BTREE
) ENGINE = InnoDB;