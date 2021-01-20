CREATE TABLE IF NOT EXISTS staffs
(
    id              INT AUTO_INCREMENT,
    email           VARCHAR(100)                 NOT NULL,
    password        VARCHAR(100)                 NOT NULL,
    password_salt   VARCHAR(50)                  NOT NULL,
    first_name      VARCHAR(50)                  NOT NULL,
    last_name       VARCHAR(50)                  NOT NULL,
    dob             DATETIME                     NOT NULL,
    active          TINYINT(1)                   NOT NULL,
    started_date    DATETIME                     NOT NULL,
    created_time    DATETIME                     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    DATETIME                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY `ix_staffs_email` (`email`) USING BTREE
) ENGINE = InnoDB;