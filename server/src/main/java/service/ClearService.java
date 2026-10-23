package service;

import result.ClearResult;

public class ClearService extends Service {

    /**
     * removes all data from the database
     *
     * @return empty body
     */
    public ClearResult clear() {

        userDao.removeAllUsers();
        gameDao.removeAllGames();
        authDao.removeAllAuths();

        return new ClearResult();
    }
}
