package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MySQLUserDAOTest {

    @Test
    void persistUserSuccess() throws DataAccessException {
        UserData userData = new UserData("username", "password", "email");
        UserDAO userDao = new MySQLUserDAO();
        userDao.addUser(userData);
        userDao.getUser(userData.username());
        userDao.removeAllUsers();
    }

    @Test
    void usernameTooLong() {
        UserData userData = new UserData("e".repeat(257), "password", "email");
        UserDAO userDao = new MySQLUserDAO();
        assertThrows(DataAccessException.class, () -> userDao.addUser(userData));
    }

    @Test
    void emailTooLong() {
        UserData userData = new UserData("username", "password", "e".repeat(257));
        UserDAO userDao = new MySQLUserDAO();
        assertThrows(DataAccessException.class, () -> userDao.addUser(userData));
    }

    @Test
    void usernameNull() {
        UserData userData = new UserData(null, "password", "email");
        UserDAO userDao = new MySQLUserDAO();
        assertThrows(DataAccessException.class, () -> userDao.addUser(userData));
    }

    @Test
    void emailNull() {
        UserData userData = new UserData("username", "password", null);
        UserDAO userDao = new MySQLUserDAO();
        assertThrows(DataAccessException.class, () -> userDao.addUser(userData));
    }
}