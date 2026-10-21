package service;

import model.AuthData;
import model.UserData;
import request.RegisterRequest;
import result.RegisterResult;

import java.util.UUID;

public class UserService extends Service {

    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException {

        // the username already exists, throw exception
        if (userDao.getUser(request.username()) != null) {
            throw new AlreadyTakenException("Username already taken");
        }

        // create new userdata
        UserData userData = new UserData(request.username(), request.password(), request.email());
        userDao.createUser(userData);

        // create authToken and add it to the database
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, request.username());
        authDao.createAuth(authData);

        // return the register result
        return new RegisterResult(request.username(), authToken);
    }
}
