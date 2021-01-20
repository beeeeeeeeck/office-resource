package app.api.interviewee;

import app.common.api.constant.PatternConstants;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;
import core.framework.api.validate.Size;

/**
 * @author beckl
 */
public class CreateIntervieweeRequest {
    @NotNull
    @NotBlank
    @Pattern(PatternConstants.MOBILE_PHONE_REGEX)
    @Property(name = "mobile_phone")
    public String mobilePhone;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Property(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Property(name = "job_position")
    public String jobPosition;
}
