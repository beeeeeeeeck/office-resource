package app.api.interview;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author beckl
 */
public class BOSearchInterviewResponse {
    @NotNull
    @Property(name = "total")
    public Long total;

    @NotNull
    @Property(name = "interviews")
    public List<BOInterviewView> interviews;

    public static class BOInterviewView {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "job_position")
        public String jobPosition;

        @NotNull
        @Property(name = "interviewee_id")
        public Long intervieweeId;

        @NotNull
        @Property(name = "staff_id")
        public Long staffId;

        @NotNull
        @Property(name = "appointed_time")
        public LocalDateTime appointedTime;

        @NotNull
        @Property(name = "completed")
        public Boolean completed;

        @Property(name = "comment")
        public String comment;
    }
}
