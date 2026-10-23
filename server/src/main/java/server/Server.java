package server;

import handler.*;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // clear
        ClearHandler clearHandler = new ClearHandler();
        javalin.delete("/db", clearHandler::handleRequest);

        // register
        RegisterHandler registerHandler = new RegisterHandler();
        javalin.post("/user", registerHandler::handleRequest);

        // login
        LoginHandler loginHandler = new LoginHandler();
        javalin.post("/session", loginHandler::handleRequest);

        // logout
        LogoutHandler logoutHandler = new LogoutHandler();
        javalin.delete("/session", logoutHandler::handleRequest);

        // create game
        CreateGameHandler createGameHandler = new CreateGameHandler();
        javalin.post("/game", createGameHandler::handleRequest);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
