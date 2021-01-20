package app.device.exception;

import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.ResponseStatus;
import core.framework.log.ErrorCode;
import core.framework.log.Severity;

import java.io.Serial;

/**
 * @author beckl
 */
@ResponseStatus(HTTPStatus.PRECONDITION_FAILED)
public class DeviceUnavailableException extends RuntimeException implements ErrorCode {
    @Serial
    private static final long serialVersionUID = 3861134413792902361L;

    public DeviceUnavailableException(String name) {
        super(name + " is unavailable");
    }

    @Override
    public String errorCode() {
        return "DEVICE_UNAVAILABLE";
    }

    @Override
    public Severity severity() {
        return Severity.WARN;
    }
}
