package site.hanschen.api.user.db;

import site.hanschen.api.user.db.entity.User;

/**
 * @author HansChen
 */
public interface UserCenterRepository {

    boolean insertUser(final User user);

    User getUserByEmail(final String email);

    void close();
}
