package service;

import dataaccess.*;
import model.AuthData;

import java.util.UUID;

public class Service {

    // data access
    public static UserDAO userDao = new MemoryUserDAO();
    public static GameDAO gameDao = new MemoryGameDAO();
    public static AuthDAO authDao = new MemoryAuthDAO();

    /**
     * Adds the authToken to the database connected to the username and returns it
     *
     * @param username the username requesting an authToken
     * @return the authToken generated
     */
    public String createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        authDao.addAuth(new AuthData(authToken, username));
        return authToken;
    }

    /**
     * Removes the authToken associated with the user from the database
     *
     * @param authData the authData to remove
     */
    public void deleteAuth(AuthData authData) {
        authDao.removeAuth(authData);
    }
}
