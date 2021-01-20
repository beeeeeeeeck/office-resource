package app.interview.domain.resume;

import core.framework.mongo.Field;

/**
 * @author beckl
 */
public class ProjectExperience {
    @Field(name = "name")
    public String name;

    @Field(name = "description")
    public String description;
}
