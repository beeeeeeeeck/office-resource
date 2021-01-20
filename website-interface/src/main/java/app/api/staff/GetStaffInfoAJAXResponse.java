package app.api.staff;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author beckl
 */
public class GetStaffInfoAJAXResponse {
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

    @NotNull
    @Property(name = "started_date")
    public LocalDate startedDate;

    @NotNull
    @Property(name = "devices")
    public List<AppliedDeviceAJAXView> devices;

    @Property(name = "job_summaries")
    public List<JobSummaryAJAXView> jobSummaries;

    public static class AppliedDeviceAJAXView {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @Property(name = "device_id")
        public Long deviceId;

        @NotNull
        @NotBlank
        @Property(name = "device_name")
        public String deviceName;

        @NotNull
        @NotBlank
        @Property(name = "purpose")
        public String purpose;
    }

    public static class JobSummaryAJAXView {
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
