package site.hanschen.api.user;

import io.grpc.stub.StreamObserver;
import site.hanschen.api.user.db.UserCenterRepository;
import site.hanschen.api.user.db.entity.User;
import site.hanschen.api.user.utils.TextUtils;

/**
 * Our implementation of UserCenter service.
 *
 * <p>See user_center.proto for details of the methods.
 */
public class UserCenterService extends UserCenterGrpc.UserCenterImplBase {

    private UserCenterRepository mUserRepository;

    public UserCenterService(UserCenterRepository repository) {
        this.mUserRepository = repository;
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
        super.requestVerificationCode(request, responseObserver);
        // TODO:
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
