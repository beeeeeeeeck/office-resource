package app.bo.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class CreateUserAJAXResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @Property(name = "active")
    public Boolean active;
}
