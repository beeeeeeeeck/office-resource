package app.api.interviewee;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class BOUpdateIntervieweeRequest {
    @NotNull
    @Property(name = "status")
    public InterviewStatusView status;
}
