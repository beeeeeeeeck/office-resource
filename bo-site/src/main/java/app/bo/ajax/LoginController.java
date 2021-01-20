package app.bo.ajax;

import app.api.user.BOUserLoginRequest;
import app.api.user.BOUserLoginResponse;
import app.bo.service.UserService;
import app.common.web.interceptor.LoginRequired;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import core.framework.web.Request;
import core.framework.web.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

/**
 * @author beckl
 */
public class LoginController {
    public static final String SESSION_USER_LOGIN_KEY = "__IS_USER_LOGIN";
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Inject
    UserService userService;

    public Response login(Request request) {
        Optional<String> authorization = request.header("Authorization");
        if (authorization.isEmpty()) {
            return Response.text("authorization should not be empty");
        }
        String[] authorizationElements = authorization.get().split(" ");
        if (authorizationElements.length != 2 && Strings.isBlank(authorizationElements[1])) {
            return Response.text("authorization should be valid");
        }
        byte[] decodedBytes = Base64.getDecoder().decode(authorizationElements[1]);
        String authorizationString = new String(decodedBytes, StandardCharsets.UTF_8);
        String[] userAttributes = authorizationString.split(":");
        if (userAttributes.length != 2) {
            return Response.text("authorization should be valid");
        }
        String userName = userAttributes[0];
        String password = userAttributes[1];
        if (Strings.isBlank(userName) || Strings.isBlank(password)) {
            return Response.text("email and password are both required for user login");
        }
        BOUserLoginRequest userLoginRequest = new BOUserLoginRequest();
        userLoginRequest.userName = userName;
        userLoginRequest.password = password;
        BOUserLoginResponse userLoginResponse = userService.login(userLoginRequest);
        if (userLoginResponse.success && userLoginResponse.userId != null) {
            request.session().set(SESSION_USER_LOGIN_KEY, "yes");
            return Response.text("you're login successfully");
        }
        return Response.text(userLoginResponse.errorMessage);
    }

    @LoginRequired(SESSION_USER_LOGIN_KEY)
    public Response logout(Request request) {
        request.session().invalidate();
        logger.info("Try to logout");
        return Response.text("just logout");
    }
}
