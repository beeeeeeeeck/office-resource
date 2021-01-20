package app.api.interview;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;

import java.time.LocalDateTime;

/**
 * @author beckl
 */
public class BOCreateInterviewRequest {
    @Size(min = 1, max = 100)
    @Property(name = "job_position")
    public String jobPosition;

    @NotNull
    @Property(name = "interviewee_id")
    public Long intervieweeId;

    @NotNull
    @Property(name = "staff_id")
    public Long staffId;

    @NotNull
    @Property(name = "appointed_time")
    public LocalDateTime appointedTime;
}
