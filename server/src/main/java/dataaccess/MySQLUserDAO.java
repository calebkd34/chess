package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLUserDAO implements UserDAO {

    public MySQLUserDAO() {
        try {
            String[] createStatements = {
                    """
            CREATE TABLE IF NOT EXISTS user (
            `username` varchar(256) NOT NULL,
            `password` varchar(256) NOT NULL,
            `email` varchar(256) NOT NULL,
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
    public void addUser(UserData user) throws DataAccessException {
        String statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, user.username());
                preparedStatement.setString(2, BCrypt.hashpw(user.password(), BCrypt.gensalt()));
                preparedStatement.setString(3, user.email());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to insert userData: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        String statement = "SELECT * FROM user WHERE username=?";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                ResultSet result = preparedStatement.executeQuery();
                if (result.next()) {
                    return new UserData(result.getString(1), result.getString(2), result.getString(3));
                } else {
                    return null;
                }
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to get userData: %s".formatted(error.getMessage()));
        }
    }

    @Override
    public void removeAllUsers() throws DataAccessException {
        String statement = "TRUNCATE user";
        try (Connection connection = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException error) {
            throw new DataAccessException("Unable to remove all users: %s".formatted(error.getMessage()));
        }
    }
}
