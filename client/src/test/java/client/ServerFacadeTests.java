package client;

import model.AuthData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        serverFacade = new ServerFacade(port);
        System.out.println("Started test HTTP server on " + port);
    }

    @BeforeEach
    public void setup() throws ResponseException {
        serverFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void registerSuccess() throws ResponseException {
        AuthData authData = serverFacade.register("username", "password", "me@mail.com");
        assertEquals("username", authData.username());
        assertNotNull(authData.authToken());
    }

    @Test
    public void registerAlreadyTaken() throws ResponseException {
        serverFacade.register("username", "password", "me@email.com");
        assertThrows(
                ResponseException.class,
                () -> serverFacade.register("username", "password", "me@email.com")
        );
    }

    @Test
    public void loginSuccess() throws ResponseException {
        String authToken = serverFacade.register("username", "password", "email").authToken();
        serverFacade.logout(authToken);
        AuthData authData = serverFacade.login("username", "password");
        assertEquals("username", authData.username());
        assertNotNull(authData.authToken());
    }

    @Test
    public void loginWrongUsername() throws ResponseException {

        serverFacade.register("username", "password", "me@gmail.com");
        assertThrows(
                ResponseException.class,
                () -> serverFacade.login("wrong", "password")
        );
    }

    @Test
    public void loginWrongPassword() throws ResponseException {
        String authToken = serverFacade.register("username", "password", "email").authToken();
        serverFacade.logout(authToken);
        assertThrows(
                client.ResponseException.class,
                () -> serverFacade.login("username", "wrong")
        );
    }

    @Test
    public void logoutSuccess() throws ResponseException {
        String authToken = serverFacade.register("username", "password", "email").authToken();
        serverFacade.logout(authToken);
        assertThrows(
                ResponseException.class,
                () -> serverFacade.logout(authToken)
        );
    }

    @Test
    public void logoutUnauthorized() throws ResponseException {
        serverFacade.register("username", "password", "email");
        assertThrows(
                ResponseException.class,
                () -> serverFacade.logout("probably not authToken")
        );
    }

}
