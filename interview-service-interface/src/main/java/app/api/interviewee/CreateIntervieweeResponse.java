package app.api.interviewee;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class CreateIntervieweeResponse {
    @NotNull
    @Property(name = "interviewee_id")
    public Long intervieweeId;

    @NotNull
    @Property(name = "status")
    public InterviewStatusView status;
}
