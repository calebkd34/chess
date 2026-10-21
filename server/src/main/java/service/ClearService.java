package service;

import request.ClearRequest;
import result.ClearResult;

public class ClearService extends Service {

    public ClearResult clear(ClearRequest request) throws BadRequestException {

        if (request == null) {
            throw new BadRequestException("Error: Bad Request");
        }
        userDao.clearAllUsers();
        gameDao.clearAllGames();
        authDao.clearAllAuths();

        return new ClearResult();
    }
}
