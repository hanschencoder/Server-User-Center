package site.hanschen.api.user.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import site.hanschen.api.user.UserCenterService;
import site.hanschen.api.user.utils.TextUtils;

/**
 * @author HansChen
 */
public class AuthManagerImpl implements AuthManager {

    private Logger logger = LoggerFactory.getLogger(UserCenterService.class);

    private final Queue<VerificationCode>     verificationCodes;
    private       ScheduledThreadPoolExecutor scheduler;

    public AuthManagerImpl() {
        verificationCodes = new ConcurrentLinkedQueue<>();
        init();
    }

    private void init() {
        scheduler = new ScheduledThreadPoolExecutor(3);
        scheduler.scheduleAtFixedRate(mVerificationCleaner, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public boolean addVerificationCode(String email, String verificationCode) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(verificationCode)) {
            return false;
        }

        synchronized (verificationCodes) {
            for (VerificationCode c : verificationCodes) {
                if (c.getEmail().equals(email)) {
                    verificationCodes.remove(c);
                    break;
                }
            }
            return verificationCodes.offer(VerificationCode.newInstance(email, verificationCode));
        }
    }

    @Override
    public boolean checkVerificationCode(String email, String verificationCode) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(verificationCode)) {
            return false;
        }
        synchronized (verificationCodes) {
            for (VerificationCode c : verificationCodes) {
                if (c.getEmail().equals(email) && c.getVerificationCode().equals(verificationCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void stop() {
        verificationCodes.clear();
        scheduler.shutdown();
        scheduler = null;
    }

    private Runnable mVerificationCleaner = new Runnable() {
        @Override
        public void run() {
            if (verificationCodes.size() <= 0) {
                return;
            }
            synchronized (verificationCodes) {
                VerificationCode code;
                while ((code = verificationCodes.peek()) != null) {
                    if (code.isExpired()) {
                        VerificationCode c = verificationCodes.poll();
                        logger.debug("清除无用验证码 {}", c.toString());
                    } else {
                        // 先进先出队列，可提前退出
                        break;
                    }
                }
            }
        }
    };
}
