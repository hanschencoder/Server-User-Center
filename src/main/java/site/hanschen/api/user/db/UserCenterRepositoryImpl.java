package site.hanschen.api.user.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.annotation.Nullable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import site.hanschen.api.user.db.entity.User;
import site.hanschen.api.user.db.entity.UserInfo;

/**
 * @author HansChen
 */
public class UserCenterRepositoryImpl implements UserCenterRepository {


    private SessionFactory mFactory = null;

    public UserCenterRepositoryImpl() {
        // TODO: MySQL帐号密码应从配置文件读取
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml")
                                               .setProperty("hibernate.connection.username", "root")
                                               .setProperty("hibernate.connection.password", "hanschen123456");
        mFactory = cfg.buildSessionFactory();
    }

    @Override
    public void close() {
        mFactory.close();
        mFactory = null;
    }

    private Session getSession() {
        if (mFactory == null) {
            throw new IllegalStateException("SessionFactory is null");
        }
        return mFactory.getCurrentSession();
    }

    @Override
    public boolean insertUser(final User user) {
        return new TransactionExecutor<Void>() {
            @Override
            protected Void doTransaction(Session session) {
                session.persist(user);
                return null;
            }
        }.execute(getSession()).isSucceed();
    }

    @Override
    @Nullable
    public User getUserByEmail(final String email) {
        return new TransactionExecutor<User>() {
            @Override
            protected User doTransaction(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<User> query = builder.createQuery(User.class);
                Root<User> root = query.from(User.class);
                query.select(root).where(builder.equal(root.get("email"), email));

                return session.createQuery(query).uniqueResult();
            }
        }.execute(getSession()).getResult();
    }

    @Override
    @Nullable
    public UserInfo getUserInfo(final long id) {
        return new TransactionExecutor<UserInfo>() {
            @Override
            protected UserInfo doTransaction(Session session) {
                CriteriaBuilder builder = session.getCriteriaBuilder();
                CriteriaQuery<UserInfo> query = builder.createQuery(UserInfo.class);
                Root<UserInfo> root = query.from(UserInfo.class);
                query.select(root).where(builder.equal(root.get("userId"), id));

                return session.createQuery(query).uniqueResult();
            }
        }.execute(getSession()).getResult();
    }

    @Override
    public boolean insertOrUpdateUserInfo(UserInfo userInfo) {
        return new TransactionExecutor<Void>() {
            @Override
            protected Void doTransaction(Session session) {
                session.saveOrUpdate(userInfo);
                return null;
            }
        }.execute(getSession()).isSucceed();
    }

    public static void main(String[] args) {

        UserCenterRepository repository = new UserCenterRepositoryImpl();
        User entity = repository.getUserByEmail("shensky711@gmail.com");
        System.out.println(entity.getUserId());
        repository.close();
    }
}
