package app.bo.api.interview;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class UpdateIntervieweeStatusAJAXRequest {
    @NotNull
    @Property(name = "status")
    public InterviewStatusAJAXView status;
}
