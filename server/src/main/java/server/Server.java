package server;

import handler.ClearHandler;
import handler.RegisterHandler;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.

        // clear
        ClearHandler clearHandler = new ClearHandler();
        javalin.delete("/db", clearHandler::handleRequest);

        // register
        RegisterHandler registerHandler = new RegisterHandler();
        javalin.post("/user", registerHandler::handleRequest);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
