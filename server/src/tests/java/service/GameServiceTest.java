package service;

import model.GameData;
import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.RegisterRequest;
import result.CreateGameResult;
import result.ListGamesResult;

import java.util.ArrayList;
import java.util.Collection;

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
    void listGamesSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        String authToken = setup();

        // add the first game
        gameService.createGame(authToken, new CreateGameRequest("Game 1"));
        gameService.createGame(authToken, new CreateGameRequest("Game 2"));
        ListGamesResult result = gameService.listGames(authToken);

        // simulate these games
        Collection<ListGamesResult.Game> fakeResult = new ArrayList<>();
        ListGamesResult.Game fakeGame1 = new ListGamesResult.Game(1, null, null, "Game 1");
        ListGamesResult.Game fakeGame2 = new ListGamesResult.Game(2, null, null, "Game 2");
        fakeResult.add(fakeGame1);
        fakeResult.add(fakeGame2);
        assertEquals(result, new ListGamesResult(fakeResult));
    }

    @Test
    public void listGamesUnauthorized() throws BadRequestException, AlreadyTakenException {
        setup();
        assertThrows(UnauthorizedException.class, () -> gameService.listGames("bad"));
    }

    @Test
    void joinGameSuccess() {
    }
}