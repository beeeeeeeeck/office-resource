package app.api.user;

import core.framework.api.json.Property;
import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;

import java.util.List;

/**
 * @author beckl
 */
public class ListUserResponse {
    @NotNull
    @Property(name = "users")
    public List<UserView> users;

    public static class UserView {
        @NotNull
        @Property(name = "id")
        public Long id;

        @NotNull
        @NotBlank
        @Property(name = "user_name")
        public String userName;

        @NotNull
        @Property(name = "active")
        public Boolean active;
    }
}
