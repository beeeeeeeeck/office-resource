package app.common.web.interceptor;

import core.framework.web.Interceptor;
import core.framework.web.Invocation;
import core.framework.web.Response;
import core.framework.web.Session;
import core.framework.web.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author beckl
 */
public class LoginVerificationInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(LoginVerificationInterceptor.class);

    @Override
    public Response intercept(Invocation invocation) throws Exception {
        LoginRequired annotation = invocation.annotation(LoginRequired.class);
        if (annotation != null) {
            logger.info("Invocation is marked as login required with key - {}", annotation.value());
            Session session = invocation.context().request().session();
            session.get(annotation.value()).orElseThrow(() -> new ForbiddenException("Access Denied"));
        }
        return invocation.proceed();
    }
}
