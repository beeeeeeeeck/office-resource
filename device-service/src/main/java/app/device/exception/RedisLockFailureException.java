package app.device.exception;

import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.ResponseStatus;
import core.framework.log.ErrorCode;
import core.framework.log.Severity;

/**
 * @author beckl
 */
@ResponseStatus(HTTPStatus.CONFLICT)
public class RedisLockFailureException extends RuntimeException implements ErrorCode {
    private static final long serialVersionUID = -3240469740038894798L;

    public RedisLockFailureException(String message) {
        super("Redis Lock Failure - " + message);
    }

    @Override
    public String errorCode() {
        return "REDIS_LOCK_FAILURE";
    }

    @Override
    public Severity severity() {
        return Severity.WARN;
    }
}
