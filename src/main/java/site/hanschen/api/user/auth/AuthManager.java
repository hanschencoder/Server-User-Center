package site.hanschen.api.user.auth;

/**
 * @author HansChen
 */
public interface AuthManager {

    boolean addVerificationCode(String email, String verificationCode);

    boolean checkVerificationCode(String email, String verificationCode);

    void addToken(String email, String token);

    void removeToken(String token);

    String getEmailByToken(String token);

    void stop();
}
