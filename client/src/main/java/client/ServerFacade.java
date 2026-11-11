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
    private final int port;

    public ServerFacade(int port) {
        this.port = port;
    }

    public AuthData register(String username, String password, String email) throws ServerException {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:%s/user".formatted(port)))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "{\"username\":\"%s\",\"password\":\"%s\",\"email\":\"%s\"}".formatted(username, password, email)
                    ))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return serializer.fromJson(response.body(), AuthData.class);
            } else {
                throw new ServerException("Error: response not OK");
            }
        } catch (URISyntaxException e) {
            throw new ServerException("Syntax Error: %s".formatted(e.toString()));
        } catch (InterruptedException e) {
            throw new ServerException("Interrupted Error: %s".formatted(e.toString()));
        } catch (IOException e) {
            throw new ServerException("IO Error: %s".formatted(e.toString()));
        }
    }

    public AuthData login() {
        return null;
    }

    public void logout() {

    }

    public void createGame() {

    }

    public Collection<GameData> listGames() {
        return null;
    }

    public void joinGame() {

    }

    public void clear() {

    }
}
