package app.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class BOCreateStaffResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @Property(name = "active")
    public Boolean active;
}
