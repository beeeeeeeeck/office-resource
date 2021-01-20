package app.interview.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author beckl
 */
@Table(name = "interviews")
public class Interview {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    @Column(name = "job_position")
    public String jobPosition;

    @Size(max = 200)
    @Column(name = "comment")
    public String comment;

    @NotNull
    @Column(name = "interviewee_id")
    public Long intervieweeId;

    @NotNull
    @Column(name = "staff_id")
    public Long staffId;

    @NotNull
    @Column(name = "appointed_time")
    public LocalDateTime appointedTime;

    @NotNull
    @Column(name = "completed")
    public Boolean completed;

    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
