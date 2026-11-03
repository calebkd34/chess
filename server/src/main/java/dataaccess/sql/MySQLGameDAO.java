package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import json.Serializer;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLGameDAO implements GameDAO {

    public MySQLGameDAO() {
        try {
            String[] createStatements = {
                    """
                    CREATE TABLE IF NOT EXISTS games (
                    `gameID` int NOT NULL,
                    `gameData` TEXT DEFAULT NULL,
                    PRIMARY KEY (`gameID`),
                    INDEX(`gameID`)
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
                    """
            };
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException error) {
            System.out.println(error.getMessage());
        }
    }

    @Override
    public Integer nextGameID() throws DataAccessException {
        String statement = "SELECT MAX(gameID) FROM games";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to get games: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public void addGame(GameData game) throws DataAccessException {
        String statement = "INSERT INTO games (gameID, gameData) VALUES (?, ?)";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setInt(1, game.gameID());
                preparedStatement.setString(2, Serializer.toJson(game));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to add game: %s".formatted(error));
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        String statement = "SELECT gameData FROM games WHERE gameID=?";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setInt(1, gameID);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    return Serializer.fromJson(result.getString(1), GameData.class);
                } else {
                    return null;
                }
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to get game: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> games = new ArrayList<>();
        String statement = "SELECT gameData FROM games";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                ResultSet result = preparedStatement.executeQuery();
                while (result.next()) {
                    games.add(Serializer.fromJson(result.getString(1), GameData.class));
                }
                return games;
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to list games: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public void removeAllGames() throws DataAccessException {
        String statement = "TRUNCATE games";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to remove game: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {
        String statement = "UPDATE games SET gameData=? WHERE gameID=?";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, Serializer.toJson(game));
                preparedStatement.setInt(2, game.gameID());
                preparedStatement.executeUpdate();
            }
        } catch(SQLException error) {
            throw new DataAccessException("Unable to update game: %s".formatted(error.getMessage()));
        }
    }
}
