package app.notification.kafka;

import app.api.notification.interview.kafka.IntervieweeNotificationMessage;
import app.notification.service.IntervieweeService;
import core.framework.inject.Inject;
import core.framework.kafka.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

/**
 * @author beckl
 */
public class IntervieweeNotificationHandler implements MessageHandler<IntervieweeNotificationMessage> {
    private final Logger logger = LoggerFactory.getLogger(IntervieweeNotificationHandler.class);
    @Inject
    IntervieweeService intervieweeService;

    @Override
    public void handle(@Nullable String key, IntervieweeNotificationMessage value) {
        logger.info("Interviewee status changed : {} - {}", value.id, value.mobilePhone);
        intervieweeService.notifyInterviewee(value.mobilePhone, value.message);
    }
}
