package site.hanschen.api.user.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import site.hanschen.api.user.db.entity.UserEntity;

/**
 * @author HansChen
 */
public class UserCenterRepository {

    private static UserCenterRepository sInstance;

    private SessionFactory mFactory;

    public static UserCenterRepository getInstance() {
        if (sInstance == null) {
            synchronized (UserCenterRepository.class) {
                if (sInstance == null) {
                    sInstance = new UserCenterRepository();
                }
            }
        }
        return sInstance;
    }

    public void destroy() {
        mFactory.close();
    }

    private UserCenterRepository() {
        // TODO: MySQL帐号密码应从配置文件读取
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml")
                                               .setProperty("hibernate.connection.username", "root")
                                               .setProperty("hibernate.connection.password", "hanschen123456");
        mFactory = cfg.buildSessionFactory();
    }

    public void insertUser(UserEntity user) {
        Session session = mFactory.openSession();
        Transaction t = session.beginTransaction();
        session.persist(user);
        t.commit();
        session.close();
    }

    public UserEntity getUserEntityByEmail(final String email) {
        Session session = mFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = builder.createQuery(UserEntity.class);
        Root<UserEntity> root = query.from(UserEntity.class);
        query.select(root).where(builder.equal(root.get("email"), email));

        return session.createQuery(query).uniqueResult();
    }

    public static void main(String[] args) {

        UserCenterRepository repository = new UserCenterRepository();
        UserEntity entity = repository.getUserEntityByEmail("shensky711@gmail.com");
        System.out.println(entity.getUserId());
        repository.destroy();
    }
}
