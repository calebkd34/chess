package service;

import model.UserData;
import org.junit.jupiter.api.Test;
import request.ClearRequest;
import request.LoginRequest;
import request.LogoutRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.LogoutResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final ClearService clearService = new ClearService();
    private static final ClearRequest clearRequest = new ClearRequest();
    private static final UserService userService = new UserService();
    private static final UserData existingUser = new UserData(
            "existingUsername",
            "existingPassword",
            "email@email.com"
    );
    private static final UserData newUser = new UserData(
            "caleb",
            "password",
            "me@gmail.com");

    public RegisterResult addExisting() throws BadRequestException, AlreadyTakenException {
        RegisterRequest request = new RegisterRequest(
                existingUser.username(),
                existingUser.password(),
                existingUser.email());
        return userService.register(request);
    }

    @Test
    void registerSuccess() throws AlreadyTakenException, BadRequestException {
        clearService.clear(clearRequest);
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), newUser.email());
        RegisterResult result = userService.register(request);
        assertNotNull(result.authToken());
        assertEquals("caleb", result.username());
        assertNull(result.message());
    }

    @Test
    void registerBadRequest() throws BadRequestException {
        clearService.clear(clearRequest);
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), null);
        assertThrows(BadRequestException.class, () -> userService.register(request));
    }

    @Test
    void registerAlreadyTaken() throws AlreadyTakenException, BadRequestException {
        clearService.clear(clearRequest);
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), newUser.email());
        userService.register(request);
        assertThrows(AlreadyTakenException.class, () -> userService.register(request));
    }

    @Test
    void loginSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        clearService.clear(clearRequest);
        addExisting();
        LoginRequest request = new LoginRequest(
                existingUser.username(),
                existingUser.password()
        );
        LoginResult result = userService.login(request);
        assertNotNull(result.authToken());
        assertEquals(existingUser.username(), result.username());
        assertNull(result.message());
    }

    @Test
    void loginBadRequest() throws BadRequestException, AlreadyTakenException {
        clearService.clear(clearRequest);
        addExisting();
        LoginRequest request = new LoginRequest(
                null,
                existingUser.password()
        );
        assertThrows(BadRequestException.class, () -> userService.login(request));
    }

    @Test
    void loginUsernameNotExist() throws BadRequestException {
        clearService.clear(clearRequest);
        LoginRequest request = new LoginRequest(
                existingUser.username(),
                existingUser.password()
        );
        assertThrows(UnauthorizedException.class, () -> userService.login(request));
    }

    @Test
    void loginWrongPassword() throws BadRequestException, AlreadyTakenException {
        clearService.clear(clearRequest);
        addExisting();
        LoginRequest request = new LoginRequest(
                existingUser.username(),
                "wrongPassword"
        );
        assertThrows(UnauthorizedException.class, () -> userService.login(request));
    }

    @Test
    void logoutSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        clearService.clear(clearRequest);
        String authToken = addExisting().authToken();
        LogoutRequest request = new LogoutRequest(authToken);
        assertEquals(new LogoutResult(), userService.logout(request));
    }

    @Test
    void logoutUnauthorized() throws BadRequestException {
        clearService.clear(clearRequest);
        assertThrows(UnauthorizedException.class, () -> userService.logout(new LogoutRequest("hello world")));
    }

}