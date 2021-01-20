package app.api.interview;

import app.api.interview.resume.JobExperienceAJAXView;
import app.api.interview.resume.ProjectExperienceAJAXView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author beckl
 */
public class GetIntervieweeResumeAJAXResponse {
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
    @Property(name = "job_position")
    public String jobPosition;

    @Property(name = "job_experiences")
    public List<JobExperienceAJAXView> jobExperiences;

    @Property(name = "project_experiences")
    public List<ProjectExperienceAJAXView> projectExperiences;
}
