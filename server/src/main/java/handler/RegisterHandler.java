package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import model.UserData;
import request.RegisterRequest;

public class RegisterHandler {

    private final Gson serializer;

    public RegisterHandler() {
        serializer = new Gson();
    }

    public void handleRequest(Context context) {

        // json stuff
        RegisterRequest request = serializer.fromJson(context.body(), RegisterRequest.class);

        System.out.println(request);
    }
}
