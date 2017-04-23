package site.hanschen.api.user;


import javax.inject.Singleton;

import dagger.Component;
import site.hanschen.api.user.auth.AuthManager;
import site.hanschen.api.user.db.UserCenterRepository;
import site.hanschen.api.user.mail.MailSender;

/**
 * @author HansChen
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    UserCenterRepository getUserCenterRepository();

    MailSender getMailSender();

    AuthManager getAuthManager();

    void inject(UserCenterService service);

}
