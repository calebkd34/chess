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
    private static final UserData EXISTING_USER = new UserData(
            "existingUsername",
            "existingPassword",
            "email@email.com"
    );
    private static final UserData NEW_USER = new UserData(
            "caleb",
            "password",
            "me@gmail.com");

    public RegisterResult addExisting() throws BadRequestException, AlreadyTakenException {
        RegisterRequest request = new RegisterRequest(
                EXISTING_USER.username(),
                EXISTING_USER.password(),
                EXISTING_USER.email());
        return USER_SERVICE.register(request);
    }

    @Test
    void registerSuccess() throws AlreadyTakenException, BadRequestException {
        CLEAR_SERVICE.clear();
        RegisterRequest request = new RegisterRequest(NEW_USER.username(), NEW_USER.password(), NEW_USER.email());
        RegisterResult result = USER_SERVICE.register(request);
        assertNotNull(result.authToken());
        assertEquals("caleb", result.username());
        assertNull(result.message());
    }

    @Test
    void registerBadRequest() {
        CLEAR_SERVICE.clear();
        RegisterRequest request = new RegisterRequest(NEW_USER.username(), NEW_USER.password(), null);
        assertThrows(BadRequestException.class, () -> USER_SERVICE.register(request));
    }

    @Test
    void registerAlreadyTaken() throws AlreadyTakenException, BadRequestException {
        CLEAR_SERVICE.clear();
        RegisterRequest request = new RegisterRequest(NEW_USER.username(), NEW_USER.password(), NEW_USER.email());
        USER_SERVICE.register(request);
        assertThrows(AlreadyTakenException.class, () -> USER_SERVICE.register(request));
    }

    @Test
    void loginSuccess() throws BadRequestException, AlreadyTakenException, UnauthorizedException {
        CLEAR_SERVICE.clear();
        addExisting();
        LoginRequest request = new LoginRequest(
                EXISTING_USER.username(),
                EXISTING_USER.password()
        );
        LoginResult result = USER_SERVICE.login(request);
        assertNotNull(result.authToken());
        assertEquals(EXISTING_USER.username(), result.username());
        assertNull(result.message());
    }

    @Test
    void loginBadRequest() throws BadRequestException, AlreadyTakenException {
        CLEAR_SERVICE.clear();
        addExisting();
        LoginRequest request = new LoginRequest(
                null,
                EXISTING_USER.password()
        );
        assertThrows(BadRequestException.class, () -> USER_SERVICE.login(request));
    }

    @Test
    void loginUsernameNotExist() {
        CLEAR_SERVICE.clear();
        LoginRequest request = new LoginRequest(
                EXISTING_USER.username(),
                EXISTING_USER.password()
        );
        assertThrows(UnauthorizedException.class, () -> USER_SERVICE.login(request));
    }

    @Test
    void loginWrongPassword() throws BadRequestException, AlreadyTakenException {
        CLEAR_SERVICE.clear();
        addExisting();
        LoginRequest request = new LoginRequest(
                EXISTING_USER.username(),
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