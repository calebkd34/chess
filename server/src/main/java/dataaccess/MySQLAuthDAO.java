package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLAuthDAO implements AuthDAO {

    public MySQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void addAuth(AuthData authData) throws DataAccessException {
        String statement = "INSERT INTO auth (token, username) VALUES (?, ?)";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, authData.authToken());
                preparedStatement.setString(2, authData.username());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to insert authData: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public void removeAuth(AuthData authData) throws DataAccessException {
        String statement = "DELETE FROM auth WHERE token=?";

    }

    @Override
    public void removeAllAuths() throws DataAccessException {
        String statement = "DELETE * FROM auth";

    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS auth (
            `token` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,
            PRIMARY KEY (`token`),
            INDEX(username),
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection connection = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = connection.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to configure database: %s".formatted(error.getMessage()));
        }
    }
}
