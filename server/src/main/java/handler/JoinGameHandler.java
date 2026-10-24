package handler;

import io.javalin.http.Context;
import json.Serializer;
import request.JoinGameRequest;
import result.JoinGameResult;
import service.AlreadyTakenException;
import service.BadRequestException;
import service.UnauthorizedException;

public class JoinGameHandler extends Handler{

    /**
     * Request handler for joining games
     *
     * @param context the JSON request body
     */
    public void handleRequest(Context context) {

        JoinGameResult result;
        try {
            String authToken = context.header("authorization");
            JoinGameRequest request = Serializer.fromJson(context.body(), JoinGameRequest.class);
            result = GAME_SERVICE.joinGame(authToken, request);
            context.status(200);
        }
        // bad request
        catch (BadRequestException error) {
            result = new JoinGameResult("Error: bad request");
            context.status(400);
        }
        // invalid authToken
        catch (UnauthorizedException error) {
            result = new JoinGameResult("Error: unauthorized");
            context.status(401);
        }
        // game is already taken
        catch (AlreadyTakenException error) {
            result = new JoinGameResult("Error: already taken");
            context.status(403);
        }
        // unknown error
        catch (Exception error) {
            result = new JoinGameResult("Error: %s".formatted(error));
            context.status(500);
        }
        context.result(Serializer.toJson(result));
    }
}
