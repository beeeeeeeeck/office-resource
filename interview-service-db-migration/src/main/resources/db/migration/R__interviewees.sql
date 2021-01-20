CREATE TABLE IF NOT EXISTS interviewees
(
    id              INT AUTO_INCREMENT,
    mobile_phone    VARCHAR(20)                  NOT NULL,
    password        VARCHAR(100)                 NOT NULL,
    password_salt   VARCHAR(50)                  NOT NULL,
    job_position    VARCHAR(100)                 NOT NULL,
    status          VARCHAR(20)                  NOT NULL,
    created_time    DATETIME                     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    DATETIME                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY `ix_interviewees_mobile_phone` (`mobile_phone`) USING BTREE,
    KEY `ix_interviewees_mobile_phone_status` (`mobile_phone`, `status`) USING BTREE
) ENGINE = InnoDB;
