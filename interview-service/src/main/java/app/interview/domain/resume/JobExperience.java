package app.interview.domain.resume;

import core.framework.mongo.Field;

import java.time.LocalDateTime;

/**
 * @author beckl
 */
public class JobExperience {
    @Field(name = "company_name")
    public String companyName;

    @Field(name = "position")
    public String position;

    @Field(name = "description")
    public String description;

    @Field(name = "started_date")
    public LocalDateTime startedDate;

    @Field(name = "end_date")
    public LocalDateTime endDate;
}
