package app.interview;

import app.api.BOInterviewWebService;
import app.api.BOIntervieweeWebService;
import app.api.InterviewWebService;
import app.api.IntervieweeWebService;
import app.api.notification.interview.kafka.IntervieweeNotificationMessage;
import app.interview.domain.Interview;
import app.interview.domain.Interviewee;
import app.interview.domain.resume.Resume;
import app.interview.service.InterviewService;
import app.interview.service.IntervieweeService;
import app.interview.web.BOInterviewWebServiceImpl;
import app.interview.web.BOIntervieweeWebServiceImpl;
import app.interview.web.InterviewWebServiceImpl;
import app.interview.web.IntervieweeWebServiceImpl;
import core.framework.module.Module;
import core.framework.mongo.module.MongoConfig;

/**
 * @author beckl
 */
public class InterviewModule extends Module {
    @Override
    protected void initialize() {
        db().repository(Interviewee.class);
        db().repository(Interview.class);

        configureMongoDB();

        bind(IntervieweeService.class);
        bind(InterviewService.class);

        api().service(IntervieweeWebService.class, bind(IntervieweeWebServiceImpl.class));
        api().service(InterviewWebService.class, bind(InterviewWebServiceImpl.class));
        api().service(BOIntervieweeWebService.class, bind(BOIntervieweeWebServiceImpl.class));
        api().service(BOInterviewWebService.class, bind(BOInterviewWebServiceImpl.class));

        configureKafka();
    }

    private void configureMongoDB() {
        MongoConfig mongoConfig = config(MongoConfig.class);
        mongoConfig.uri(requiredProperty("sys.mongo.uri"));
        mongoConfig.collection(Resume.class);
    }

    private void configureKafka() {
        kafka().uri(requiredProperty("sys.kafka.uri"));
        kafka().publish("interviewee-notification", IntervieweeNotificationMessage.class);
    }
}
