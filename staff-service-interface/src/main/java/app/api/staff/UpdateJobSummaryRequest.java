package app.api.staff;

import core.framework.api.json.Property;

/**
 * @author beckl
 */
public class UpdateJobSummaryRequest {
    @Property(name = "title")
    public String title;

    @Property(name = "summary")
    public String summary;
}
