package service;

import result.ClearResult;

public class ClearService extends Service {

    /**
     * removes all data from the database
     *
     * @return empty body
     * @throws BadRequestException when the body is null
     */
    public ClearResult clear() throws BadRequestException {

        userDao.removeAllUsers();
        gameDao.removeAllGames();
        authDao.removeAllAuths();

        return new ClearResult();
    }
}
