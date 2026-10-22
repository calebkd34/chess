package service;

import model.UserData;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static final UserService userService = new UserService();
    private static UserData existingUser;
    private static UserData newUser;

    @Test
    void registerSuccess() throws AlreadyTakenException {
         RegisterResult result = userService.register(new RegisterRequest("caleb", "password", "me@email.com"));
         assertNotNull(result.authToken());
         assertEquals("caleb", result.username());
         assertNull(result.message());
    }
}