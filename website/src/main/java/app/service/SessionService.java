package app.service;

import core.framework.inject.Inject;
import core.framework.web.WebContext;

import java.util.Optional;

/**
 * @author beckl
 */
public class SessionService {
    public static final String SESSION_LOGIN_STAFF_ID_KEY = "__LOGIN_STAFF_ID";
    public static final String SESSION_LOGIN_INTERVIEWEE_ID_KEY = "__LOGIN_INTERVIEWEE_ID";

    @Inject
    WebContext webContext;

    public Optional<String> getLoginStaffId() {
        return webContext.request().session().get(SESSION_LOGIN_STAFF_ID_KEY);
    }

    public Optional<String> getLoginIntervieweeId() {
        return webContext.request().session().get(SESSION_LOGIN_INTERVIEWEE_ID_KEY);
    }
}
