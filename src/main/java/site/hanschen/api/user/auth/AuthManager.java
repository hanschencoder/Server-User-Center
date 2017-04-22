package site.hanschen.api.user.auth;

/**
 * @author HansChen
 */
public interface AuthManager {

    boolean addVerificationCode(String email, String verificationCode);

    boolean checkVerificationCode(String email, String verificationCode);

    void stop();
}
