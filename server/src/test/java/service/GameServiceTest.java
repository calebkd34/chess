package service;

import org.junit.jupiter.api.Test;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.RegisterRequest;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private static final ClearService CLEAR_SERVICE = new ClearService();
    private static final GameService GAME_SERVICE = new GameService();
    private static final UserService USER_SERVICE = new UserService();

    String setup() throws BadRequestException, AlreadyTakenException {
        CLEAR_SERVICE.clear();
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        return USER_SERVICE.register(request).authToken();
    }

    @Test
    void createGameSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        String authToken = setup();
        CreateGameRequest request = new CreateGameRequest("Game 1");
        CreateGameResult result = GAME_SERVICE.createGame(authToken, request);
        assertNotNull(result.gameID());
        assertNull(result.message());
    }

    @Test
    void createGameBadRequest() throws BadRequestException, AlreadyTakenException {
        String authToken = setup();
        CreateGameRequest request = new CreateGameRequest(null);
        assertThrows(BadRequestException.class, () -> GAME_SERVICE.createGame(authToken, request));
    }

    @Test
    void createGameUnauthorized() throws BadRequestException, AlreadyTakenException {
        setup();
        CreateGameRequest request = new CreateGameRequest("Game 1");
        assertThrows(UnauthorizedException.class, () -> GAME_SERVICE.createGame("trash", request));
    }

    @Test
    void listGamesSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        String authToken = setup();

        // add the first game
        GAME_SERVICE.createGame(authToken, new CreateGameRequest("Game 1"));
        GAME_SERVICE.createGame(authToken, new CreateGameRequest("Game 2"));
        ListGamesResult result = GAME_SERVICE.listGames(authToken);

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
        assertThrows(UnauthorizedException.class, () -> GAME_SERVICE.listGames("bad"));
    }

    @Test
    void joinGameSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        String authToken = setup();
        GAME_SERVICE.createGame(authToken, new CreateGameRequest("Game 1"));
        JoinGameRequest request = new JoinGameRequest("WHITE", 1);
        JoinGameResult result = GAME_SERVICE.joinGame(authToken, request);
        assertNotNull(result);
    }

    @Test
    public void joinGameBadRequest() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        String authToken = setup();
        GAME_SERVICE.createGame(authToken, new CreateGameRequest("Game 1"));
        JoinGameRequest request = new JoinGameRequest("brown", 1);
        assertThrows(BadRequestException.class, () -> GAME_SERVICE.joinGame(authToken, request));
    }

    @Test
    public void joinGameUnauthorized() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        String authToken = setup();
        GAME_SERVICE.createGame(authToken, new CreateGameRequest("Game 1"));
        JoinGameRequest request = new JoinGameRequest("BLACK", 1);
        assertThrows(UnauthorizedException.class, () -> GAME_SERVICE.joinGame("dum", request));
    }

    @Test
    public void joinGameAlreadyTaken() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        String authToken = setup();
        GAME_SERVICE.createGame(authToken, new CreateGameRequest("Game 1"));
        JoinGameRequest request = new JoinGameRequest("WHITE", 1);
        GAME_SERVICE.joinGame(authToken, request);
        assertThrows(AlreadyTakenException.class, () -> GAME_SERVICE.joinGame(authToken, request));
    }
}