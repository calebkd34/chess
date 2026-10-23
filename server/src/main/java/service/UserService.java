package service;

import model.AuthData;
import model.UserData;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;

import java.util.UUID;

public class UserService extends Service {

    /**
     * Registers a new user
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

    /**
     *
     * Logs in an existing user
     *
     * @param request the object containing username, password, email
     * @return LoginResult object containing username and authToken
     * @throws BadRequestException when any fields are left blank
     * @throws UnauthorizedException when username or password is incorrect
     */
    public LoginResult login(LoginRequest request) throws BadRequestException, UnauthorizedException {

        // left a field blank
        if (request.username() == null || request.password() == null) {
            System.out.println("blank field");
            throw new BadRequestException("Field cannot be left blank");
        }

        // try and get the username information
        UserData user = userDao.getUser(request.username());
        if (user == null) {
            throw new UnauthorizedException("Specified username does not exist");
        }

        // check if the password matches
        if (!user.password().equals(request.password())) {
            throw new UnauthorizedException("Password does not match");
        }

        // create an authToken
        String authToken = createAuth(request.username());

        return new LoginResult(request.username(), authToken);
    }
}
