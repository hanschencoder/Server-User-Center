package site.hanschen.api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import site.hanschen.api.user.db.UserCenterRepository;
import site.hanschen.api.user.db.UserCenterRepositoryImpl;

/**
 * @author HansChen
 */
public class UserCenterServer {

    private static final Logger logger = LoggerFactory.getLogger(UserCenterServer.class);

    private final int    port;
    private final Server server;

    public UserCenterServer(int port, UserCenterRepository repository) throws IOException {
        this(ServerBuilder.forPort(port), port, repository);
    }

    /**
     * Create a UserCenter server using serverBuilder
     */
    public UserCenterServer(ServerBuilder<?> serverBuilder, int port, UserCenterRepository repository) {
        this.port = port;
        server = serverBuilder.addService(new UserCenterService(repository)).build();
    }

    /**
     * Start serving requests.
     */
    public void start() throws IOException {
        server.start();
        logger.debug("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.error("*** shutting down gRPC server since JVM is shutting down");
                UserCenterServer.this.stop();
                logger.error("*** server shut down");
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
        UserCenterServer server = new UserCenterServer(8980, new UserCenterRepositoryImpl());
        server.start();
        server.blockUntilShutdown();
    }
}
