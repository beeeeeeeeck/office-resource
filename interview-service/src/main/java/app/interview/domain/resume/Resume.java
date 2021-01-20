package app.interview.domain.resume;

import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author beckl
 */
@Collection(name = "resumes")
public class Resume {
    @Id
    public ObjectId id;

    @NotNull
    @Field(name = "interviewee_id")
    public Long intervieweeId;

    @Field(name = "personal_info")
    public PersonalInfo personalInfo;

    @Field(name = "job_experiences")
    public List<JobExperience> jobExperiences;

    @Field(name = "project_experiences")
    public List<ProjectExperience> projectExperiences;
}
