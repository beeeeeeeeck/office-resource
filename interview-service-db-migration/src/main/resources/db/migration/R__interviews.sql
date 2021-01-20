create TABLE IF NOT EXISTS interviews
(
    id                 INT AUTO_INCREMENT,
    interviewee_id     INT(10)                     NOT NULL,
    staff_id           INT(10)                     NOT NULL,
    job_position       VARCHAR(100)                NOT NULL,
    appointed_time     DATETIME                    NOT NULL,
    completed          TINYINT(1)                  NOT NULL DEFAULT 0,
    comment            VARCHAR(200)                NULL,
    created_time       DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time       DATETIME                    NOT NULL DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY `ix_interviews_staff_id` (`staff_id`) USING BTREE,
    KEY `ix_interviews_interviewee_staff` (`interviewee_id`, `staff_id`) USING BTREE
) ENGINE = InnoDB;