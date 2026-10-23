package handler;

import io.javalin.http.Context;
import json.Serializer;
import result.ListGamesResult;
import service.GameService;
import service.UnauthorizedException;

public class ListGamesHandler extends Handler {

    private static final GameService gameService = new GameService();

    /**
     * Request handler for listing games
     *
     * @param context the JSON request body
     */
    public void handleRequest(Context context) {

        ListGamesResult result;
        try {
            String authToken = context.header("authorization");
            result = gameService.listGames(authToken);
            context.status(200);
        }
        // throws when authToken is bad
        catch (UnauthorizedException error) {
            result = new ListGamesResult("Error: unauthorized");
            context.status(401);
        }
        // throws when something went wrong
        catch (Exception error) {
            result = new ListGamesResult("Error: %s".formatted(error.toString()));
            context.status(500);
        }
        context.result(Serializer.toJson(result));
    }
}
