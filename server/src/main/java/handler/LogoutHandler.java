package handler;

import io.javalin.http.Context;
import json.Serializer;
import result.LogoutResult;
import service.UnauthorizedException;

public class LogoutHandler extends Handler {

    /**
     * Logs out a logged-in user
     *
     * @param context the http JSON body
     */
    public void handleRequest(Context context) {

        LogoutResult result;
        try {
            result = userService.logout(context.header("authorization"));
            context.status(200);
        }
        // thrown when request was invalid
        catch (UnauthorizedException error) {
            result = new LogoutResult("Error: unauthorized");
            context.status(401);
        }
        // thrown when something went very wrong
        catch (Exception error) {
            result = new LogoutResult("Error: %s".formatted(error));
            context.status(500);
        }
        context.result(Serializer.toJson(result));
    }
}
