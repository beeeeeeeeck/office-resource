package app.user.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author beckl
 */
@Table(name = "users")
public class User {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "user_name")
    public String userName;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "password_salt")
    public String passwordSalt;

    @NotNull
    @Column(name = "active")
    public Boolean active;

    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
