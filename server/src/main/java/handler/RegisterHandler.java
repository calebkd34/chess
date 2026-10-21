package handler;

import io.javalin.http.Context;
import request.RegisterRequest;

public class RegisterHandler extends Handler{

    @Override
    public void handleRequest(Context context) {
        RegisterRequest request = serializer.fromJson(context.body(), RegisterRequest.class);
    }
}
