package site.hanschen.api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * @author HansChen
 */
public class UserCenterServer {

    private static final Logger logger = LoggerFactory.getLogger(UserCenterServer.class);

    private final int    port;
    private final Server server;

    UserCenterServer(int port, BindableService service) throws IOException {
        this.port = port;
        this.server = ServerBuilder.forPort(port).addService(service).build();
    }

    /**
     * Start serving requests.
     */
    void start() throws IOException {
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
    void stop() {
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
        UserCenterService service = new UserCenterService();
        DaggerApplicationComponent.builder().applicationModule(new ApplicationModule()).build().inject(service);
        UserCenterServer server = new UserCenterServer(8980, service);
        server.start();
        server.blockUntilShutdown();
    }
}
