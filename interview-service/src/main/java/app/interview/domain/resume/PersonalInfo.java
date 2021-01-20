package app.interview.domain.resume;

import core.framework.mongo.Field;

import java.time.LocalDateTime;

/**
 * @author beckl
 */
public class PersonalInfo {
    @Field(name = "first_name")
    public String firstName;

    @Field(name = "last_name")
    public String lastName;

    @Field(name = "dob")
    public LocalDateTime dob;
}
