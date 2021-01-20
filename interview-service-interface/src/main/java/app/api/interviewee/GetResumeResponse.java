package app.api.interviewee;

import app.api.interviewee.resume.JobExperienceView;
import app.api.interviewee.resume.ProjectExperienceView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * @author beckl
 */
public class GetResumeResponse {
    @NotNull
    @Property(name = "interviewee_id")
    public Long intervieweeId;

    @NotNull
    @NotBlank
    @Property(name = "job_position")
    public String jobPosition;

    @NotNull
    @NotBlank
    @Property(name = "resume_id")
    public String resumeId;

    @NotNull
    @NotBlank
    @Property(name = "first_name")
    public String firstName;

    @NotNull
    @NotBlank
    @Property(name = "last_name")
    public String lastName;

    @NotNull
    @NotBlank
    @Property(name = "mobile_phone")
    public String mobilePhone;

    @NotNull
    @Property(name = "dob")
    public LocalDate dob;

    @Property(name = "job_experiences")
    public List<JobExperienceView> jobExperiences;

    @Property(name = "project_experiences")
    public List<ProjectExperienceView> projectExperiences;
}
