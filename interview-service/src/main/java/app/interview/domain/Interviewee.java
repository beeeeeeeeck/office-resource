package app.interview.domain;

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
@Table(name = "interviewees")
public class Interviewee {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 20)
    @Column(name = "mobile_phone")
    public String mobilePhone;

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
    @Size(min = 1, max = 100)
    @Column(name = "job_position")
    public String jobPosition;

    @NotNull
    @Column(name = "status")
    public InterviewStatus status;

    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
