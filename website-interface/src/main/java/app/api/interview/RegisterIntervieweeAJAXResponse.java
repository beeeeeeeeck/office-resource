package app.api.interview;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class RegisterIntervieweeAJAXResponse {
    @NotNull
    @Property(name = "interviewee_id")
    public Long intervieweeId;
}
