package app.api.interviewee;

import app.api.interviewee.resume.JobExperienceView;
import app.api.interviewee.resume.ProjectExperienceView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;

import java.time.LocalDate;
import java.util.List;

/**
 * @author beckl
 */
public class SaveResumeRequest {
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

    @Size(min = 0, max = 10)
    @Property(name = "job_experiences")
    public List<JobExperienceView> jobExperiences;

    @Size(min = 0, max = 20)
    @Property(name = "project_experiences")
    public List<ProjectExperienceView> projectExperiences;
}
