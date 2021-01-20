package app.api.notification.interview.kafka;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

/**
 * @author beckl
 */
public class IntervieweeNotificationMessage {
    @NotNull(message = "id is required")
    @Property(name = "id")
    public Long id;

    @NotNull
    @NotBlank
    @Property(name = "mobile_phone")
    public String mobilePhone;

    @NotNull
    @NotBlank
    @Property(name = "message")
    public String message;
}