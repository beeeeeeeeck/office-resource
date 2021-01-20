package app.bo.api.staff;

import app.common.api.constant.PatternConstants;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

import java.time.LocalDate;

/**
 * @author beckl
 */
public class CreateStaffAJAXRequest {
    @NotNull
    @Property(name = "interviewee_id")
    public Long intervieweeId;

    @NotNull
    @NotBlank
    @Pattern(PatternConstants.EMAIL_REGEX)
    @Property(name = "email")
    public String email;

    @NotNull
    @Property(name = "started_date")
    public LocalDate startedDate;
}
