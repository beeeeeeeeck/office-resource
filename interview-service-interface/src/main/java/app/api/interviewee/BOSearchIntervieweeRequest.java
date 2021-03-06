package app.api.interviewee;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class BOSearchIntervieweeRequest {
    @Property(name = "mobile_phone")
    public String mobilePhone;

    @Property(name = "status")
    public InterviewStatusView status;

    @NotNull
    @Property(name = "skip")
    public Integer skip = 0;

    @NotNull
    @Property(name = "limit")
    public Integer limit = 1000;
}
