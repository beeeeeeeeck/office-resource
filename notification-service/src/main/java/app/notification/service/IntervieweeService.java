package app.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author beckl
 */
public class IntervieweeService {
    private final Logger logger = LoggerFactory.getLogger(IntervieweeService.class);

    public void notifyInterviewee(String mobilePhone, String message) {
        logger.info("Sending SMS to interviewee's mobile phone ({}) - Your interview is just {} now.", mobilePhone, message);
    }
}
