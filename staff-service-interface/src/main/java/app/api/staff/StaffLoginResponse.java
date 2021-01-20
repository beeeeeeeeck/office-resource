package app.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class StaffLoginResponse {
    @NotNull
    @Property(name = "success")
    public Boolean success;

    @Property(name = "staff_id")
    public Long staffId;

    @Property(name = "error_message")
    public String errorMessage;
}
