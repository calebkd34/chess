package handler;

import io.javalin.*;
import io.javalin.http.Context;
import service.ClearService;

public class ClearHandler {

    private final ClearService clearService;

    public ClearHandler() {
        clearService = new ClearService();
    }

    public void handleRequest(Context context) {
        clearService.clear();
    }
}
