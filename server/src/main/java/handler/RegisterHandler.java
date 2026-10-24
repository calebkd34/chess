package handler;

import io.javalin.http.Context;
import json.Serializer;
import request.RegisterRequest;
import result.RegisterResult;
import service.AlreadyTakenException;
import service.BadRequestException;

public class RegisterHandler extends Handler {

    /**
     * registers a new user
     *
     * @param context the context from the http request containing the JSON body
     */
    public void handleRequest(Context context) {

        RegisterResult result;
        try {
            RegisterRequest request = Serializer.fromJson(context.body(), RegisterRequest.class);
            result = userService.register(request);
            context.status(200);
        }
        // thrown when request was invalid
        catch (BadRequestException error) {
            result = new RegisterResult("Error: bad request");
            context.status(400);
        }
        // thrown when username is already taken
        catch (AlreadyTakenException error) {
            result = new RegisterResult("Error: already taken");
            context.status(403);
        }
        // all other errors, likely runtime
        catch (Exception error) {
            result = new RegisterResult("Error: %s".formatted(error));
            context.status(500);
        }
        context.result(Serializer.toJson(result));
    }

}
