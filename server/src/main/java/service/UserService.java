package service;

import model.AuthData;
import model.UserData;
import request.RegisterRequest;
import result.RegisterResult;

import java.util.UUID;

public class UserService extends Service {

    /**
     * Registers the user and returns an authToken
     *
     * @param request contains the username, email and password to register with
     * @return the username and authToken created
     * @throws AlreadyTakenException when the username is already taken
     * @throws BadRequestException when any field is left blank
     */
    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException, BadRequestException {

        // left a field blank
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new BadRequestException("Field cannot be left blank");
        }

        // the username already exists, throw exception
        if (userDao.getUser(request.username()) != null) {
            throw new AlreadyTakenException("Username already taken");
        }

        // create new userdata
        UserData userData = new UserData(request.username(), request.password(), request.email());
        userDao.createUser(userData);

        // create authToken and add it to the database
        String authToken = createAuth(request.username());

        // return the register result
        return new RegisterResult(request.username(), authToken);
    }
}
