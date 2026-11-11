package client;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

public class ServerFacade {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson serializer = new Gson();

    public static AuthData register() throws ServerException {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://localhost:8080"))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return serializer.fromJson(response.body(), AuthData.class);
            } else {
                throw new ServerException("Error: response not 200 OK");
            }
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new ServerException("Error: %s".formatted(e.getMessage()));
        }
    }

    public static AuthData login() {
        return null;
    }

    public static void logout() {

    }

    public static void createGame() {

    }

    public static Collection<GameData> listGames() {
        return null;
    }

    public static void joinGame() {

    }

    public static void clear() {

    }
}
