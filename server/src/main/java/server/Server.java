package server;

import handler.ClearHandler;
import io.javalin.*;

public class Server {

    private final Javalin javalin;
    private final ClearHandler clearHandler;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.

        // delete
        clearHandler = new ClearHandler();
        javalin.delete("/db", clearHandler::handleRequest);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
