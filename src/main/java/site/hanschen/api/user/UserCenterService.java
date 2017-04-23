package site.hanschen.api.user;

import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.mail.Multipart;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.grpc.stub.StreamObserver;
import site.hanschen.api.user.auth.AuthManager;
import site.hanschen.api.user.db.UserCenterRepository;
import site.hanschen.api.user.db.entity.User;
import site.hanschen.api.user.mail.MailSender;
import site.hanschen.api.user.mail.MultipartBuilder;
import site.hanschen.api.user.utils.TextUtils;

/**
 * Our implementation of UserCenter service.
 *
 * <p>See user_center.proto for details of the methods.
 */
public class UserCenterService extends UserCenterGrpc.UserCenterImplBase {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserCenterService.class);

    @Inject
    UserCenterRepository mUserRepository;
    @Inject
    MailSender           mMailSender;
    @Inject
    AuthManager          mAuthManager;

    public UserCenterService() {

    }

    @Override
    public void login(LoginInfo request, StreamObserver<LoginReply> responseObserver) {

        LoginReply.Builder builder = LoginReply.newBuilder();
        builder.setSucceed(false);

        String username = request.getUsername();
        String md5 = request.getPasswordMd5();
        if (TextUtils.isEmpty(username)) {
            builder.setErrCode(LoginReply.ErrorCode.ACCOUNT_EMPTY);
        } else if (TextUtils.isEmpty(md5)) {
            builder.setErrCode(LoginReply.ErrorCode.PASSWORD_EMPTY);
        } else {
            User user = mUserRepository.getUserByEmail(username);
            if (user == null || !md5.equals(user.getPasswordMd5())) {
                builder.setErrCode(LoginReply.ErrorCode.ACCOUNT_PASSWORD_INCORRECT);
            } else {
                builder.setSucceed(true);
            }
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void requestVerificationCode(VerificationRequest request, StreamObserver<VerificationReply> responseObserver) {

        VerificationReply.Builder builder = VerificationReply.newBuilder();
        builder.setSucceed(false);

        String email = request.getEmail();
        if (mUserRepository.getUserByEmail(email) != null) {
            builder.setErrCode(VerificationReply.ErrorCode.EMAIL_ALREADY_REGISTERED);
        } else if (!TextUtils.isEmailValid(email)) {
            builder.setErrCode(VerificationReply.ErrorCode.EMAIL_INVALID);
        } else {
            String verificationCode = String.valueOf(getRandNum(1, 999999));
            boolean succeed = mMailSender.send(email, "帐号注册", createMultipart(email, verificationCode));
            succeed &= mAuthManager.addVerificationCode(email, verificationCode);
            if (succeed) {
                builder.setSucceed(true);
            } else {
                builder.setErrCode(VerificationReply.ErrorCode.UNKNOWN);
            }
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    private int getRandNum(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private Multipart createMultipart(String email, String verificationCode) {
        MultipartBuilder builder = MultipartBuilder.newBuilder();
        builder.setText(createHtml(email, verificationCode), "text/html; charset=utf-8");
        return builder.build();
    }

    private String createHtml(String email, String verificationCode) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
            configuration.setClassLoaderForTemplateLoading(UserCenterService.class.getClassLoader(), "/template/");
            configuration.setDefaultEncoding("UTF-8");
            Template template = configuration.getTemplate("VerificationCode.ftl");
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("mail", email);
            dataModel.put("verificationCode", verificationCode);
            Writer writer = new StringWriter();
            template.process(dataModel, writer);
            return writer.toString();
        } catch (IOException | TemplateException e) {
            logger.error("oops", e);
            return "";
        }
    }

    @Override
    public void register(RegisterInfo request, StreamObserver<RegisterReply> responseObserver) {

        RegisterReply.Builder builder = RegisterReply.newBuilder();
        builder.setSucceed(false);

        String email = request.getEmail();
        if (mUserRepository.getUserByEmail(email) != null) {
            builder.setErrCode(RegisterReply.ErrorCode.EMAIL_ALREADY_REGISTERED);
        } else if (request.getPassword() == null || request.getPassword().length() < 8) {
            builder.setErrCode(RegisterReply.ErrorCode.PASSWORD_INVALID);
        } else if (!TextUtils.isEmailValid(email)) {
            builder.setErrCode(RegisterReply.ErrorCode.EMAIL_INVALID);
        } else if (!mAuthManager.checkVerificationCode(email, request.getVerificationCode())) {
            builder.setErrCode(RegisterReply.ErrorCode.VERIFICATION_CODE_ERROR);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(request.getPassword());
            user.setPasswordMd5(request.getPasswordMd5());
            if (mUserRepository.insertUser(user)) {
                builder.setSucceed(true);
            } else {
                builder.setErrCode(RegisterReply.ErrorCode.UNKNOWN);
            }
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void requestAuthorization(AuthorizationRequest request, StreamObserver<AuthorizationReply> responseObserver) {
        super.requestAuthorization(request, responseObserver);
        // TODO:
    }

    @Override
    public void changePassword(NewPassword request, StreamObserver<NewPasswordReply> responseObserver) {
        super.changePassword(request, responseObserver);
        // TODO:
    }

    @Override
    public void requestUserInfo(OperateToken request, StreamObserver<UserInfo> responseObserver) {
        super.requestUserInfo(request, responseObserver);
        // TODO:
    }

    @Override
    public void updateUserInfo(UpdateRequest request, StreamObserver<ResultReply> responseObserver) {
        super.updateUserInfo(request, responseObserver);
        // TODO:
    }
}
