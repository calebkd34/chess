package service;

import dataaccess.*;
import dataaccess.sql.MySQLAuthDAO;
import dataaccess.sql.MySQLGameDAO;
import dataaccess.sql.MySQLUserDAO;
import model.AuthData;

import java.util.UUID;

public class Service {

    // data access
    public static UserDAO userDao = new MySQLUserDAO();
    public static GameDAO gameDao = new MySQLGameDAO();
    public static AuthDAO authDao = new MySQLAuthDAO();

    /**
     * Adds the authToken to the database connected to the username and returns it
     *
     * @param username the username requesting an authToken
     * @return the authToken generated
     */
    public String createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        authDao.addAuth(new AuthData(authToken, username));
        return authToken;
    }

    /**
     * Removes the authToken associated with the user from the database
     *
     * @param authData the authData to remove
     */
    public void deleteAuth(AuthData authData) throws DataAccessException {
        authDao.removeAuth(authData);
    }
}
