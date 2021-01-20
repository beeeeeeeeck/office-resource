package app.user.service;

import app.api.user.BOCreateUserRequest;
import app.api.user.BOCreateUserResponse;
import app.api.user.BOUserLoginRequest;
import app.api.user.BOUserLoginResponse;
import app.api.user.ListUserResponse;
import app.common.util.PasswordUtils;
import app.user.domain.User;
import core.framework.db.Repository;
import core.framework.inject.Inject;
import core.framework.web.exception.ConflictException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author beckl
 */
public class UserService {
    @Inject
    Repository<User> userRepository;

    public BOCreateUserResponse create(BOCreateUserRequest request) {
        // Make sure user name is unique for every user
        userRepository.selectOne("user_name = ?", request.userName).ifPresent(user -> {
            throw new ConflictException("Duplicated user name requested");
        });
        User user = new User();
        user.userName = request.userName;
        user.passwordSalt = PasswordUtils.getSalt();
        user.password = PasswordUtils.generateSecurePassword(request.password, user.passwordSalt);
        user.active = Boolean.TRUE;
        user.createdTime = LocalDateTime.now();
        user.updatedTime = LocalDateTime.now();
        BOCreateUserResponse response = new BOCreateUserResponse();
        response.id = userRepository.insert(user).orElseThrow();
        response.active = user.active;
        return response;
    }

    public BOUserLoginResponse login(BOUserLoginRequest request) {
        return userRepository.selectOne("user_name = ?", request.userName).map(user -> {
            BOUserLoginResponse response = new BOUserLoginResponse();
            if (!user.active) {
                response.success = Boolean.FALSE;
                response.errorMessage = "User is inactive";
                return response;
            }
            if (!PasswordUtils.verifyPassword(request.password, user.password, user.passwordSalt)) {
                response.success = Boolean.FALSE;
                response.errorMessage = "Incorrect password";
                return response;
            }
            response.success = Boolean.TRUE;
            response.userId = user.id;
            return response;
        }).orElseGet(() -> {
            BOUserLoginResponse response = new BOUserLoginResponse();
            response.success = Boolean.FALSE;
            response.errorMessage = "Incorrect user name";
            return response;
        });
    }

    public ListUserResponse list() {
        List<User> users = userRepository.select().fetch();
        ListUserResponse response = new ListUserResponse();
        response.users = users.stream().map(user -> {
            var view = new ListUserResponse.UserView();
            view.id = user.id;
            view.userName = user.userName;
            view.active = user.active;
            return view;
        }).collect(Collectors.toList());
        return response;
    }
}
