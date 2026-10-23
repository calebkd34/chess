package service;

import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import request.ClearRequest;
import request.RegisterRequest;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final ClearService clearService = new ClearService();
    private static final ClearRequest clearRequest = new ClearRequest();
    private static final UserService userService = new UserService();
    private static UserData existingUser;
    private static final UserData newUser = new UserData("caleb", "password", "me@gmail.com");

    @Test
    void registerSuccess() throws AlreadyTakenException {
        clearService.clear(clearRequest);
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), newUser.email());
        RegisterResult result = userService.register(request);
        assertNotNull(result.authToken());
        assertEquals("caleb", result.username());
        assertNull(result.message());
    }

    @Test
    void registerBadRequest() throws AlreadyTakenException {
        clearService.clear(clearRequest);
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), null);
        assertThrows(BadRequestException.class, () -> userService.register(request));
    }

    @Test
    void registerAlreadyTaken() throws AlreadyTakenException {
        clearService.clear(clearRequest);
        RegisterRequest request = new RegisterRequest(newUser.username(), newUser.password(), newUser.email());
        userService.register(request);
        assertThrows(AlreadyTakenException.class, () -> userService.register(request));
    }
}