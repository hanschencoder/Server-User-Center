package site.hanschen.api.user.db;

import javax.annotation.Nullable;

import site.hanschen.api.user.db.entity.User;

/**
 * @author HansChen
 */
public interface UserCenterRepository {

    boolean insertUser(final User user);

    @Nullable
    User getUserByEmail(final String email);

    void close();
}
