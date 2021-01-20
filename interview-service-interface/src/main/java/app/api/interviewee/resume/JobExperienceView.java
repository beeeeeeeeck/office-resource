package app.api.interviewee.resume;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;

/**
 * @author beckl
 */
public class JobExperienceView {
    @NotNull
    @NotBlank
    @Property(name = "company_name")
    public String companyName;

    @NotNull
    @NotBlank
    @Property(name = "position")
    public String position;

    @NotNull
    @NotBlank
    @Property(name = "description")
    public String description;

    @NotNull
    @Property(name = "started_date")
    public LocalDateTime startedDate;

    @NotNull
    @Property(name = "end_date")
    public LocalDateTime endDate;
}
