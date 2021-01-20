package app.bo.api.user;

import app.common.api.constant.PatternConstants;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;
import core.framework.api.validate.Size;

/**
 * @author beckl
 */
public class CreateUserAJAXRequest {
    @NotNull
    @NotBlank
    @Pattern(PatternConstants.USER_NAME_REGEX)
    @Size(min = 1, max = 100)
    @Property(name = "user_name")
    public String userName;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Property(name = "password")
    public String password;
}
