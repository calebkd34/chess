package dataaccess.sql;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MySQLAuthDAOTest {

    @Test
    void persistAuthSuccess() throws DataAccessException {
        AuthData authData = new AuthData("hello hello", "password");
        AuthDAO authDao = new MySQLAuthDAO();
        authDao.addAuth(authData);
        authDao.getAuth(authData.authToken());
        authDao.removeAuth(authData);
        authDao.removeAllAuths();
    }

    @Test
    void authTokenTooLong() {
        AuthData authData = new AuthData("w".repeat(257), "username");
        AuthDAO authDao = new MySQLAuthDAO();
        assertThrows(DataAccessException.class, () -> authDao.addAuth(authData));
    }

    @Test
    void usernameTooLong() {
        AuthData authData = new AuthData("w", "a".repeat(257));
        AuthDAO authDao = new MySQLAuthDAO();
        assertThrows(DataAccessException.class, () -> authDao.addAuth(authData));
    }

    @Test
    void tokenNull() {
        AuthData authData = new AuthData(null, "password");
        AuthDAO authDao = new MySQLAuthDAO();
        assertThrows(DataAccessException.class, () -> authDao.addAuth(authData));
    }

    @Test
    void usernameNull() {
        AuthData authData = new AuthData("username", null);
        AuthDAO authDao = new MySQLAuthDAO();
        assertThrows(DataAccessException.class, () -> authDao.addAuth(authData));
    }
}