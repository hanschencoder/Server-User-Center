package site.hanschen.api.user.auth;

import java.util.concurrent.TimeUnit;

import site.hanschen.api.user.utils.TextUtils;

/**
 * @author HansChen
 */
public class VerificationCode {

    private static final long EXPIRY_TIME = TimeUnit.MINUTES.toMillis(10);
    private final String email;
    private final String verificationCode;
    private final long   expiryTime;

    public static VerificationCode newInstance(String email, String verificationCode) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(verificationCode)) {
            throw new IllegalArgumentException("email or verificationCode can not be null");
        }
        return new VerificationCode(email, verificationCode);
    }

    private VerificationCode(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.expiryTime = System.currentTimeMillis() + EXPIRY_TIME;
    }

    public String getEmail() {
        return email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }

    @Override
    public String toString() {
        return "VerificationCode{" + "email='" + email + '\'' + ", verificationCode='" + verificationCode + '\'' + ", expiryTime=" + expiryTime + '}';
    }
}
