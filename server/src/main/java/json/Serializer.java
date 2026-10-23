package json;

import com.google.gson.Gson;

public class Serializer {

    private static final Gson serializer = new Gson();

    public static <T> T fromJson(String json, Class<T> request) {
        return serializer.fromJson(json, request);
    }

    public static <T> String toJson(T result) {
        return serializer.toJson(result);
    }

}
