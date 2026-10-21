package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

public class ClearService {

    private final UserDAO userDao;
    private final GameDAO gameDao;
    private final AuthDAO authDao;

    public ClearService() {
        userDao = new UserDAO();
        gameDao = new GameDAO();
        authDao = new AuthDAO();
    }

    public void clear() {

        // clear database
        userDao.clearAllUsers();
        gameDao.clearAllGames();
        authDao.clearAllAuths();
    }
}
