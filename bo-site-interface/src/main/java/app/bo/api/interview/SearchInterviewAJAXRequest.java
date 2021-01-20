package app.bo.api.interview;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class SearchInterviewAJAXRequest {
    @Property(name = "staff_id")
    public Long staffId;

    @Property(name = "is_completed")
    public Boolean isCompleted;

    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;
}
