package handler;

import io.javalin.http.Context;
import json.Serializer;
import result.ClearResult;

public class ClearHandler extends Handler {

    /**
     * handles a request for to clear the database
     *
     * @param context the context from the serve, containing body
     */
    public void handleRequest(Context context) {
        ClearResult result;

        try {
            context.status(200);
            result = CLEAR_SERVICE.clear();
        }
        // presumably runtime error
        catch (Exception error) {
            context.status(500);
            result = new ClearResult("Error: %s".formatted(error));
        }
        context.result(Serializer.toJson(result));
    }
}
