package app.api.interview;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Size;

/**
 * @author beckl
 */
public class UpdateInterviewRequest {
    @NotNull
    @NotBlank
    @Size(min = 1, max = 200)
    @Property(name = "comment")
    public String comment;
}
