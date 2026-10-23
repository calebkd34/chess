package handler;

import io.javalin.http.Context;
import json.Serializer;
import result.ClearResult;
import service.ClearService;

public class ClearHandler {

    private static final ClearService clearService = new ClearService();

    /**
     * handles a request for to clear the database
     *
     * @param context the context from the serve, containing body
     */
    public void handleRequest(Context context) {
        ClearResult result;

        try {
            context.status(200);
            result = clearService.clear();
        }
        // presumably runtime error
        catch (Exception error) {
            context.status(500);
            result = new ClearResult("Error: %s".formatted(error));
        }
        context.result(Serializer.toJson(result));
    }
}
