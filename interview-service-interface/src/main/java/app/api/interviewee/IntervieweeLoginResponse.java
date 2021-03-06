package app.api.interviewee;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class IntervieweeLoginResponse {
    @NotNull
    @Property(name = "success")
    public Boolean success;

    @Property(name = "interviewee_id")
    public Long intervieweeId;

    @Property(name = "error_message")
    public String errorMessage;
}
