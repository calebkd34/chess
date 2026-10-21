package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;

public class Handler {

    public static Gson serializer;

    public Handler() {
        serializer = new Gson();
    }

    public void handleRequest(Context context) {
    }

}
