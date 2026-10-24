package json;

import com.google.gson.Gson;

public class Serializer {

    private static final Gson SERIALIZER = new Gson();

    public static <T> T fromJson(String json, Class<T> request) {
        return SERIALIZER.fromJson(json, request);
    }

    public static <T> String toJson(T result) {
        return SERIALIZER.toJson(result);
    }

}
