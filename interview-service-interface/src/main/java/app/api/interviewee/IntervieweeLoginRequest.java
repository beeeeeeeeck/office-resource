package app.api.interviewee;

import app.common.api.constant.PatternConstants;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

/**
 * @author beckl
 */
public class IntervieweeLoginRequest {
    @NotNull
    @NotBlank
    @Pattern(PatternConstants.MOBILE_PHONE_REGEX)
    @Property(name = "mobile_phone")
    public String mobilePhone;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;
}
