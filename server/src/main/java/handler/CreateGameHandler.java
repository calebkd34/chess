package handler;

import io.javalin.http.Context;
import json.Serializer;
import request.CreateGameRequest;
import result.CreateGameResult;
import service.BadRequestException;
import service.UnauthorizedException;

public class CreateGameHandler extends Handler {

    /**
     * Request handler for creating a game
     *
     * @param context the JSON request body
     */
    public void handleRequest(Context context) {

        CreateGameResult result;
        try {
            CreateGameRequest request = Serializer.fromJson(context.body(), CreateGameRequest.class);
            String authToken = context.header("authorization");
            result = gameService.createGame(authToken, request);
            context.status(200);
        }
        // throws if bad request
        catch (BadRequestException error) {
            result = new CreateGameResult("Error: bad request");
            context.status(400);
        }
        // throws if authToken is wrong
        catch (UnauthorizedException error) {
            result = new CreateGameResult("Error: unauthorized");
            context.status(401);
        }
        // throws if something bad, something new
        catch (Exception error) {
            result = new CreateGameResult("Error: %s".formatted(error));
            context.status(500);
        }
        context.result(Serializer.toJson(result));
    }
}
