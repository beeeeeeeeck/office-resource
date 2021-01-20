package app.notification;

import app.api.notification.interview.kafka.IntervieweeNotificationMessage;
import app.notification.kafka.IntervieweeNotificationHandler;
import core.framework.module.Module;

/**
 * @author beckl
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        configureKafka();
    }

    private void configureKafka() {
        kafka().uri(requiredProperty("sys.kafka.uri"));

        kafka().subscribe("interviewee-notification", IntervieweeNotificationMessage.class, bind(IntervieweeNotificationHandler.class));
        kafka().poolSize(2);
    }
}
