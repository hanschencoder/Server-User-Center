package site.hanschen.api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import site.hanschen.api.user.auth.AuthManager;
import site.hanschen.api.user.auth.AuthManagerImpl;
import site.hanschen.api.user.db.UserCenterRepository;
import site.hanschen.api.user.db.UserCenterRepositoryImpl;
import site.hanschen.api.user.mail.MailSender;
import site.hanschen.api.user.mail.MailSender163;

/**
 * @author HansChen
 */
public class UserCenterServer {

    private static final Logger logger = LoggerFactory.getLogger(UserCenterServer.class);

    private final int    port;
    private final Server server;

    public UserCenterServer(int port,
                            UserCenterRepository repository,
                            MailSender sender,
                            AuthManager authManager) throws IOException {
        this(ServerBuilder.forPort(port), port, repository, sender, authManager);
    }

    /**
     * Create a UserCenter server using serverBuilder
     */
    public UserCenterServer(ServerBuilder<?> serverBuilder,
                            int port,
                            UserCenterRepository repository,
                            MailSender sender,
                            AuthManager authManager) {
        this.port = port;
        server = serverBuilder.addService(new UserCenterService(repository, sender, authManager)).build();
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
        UserCenterRepository repository = new UserCenterRepositoryImpl();
        MailSender sender = new MailSender163("user_hanschen@163.com", "12345678abc");
        AuthManager authManager = new AuthManagerImpl();
        UserCenterServer server = new UserCenterServer(8980, repository, sender, authManager);
        server.start();
        server.blockUntilShutdown();
    }
}
