package app.common.web.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author beckl
 */
@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface LoginRequired {
    String value();
}
