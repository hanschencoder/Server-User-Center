package site.hanschen.api.user;

import io.grpc.stub.StreamObserver;
import site.hanschen.api.user.db.UserCenterRepository;

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
        super.login(request, responseObserver);
        // TODO:
    }

    @Override
    public void register(RegisterInfo request, StreamObserver<RegisterReply> responseObserver) {
        super.register(request, responseObserver);
        String email = request.getEmail();
        RegisterReply.Builder builder = RegisterReply.newBuilder();
        if (mUserRepository.getUserByEmail(email) != null) {
            builder.setErrCode(RegisterReply.ErrorCode.EMAIL_ALREADY_REGISTERED);
            builder.setSucceed(false);
        }
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
