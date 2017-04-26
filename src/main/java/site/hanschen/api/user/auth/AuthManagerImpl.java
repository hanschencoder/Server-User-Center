package site.hanschen.api.user.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
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

    private final Queue<VerificationCode>           verificationCodes;
    private final ConcurrentHashMap<String, String> tokens;
    private       ScheduledThreadPoolExecutor       scheduler;

    public AuthManagerImpl() {
        verificationCodes = new ConcurrentLinkedQueue<>();
        tokens = new ConcurrentHashMap<>();
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
        tokens.clear();
        scheduler.shutdown();
        scheduler = null;
    }

    @Override
    public void addToken(String email, String token) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(token)) {
            return;
        }
        for (Map.Entry<String, String> entity : tokens.entrySet()) {
            if (entity.getValue().equals(email)) {
                tokens.remove(entity.getKey());
                break;
            }
        }
        tokens.put(token, email);
    }

    @Override
    public void removeToken(String token) {
        if (TextUtils.isEmpty(token)) {
            return;
        }
        tokens.remove(token);
    }

    @Override
    public String getEmailByToken(String token) {
        return tokens.get(token);
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
