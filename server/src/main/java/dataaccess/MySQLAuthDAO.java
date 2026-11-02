package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAuthDAO implements AuthDAO {

    public MySQLAuthDAO() {
        try {
            String[] createStatements = {
                    """
            CREATE TABLE IF NOT EXISTS auth (
            `token` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,
            PRIMARY KEY (`token`),
            INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
            };
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException error) {
            System.out.println(error.getMessage());
        }
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
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, authData.authToken());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to remove authData: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public void removeAllAuths() throws DataAccessException {
        String statement = "TRUNCATE auth";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to remove authData: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        String statement = "SELECT * FROM auth WHERE token=?";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, authToken);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    return new AuthData(result.getString(1), result.getString(2));
                } else {
                    return null;
                }
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to remove authData: %s".formatted(error.getMessage()));
        }
    }
}
