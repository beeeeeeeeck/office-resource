package app.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author beckl
 */
public class GetStaffResponse {
    @NotNull
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "email")
    public String email;

    @NotNull
    @NotBlank
    @Property(name = "first_name")
    public String firstName;

    @NotNull
    @NotBlank
    @Property(name = "last_name")
    public String lastName;

    @NotNull
    @Property(name = "dob")
    public LocalDate dob;

    @Property(name = "started_date")
    public LocalDate startedDate;

    @Property(name = "job_summaries")
    public List<JobSummaryView> jobSummaries;

    public static class JobSummaryView {
        @NotNull
        @NotBlank
        @Property(name = "id")
        public String id;

        @NotNull
        @NotBlank
        @Property(name = "title")
        public String title;

        @NotNull
        @NotBlank
        @Property(name = "summary")
        public String summary;
    }
}
