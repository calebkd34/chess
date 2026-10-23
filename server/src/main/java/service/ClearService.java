package service;

import request.ClearRequest;
import result.ClearResult;

public class ClearService extends Service {

    /**
     * removes all data from the database
     *
     * @param request empty body
     * @return empty body
     * @throws BadRequestException when the body is null
     */
    public ClearResult clear(ClearRequest request) throws BadRequestException {

        if (request == null) {
            throw new BadRequestException("Error: Bad Request");
        }
        userDao.removeAllUsers();
        gameDao.removeAllGames();
        authDao.removeAllAuths();

        return new ClearResult();
    }
}
