package service;

import chess.ChessGame;
import kotlin.NotImplementedError;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;

import java.util.ArrayList;
import java.util.Collection;

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

    /**
     * Gets the list of current games
     *
     * @param authToken the authToken
     * @return a list of the current games
     * @throws UnauthorizedException thrown when authToken is incorrect
     */
    public ListGamesResult listGames(String authToken) throws UnauthorizedException {

        // check the authToken
        if (authDao.getAuth(authToken) == null) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        // get the list of games
        Collection<ListGamesResult.Game> translatedGames = new ArrayList<>();
        for (GameData game : gameDao.listGames()) {
            translatedGames.add(new ListGamesResult.Game(
                    game.gameID(),
                    game.whiteUsername(),
                    game.blackUsername(),
                    game.gameName()
            ));
        }
        return new ListGamesResult(translatedGames);
    }

    public JoinGameResult joinGame(String authToken, JoinGameRequest request) {
        throw new NotImplementedError("Not implemented");
    }
}
