package app.exception;

import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.ResponseStatus;
import core.framework.log.ErrorCode;
import core.framework.log.Severity;

import java.io.Serial;

/**
 * @author beckl
 */
@ResponseStatus(HTTPStatus.FORBIDDEN)
public class ResourceNotAccessibleException extends RuntimeException implements ErrorCode {
    @Serial
    private static final long serialVersionUID = -4713345062533552859L;

    public ResourceNotAccessibleException() {
        super("Resource Not Accessible");
    }

    @Override
    public String errorCode() {
        return "INVALID_REQUEST_VALUES";
    }

    @Override
    public Severity severity() {
        return Severity.WARN;
    }
}
