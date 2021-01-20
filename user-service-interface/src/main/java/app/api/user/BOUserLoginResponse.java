package app.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class BOUserLoginResponse {
    @NotNull
    @Property(name = "success")
    public Boolean success;

    @Property(name = "user_id")
    public Long userId;

    @Property(name = "error_message")
    public String errorMessage;
}
