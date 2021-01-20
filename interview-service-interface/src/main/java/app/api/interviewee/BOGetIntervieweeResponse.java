package app.api.interviewee;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class BOGetIntervieweeResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Property(name = "password_salt")
    public String passwordSalt;

    @NotNull
    @Property(name = "status")
    public InterviewStatusView status;
}
