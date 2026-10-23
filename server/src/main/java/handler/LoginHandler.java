package handler;

import io.javalin.http.Context;
import json.Serializer;
import request.LoginRequest;
import result.LoginResult;
import service.BadRequestException;
import service.UnauthorizedException;
import service.UserService;

public class LoginHandler extends Handler{

    private static final UserService userService = new UserService();

    /**
     * Logs in an existing user
     *
     * @param context the http JSON body
     */
    public void handleRequest(Context context) {

        LoginResult result;
        try {
            LoginRequest request = Serializer.fromJson(context.body(), LoginRequest.class);
            result = userService.login(request);
            context.status(200);
        }
        // thrown when request was invalid
        catch (BadRequestException error) {
            result = new LoginResult("Error: bad request");
            context.status(400);
        }
        // thrown when username or password is incorrect
        catch (UnauthorizedException error) {
            result = new LoginResult("Error: unauthorized");
            context.status(401);
        }
        // thrown when I don't know what happened
        catch (Exception error) {
            result = new LoginResult("Error: %s".formatted(error));
            context.status(500);
        }
        context.result(Serializer.toJson(result));
    }
}
