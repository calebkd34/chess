package client;

import client.POJOs.GameID;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerFacade {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final Gson serializer = new Gson();
    private final int port;

    public ServerFacade(int port) {
        this.port = port;
    }

    public void clear() throws ResponseException {
        try {
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:%s/db".formatted(port)))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .DELETE()
                    .build();

            // send
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // exceptions
        } catch (Exception e) {
            throw new ResponseException("Unable to clear database");
        }
    }

    public AuthData register(String username, String password, String email) throws ResponseException {

        try {
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:%s/user".formatted(port)))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "{\"username\":\"%s\",\"password\":\"%s\",\"email\":\"%s\"}".formatted(username, password, email)
                    ))
                    .header("Content-Type", "application/json")
                    .build();

            // send
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // build an AuthData instance from the response if possible
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return serializer.fromJson(response.body(), AuthData.class);
            } else {
                throw new ResponseException("[%s] is not OK: %s".formatted(response.statusCode(), response.body()));
            }
            // exceptions
        } catch (URISyntaxException e) {
            throw new ResponseException("Syntax Error: %s".formatted(e.toString()));
        } catch (InterruptedException e) {
            throw new ResponseException("Interrupted Error: %s".formatted(e.toString()));
        } catch (IOException e) {
            throw new ResponseException("IO Error: %s".formatted(e.toString()));
        }
    }

    public AuthData login(String username, String password) throws ResponseException {
        try {
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:%s/session".formatted(port)))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "{\"username\":\"%s\",\"password\":\"%s\"}".formatted(username, password)
                    ))
                    .build();

            // send
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // build an AuthData instance from the response if possible
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return serializer.fromJson(response.body(), AuthData.class);
            } else {
                throw new ResponseException("[%s] is not OK: %s".formatted(response.statusCode(), response.body()));
            }
            // exceptions
        } catch (URISyntaxException e) {
            throw new ResponseException("Syntax Error: %s".formatted(e.toString()));
        } catch (InterruptedException e) {
            throw new ResponseException("Interrupted Error: %s".formatted(e.toString()));
        } catch (IOException e) {
            throw new ResponseException("IO Error: %s".formatted(e.toString()));
        }
    }

    public void logout(String authToken) throws ResponseException {
        try {
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:%s/session".formatted(port)))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .DELETE()
                    .header("authorization", authToken)
                    .build();

            // send
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new ResponseException("[%s] is not OK: %s".formatted(response.statusCode(), response.body()));
            }
            // exceptions
        } catch (URISyntaxException e) {
            throw new ResponseException("Syntax Error: %s".formatted(e.toString()));
        } catch (InterruptedException e) {
            throw new ResponseException("Interrupted Error: %s".formatted(e.toString()));
        } catch (IOException e) {
            throw new ResponseException("IO Error: %s".formatted(e.toString()));
        }
    }

    public int createGame(String authToken, String gameName) throws ResponseException {
        try {
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:%s/game".formatted(port)))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "{\"gameName\":\"%s\"}".formatted(gameName)
                    ))
                    .header("authorization", authToken)
                    .build();

            // send
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // build instance from the response if possible
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return serializer.fromJson(response.body(), GameID.class).gameID();
            } else {
                throw new ResponseException("[%s] is not OK: %s".formatted(response.statusCode(), response.body()));
            }
            // exceptions
        } catch (URISyntaxException e) {
            throw new ResponseException("Syntax Error: %s".formatted(e.toString()));
        } catch (InterruptedException e) {
            throw new ResponseException("Interrupted Error: %s".formatted(e.toString()));
        } catch (IOException e) {
            throw new ResponseException("IO Error: %s".formatted(e.toString()));
        }
    }
}
