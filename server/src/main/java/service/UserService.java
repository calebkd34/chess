package service;

import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import model.UserData;
import request.RegisterRequest;
import result.RegisterResult;

public class UserService {

    private final UserDAO userDao;

    public UserService() {
        userDao = new MemoryUserDAO();
    }

    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException {

        if (userDao.getUser(request.username()) != null) {
            throw new AlreadyTakenException("Username already taken");
        }
        // create new userdata
        // create authToken
        return null;
    }
}
