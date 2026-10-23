package service;

import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.RegisterRequest;
import result.CreateGameResult;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private static final ClearService clearService = new ClearService();
    private static final GameService gameService = new GameService();
    private static final UserService userService = new UserService();

    String setup() throws BadRequestException, AlreadyTakenException {
        clearService.clear();
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        return userService.register(request).authToken();
    }

    @Test
    void createGameSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        String authToken = setup();
        CreateGameRequest request = new CreateGameRequest("Game 1");
        CreateGameResult result = gameService.createGame(authToken, request);
        assertNotNull(result.gameID());
        assertNull(result.message());
    }

    @Test
    void createGameBadRequest() throws BadRequestException, AlreadyTakenException {
        String authToken = setup();
        CreateGameRequest request = new CreateGameRequest(null);
        assertThrows(BadRequestException.class, () -> gameService.createGame(authToken, request));
    }

    @Test
    void createGameUnauthorized() throws BadRequestException, AlreadyTakenException {
        setup();
        CreateGameRequest request = new CreateGameRequest("Game 1");
        assertThrows(UnauthorizedException.class, () -> gameService.createGame("trash", request));
    }

    @Test
    void listGamesSuccess() {
    }

    @Test
    void joinGameSuccess() {
    }
}