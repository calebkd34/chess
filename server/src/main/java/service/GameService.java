package service;

import kotlin.NotImplementedError;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;

public class GameService extends Service{

    public CreateGameResult createGame(CreateGameRequest request) {
        throw new NotImplementedError("Not implemented");
    }

    public ListGamesResult listGames(ListGamesRequest request) {
        throw new NotImplementedError("Not implemented");
    }

    public JoinGameResult joinGame(JoinGameRequest request) {
        throw new NotImplementedError("Not implemented");
    }
}
