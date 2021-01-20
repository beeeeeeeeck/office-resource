package app.api.interviewee;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class SaveResumeResponse {
    @NotNull
    @Property(name = "interviewee_id")
    public Long intervieweeId;

    @NotNull
    @NotBlank
    @Property(name = "resume_id")
    public String resumeId;
}
