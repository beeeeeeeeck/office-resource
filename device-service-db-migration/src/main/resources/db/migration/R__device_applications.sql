CREATE TABLE IF NOT EXISTS device_applications
(
    id                 INT AUTO_INCREMENT,
    purpose            VARCHAR(200)                NOT NULL,
    device_id          INT(10)                     NOT NULL,
    device_name        VARCHAR(100)                NOT NULL,
    staff_id           INT(10)                     NOT NULL,
    status             VARCHAR(20)                 NOT NULL,
    created_time       DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time       DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY `ix_device_applications_status` (`status`) USING BTREE,
    KEY `ix_device_applications_staff_id` (`staff_id`) USING BTREE,
    KEY `ix_device_applications_staff_device_status` (`device_name`, `staff_id`, `status`) USING BTREE
) ENGINE = InnoDB;