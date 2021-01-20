package app.staff.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author beckl
 */
@Collection(name = "job_summaries")
public class JobSummary {
    @Id
    public ObjectId id;

    @NotNull
    @Field(name = "staff_id")
    public Long staffId;

    @NotNull
    @NotBlank
    @Field(name = "title")
    public String title;

    @NotNull
    @NotBlank
    @Field(name = "summary")
    public String summary;

    @Field(name = "created_time")
    public LocalDateTime createdTime;

    @Field(name = "updated_time")
    public LocalDateTime updatedTime;
}
