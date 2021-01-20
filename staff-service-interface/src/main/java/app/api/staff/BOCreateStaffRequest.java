package app.api.staff;

import app.common.api.constant.PatternConstants;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;
import core.framework.api.validate.Size;

import java.time.LocalDate;

/**
 * @author beckl
 */
public class BOCreateStaffRequest {
    @NotNull
    @NotBlank
    @Pattern(PatternConstants.EMAIL_REGEX)
    @Size(min = 1, max = 100)
    @Property(name = "email")
    public String email;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Property(name = "password")
    public String password;

    @Property(name = "password_salt")
    public String passwordSalt;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Property(name = "first_name")
    public String firstName;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    @Property(name = "last_name")
    public String lastName;

    @NotNull
    @Property(name = "dob")
    public LocalDate dob;

    @NotNull
    @Property(name = "started_date")
    public LocalDate startedDate;
}
