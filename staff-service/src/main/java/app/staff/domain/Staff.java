package app.staff.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author beckl
 */
@Table(name = "staffs")
public class Staff {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "email")
    public String email;

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
    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "first_name")
    public String firstName;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Column(name = "last_name")
    public String lastName;

    @NotNull
    @Column(name = "dob")
    public LocalDate dob;

    @NotNull
    @Column(name = "active")
    public Boolean active;

    @NotNull
    @Column(name = "started_date")
    public LocalDate startedDate;

    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
