package app.bo.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class UpdateStaffAJAXRequest {
    @NotNull
    @Property(name = "active")
    public Boolean active;
}
