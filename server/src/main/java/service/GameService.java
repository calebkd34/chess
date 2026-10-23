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

    /**
     * Creates a new game with the given name.
     *
     * @param authToken proof the user is logged in
     * @param request contains the new game name
     * @return JSON of new game ID
     * @throws UnauthorizedException when the authToken is wrong
     * @throws BadRequestException when no name is given
     */
    public CreateGameResult createGame(String authToken, CreateGameRequest request) throws UnauthorizedException, BadRequestException, AlreadyTakenException {

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
