package site.hanschen.api.user.db;

import javax.annotation.Nullable;

import site.hanschen.api.user.db.entity.User;
import site.hanschen.api.user.db.entity.UserInfo;

/**
 * @author HansChen
 */
public interface UserCenterRepository {

    boolean insertUser(final User user);

    @Nullable
    User getUserByEmail(final String email);

    @Nullable
    UserInfo getUserInfo(final long id);

    boolean insertOrUpdateUserInfo(final UserInfo userInfo);

    void close();
}
