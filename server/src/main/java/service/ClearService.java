package service;

import dataaccess.*;

public class ClearService extends Service {

    public void clear() {
        userDao.clearAllUsers();
        gameDao.clearAllGames();
        authDao.clearAllAuths();
    }
}
