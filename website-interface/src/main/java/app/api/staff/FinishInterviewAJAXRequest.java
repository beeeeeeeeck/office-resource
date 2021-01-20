package app.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class FinishInterviewAJAXRequest {
    @NotNull
    @Property(name = "interview_id")
    public Long interviewId;

    @NotNull
    @NotBlank
    @Property(name = "comment")
    public String comment;
}
