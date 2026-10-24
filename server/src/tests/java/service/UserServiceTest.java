package service;

import model.UserData;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.LogoutResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final ClearService CLEAR_SERVICE = new ClearService();
    private static final UserService USER_SERVICE = new UserService();
    private static final UserData USER_DATA = new UserData(
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
                USER_DATA.username(),
                USER_DATA.password(),
                USER_DATA.email());
        return USER_SERVICE.register(request);
    }

    @Test
    void registerSuccess() throws AlreadyTakenException, BadRequestException {
        CLEAR_SERVICE.clear();
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), newUser.email());
        RegisterResult result = USER_SERVICE.register(request);
        assertNotNull(result.authToken());
        assertEquals("caleb", result.username());
        assertNull(result.message());
    }

    @Test
    void registerBadRequest() {
        CLEAR_SERVICE.clear();
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), null);
        assertThrows(BadRequestException.class, () -> USER_SERVICE.register(request));
    }

    @Test
    void registerAlreadyTaken() throws AlreadyTakenException, BadRequestException {
        CLEAR_SERVICE.clear();
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), newUser.email());
        USER_SERVICE.register(request);
        assertThrows(AlreadyTakenException.class, () -> USER_SERVICE.register(request));
    }

    @Test
    void loginSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        CLEAR_SERVICE.clear();
        addExisting();
        LoginRequest request = new LoginRequest(
                USER_DATA.username(),
                USER_DATA.password()
        );
        LoginResult result = USER_SERVICE.login(request);
        assertNotNull(result.authToken());
        assertEquals(USER_DATA.username(), result.username());
        assertNull(result.message());
    }

    @Test
    void loginBadRequest() throws BadRequestException, AlreadyTakenException {
        CLEAR_SERVICE.clear();
        addExisting();
        LoginRequest request = new LoginRequest(
                null,
                USER_DATA.password()
        );
        assertThrows(BadRequestException.class, () -> USER_SERVICE.login(request));
    }

    @Test
    void loginUsernameNotExist() {
        CLEAR_SERVICE.clear();
        LoginRequest request = new LoginRequest(
                USER_DATA.username(),
                USER_DATA.password()
        );
        assertThrows(UnauthorizedException.class, () -> USER_SERVICE.login(request));
    }

    @Test
    void loginWrongPassword() throws BadRequestException, AlreadyTakenException {
        CLEAR_SERVICE.clear();
        addExisting();
        LoginRequest request = new LoginRequest(
                USER_DATA.username(),
                "wrongPassword"
        );
        assertThrows(UnauthorizedException.class, () -> USER_SERVICE.login(request));
    }

    @Test
    void logoutSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        CLEAR_SERVICE.clear();
        String authToken = addExisting().authToken();
        assertEquals(new LogoutResult(), USER_SERVICE.logout(authToken));
    }

    @Test
    void logoutUnauthorized() {
        CLEAR_SERVICE.clear();
        assertThrows(UnauthorizedException.class, () -> USER_SERVICE.logout("hello world"));
    }

}