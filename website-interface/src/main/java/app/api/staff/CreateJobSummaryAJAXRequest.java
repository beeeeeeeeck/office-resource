package app.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class CreateJobSummaryAJAXRequest {
    @NotNull
    @NotBlank
    @Property(name = "title")
    public String title;

    @NotNull
    @NotBlank
    @Property(name = "summary")
    public String summary;
}
