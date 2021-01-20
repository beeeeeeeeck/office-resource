package app.api.interview;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class BOSearchInterviewRequest {
    @Property(name = "job_position")
    public String jobPosition;

    @Property(name = "interviewee_id")
    public Long intervieweeId;

    @Property(name = "staff_id")
    public Long staffId;

    @Property(name = "is_completed")
    public Boolean isCompleted;

    @Property(name = "is_interviewee_assigned")
    public Boolean isIntervieweeAssigned;

    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;
}
