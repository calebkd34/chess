package handler;

import io.javalin.http.Context;
import json.Serializer;
import request.ClearRequest;
import result.ClearResult;
import service.BadRequestException;
import service.ClearService;

public class ClearHandler {

    private final ClearService clearService;

    public ClearHandler() {
        clearService = new ClearService();
    }

    public String handleRequest(Context context) {

        try {
            ClearRequest request = Serializer.fromJson(context.body(), ClearRequest.class);
            ClearResult result = clearService.clear(request);
            context.status(200);
            return Serializer.toJson(result);
        } catch (Exception error) {
            context.status(500);
            return "{\"message\":\"Error: %s\"".formatted(error);
        }
    }
}
