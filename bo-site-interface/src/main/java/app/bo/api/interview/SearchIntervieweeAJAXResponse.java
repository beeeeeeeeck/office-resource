package app.bo.api.interview;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author beckl
 */
public class SearchIntervieweeAJAXResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "interviewees")
    public List<IntervieweeAJAXView> interviewees;

    public static class IntervieweeAJAXView {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "mobile_phone")
        public String mobilePhone;

        @NotNull
        @NotBlank
        @Property(name = "job_position")
        public String jobPosition;

        @NotNull
        @Property(name = "status")
        public InterviewStatusAJAXView status;
    }
}
