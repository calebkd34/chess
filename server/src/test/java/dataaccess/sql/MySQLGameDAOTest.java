package dataaccess.sql;

import chess.*;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;
import org.junit.jupiter.api.Test;

class MySQLGameDAOTest {

    @Test
    void simulateGame() throws DataAccessException, InvalidMoveException {
        // setup
        GameDAO gameDao = new MySQLGameDAO();
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1, null, null, "Game 1", game);
        gameDao.addGame(gameData);
        gameDao.listGames();
        ChessGame simulatedGame = gameDao.getGame(1).game();

        // make a test move
        ChessMove testMove = new ChessMove(new ChessPosition(2, 2), new ChessPosition(3, 2), null);
        simulatedGame.makeMove(testMove);
        GameData simulatedGameData = new GameData(
                gameData.gameID(),
                gameData.whiteUsername(),
                gameData.blackUsername(),
                gameData.gameName(),
                simulatedGame
        );
        gameDao.updateGame(simulatedGameData);
    }
}