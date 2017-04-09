package site.hanschen.api.user;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

/**
 * @author HansChen
 */
public class UserCenterServer {

    private final int    port;
    private final Server server;

    public UserCenterServer(int port) throws IOException {
        this(ServerBuilder.forPort(port), port);
    }

    /**
     * Create a UserCenter server using serverBuilder
     */
    public UserCenterServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        server = serverBuilder.addService(new UserCenterService()).build();
    }

    /**
     * Start serving requests.
     */
    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                UserCenterServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    /**
     * Stop serving requests and shutdown resources.
     */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        UserCenterServer server = new UserCenterServer(8980);
        server.start();
        server.blockUntilShutdown();
    }

    /**
     * Our implementation of UserCenter service.
     *
     * <p>See user_center.proto for details of the methods.
     */
    private static class UserCenterService extends UserCenterGrpc.UserCenterImplBase {

        UserCenterService() {
        }

        @Override
        public void login(LoginInfo request, StreamObserver<LoginReply> responseObserver) {
            System.out.println("Received request from client:");
            System.out.println(request.toString());
            LoginReply reply = LoginReply.newBuilder()
                                         .setSucceed(true)
                                         .setToken("df4d58f7d5f1s25df4ds5f4d5f4df5d5fd5sf4")
                                         .build();
            System.out.println("send Response:");
            System.out.println(reply.toString());
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void register(RegisterInfo request, StreamObserver<RegisterReply> responseObserver) {
            super.register(request, responseObserver);
        }
    }
}
