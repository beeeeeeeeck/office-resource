package app.api.user;

import app.common.api.constant.PatternConstants;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

/**
 * @author beckl
 */
public class BOUserLoginRequest {
    @NotNull
    @NotBlank
    @Pattern(PatternConstants.USER_NAME_REGEX)
    @Property(name = "user_name")
    public String userName;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
