package app.api.staff;

import app.common.api.constant.PatternConstants;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

/**
 * @author beckl
 */
public class StaffLoginRequest {
    @NotNull
    @NotBlank
    @Pattern(PatternConstants.EMAIL_REGEX)
    @Property(name = "email")
    public String email;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
