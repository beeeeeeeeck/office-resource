CREATE TABLE IF NOT EXISTS users
(
    id              INT AUTO_INCREMENT,
    user_name       VARCHAR(100)                 NOT NULL,
    password        VARCHAR(100)                 NOT NULL,
    password_salt   VARCHAR(50)                  NOT NULL,
    active          TINYINT(1)                   NOT NULL,
    created_time    DATETIME                     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_time    DATETIME                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY `ix_staffs_user_name` (`user_name`) USING BTREE
) ENGINE = InnoDB;

-- Admin initialization with password
SELECT COUNT(*) INTO @initialAdminCount FROM `users` WHERE user_name = 'admin';
SET @SQL = IF(@initialAdminCount = 0, "INSERT INTO `users` (`user_name`, `password`, `password_salt`, `active`) VALUES ('admin', '26bzROcwznEoya0Gh8C3o/G/755dHWPwe1gy88ftejrCgIk+fxMt6Rxj7P0nnu/oLr10HH09om8cVDWOnEQ6lg==', 'hs0GOCFVawvaLcDVQxfj', 1);", 'SELECT 1;');
PREPARE addInitialAdminStatement FROM @SQL;
EXECUTE addInitialAdminStatement;