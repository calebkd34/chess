package service;

import chess.ChessGame;
import kotlin.NotImplementedError;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.ListGamesRequest;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;

public class GameService extends Service{

    public CreateGameResult createGame(String authToken, CreateGameRequest request) throws UnauthorizedException, BadRequestException {

        // check the request
        if (request.gameName() == null) {
            throw new BadRequestException("no gameName given");
        }

        // check the authToken
        if (authDao.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        // create the game
        Integer gameID = gameDao.nextGameID();
        GameData game = new GameData(gameID, null, null, request.gameName(), new ChessGame());
        gameDao.createGame(game);
        return new CreateGameResult(game.gameID());
    }

    public ListGamesResult listGames(String authToken, ListGamesRequest request) {
        throw new NotImplementedError("Not implemented");
    }

    public JoinGameResult joinGame(String authToken, JoinGameRequest request) {
        throw new NotImplementedError("Not implemented");
    }
}
