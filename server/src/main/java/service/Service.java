package service;

import dataaccess.*;

public class Service {

    public static UserDAO userDao;
    public static GameDAO gameDao;
    public static AuthDAO authDao;

    public Service() {
        userDao = new MemoryUserDAO();
        gameDao = new MemoryGameDAO();
        authDao = new MemoryAuthDAO();
    }
}
