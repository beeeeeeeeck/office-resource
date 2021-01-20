package app.bo.exception;

import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.ResponseStatus;
import core.framework.log.ErrorCode;
import core.framework.log.Severity;

/**
 * @author beckl
 */
@ResponseStatus(HTTPStatus.PRECONDITION_FAILED)
public class InvalidRequestValuesException extends RuntimeException implements ErrorCode {
    private static final long serialVersionUID = 6023220789750687941L;

    public InvalidRequestValuesException(String message) {
        super("Invalid Request Values - " + message);
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
