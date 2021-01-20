package app.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class CreateJobSummaryAJAXResponse {
    @NotNull
    @NotBlank
    @Property(name = "job_summary_id")
    public String jobSummaryId;
}
