package handler;

import io.javalin.http.Context;
import json.Serializer;
import request.RegisterRequest;
import result.RegisterResult;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.UserService;

public class RegisterHandler {

    private final UserService userService;

    public RegisterHandler() {
        userService = new UserService();
    }

    public String handleRequest(Context context) {

        RegisterResult result;
        try {
            RegisterRequest request = Serializer.fromJson(context.body(), RegisterRequest.class);
            result = userService.register(request);
        } catch (BadRequestException error) {
            context.status(400);
            result = new RegisterResult("Error: bad request");
            return Serializer.toJson(result);
        } catch (AlreadyTakenException error) {
            context.status(403);
            result = new RegisterResult("Error: already taken");
        } catch (Exception error) {
            context.status(500);
            result = new RegisterResult("Error: %s".formatted(error));
        }
        return Serializer.toJson(result);
    }

}
