package site.hanschen.api.user;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import site.hanschen.api.user.auth.AuthManager;
import site.hanschen.api.user.auth.AuthManagerImpl;
import site.hanschen.api.user.db.UserCenterRepository;
import site.hanschen.api.user.db.UserCenterRepositoryImpl;
import site.hanschen.api.user.mail.MailSender;
import site.hanschen.api.user.mail.MailSender163;

/**
 * @author HansChen
 */
@Module
class ApplicationModule {

    ApplicationModule() {
    }

    @Provides
    @Singleton
    UserCenterRepository provideUserCenterRepository() {
        return new UserCenterRepositoryImpl();
    }

    @Provides
    @Singleton
    MailSender provideMailSender() {
        return new MailSender163("user_hanschen@163.com", "12345678abc");
    }

    @Provides
    @Singleton
    AuthManager provideAuthManager() {
        return new AuthManagerImpl();
    }
}
