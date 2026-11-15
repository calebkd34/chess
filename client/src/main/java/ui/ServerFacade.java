package ui;

import com.google.gson.Gson;
import model.AuthData;
import result.CreateGameResult;
import result.JoinGameResult;
import result.ListGamesResult;

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
                throw new ResponseException(switch (response.statusCode()) {
                    case 400 -> "bad request";
                    case 403 -> "already taken";
                    default -> "an unknown error occurred";
                });
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
                throw new ResponseException(switch (response.statusCode()) {
                    case 400 -> "bad request";
                    case 401 -> "unauthorized";
                    default -> "an unknown error occurred";
                });
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
                throw new ResponseException((response.statusCode() == 401) ? "unauthorized" : "an unknown error occurred");
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

    public Collection<ListGamesResult.Game> listGames(String authToken) throws ResponseException {
        try {
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:%s/game".formatted(port)))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .GET()
                    .header("authorization", authToken)
                    .build();

            // send
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // build instance from the response if possible
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return serializer.fromJson(response.body(), ListGamesResult.class).games();
            } else {
                throw new ResponseException((response.statusCode() == 401) ? "unauthorized" : "an unknown error occurred");
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
                return serializer.fromJson(response.body(), CreateGameResult.class).gameID();
            } else {
                throw new ResponseException(switch (response.statusCode()) {
                    case 400 -> "bad request";
                    case 401 -> "unauthorized";
                    default -> "an unknown error occurred";
                });
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

    public void joinGame(String authToken, Integer gameID, String playerColor) throws ResponseException {
        try {
            // build request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:%s/game".formatted(port)))
                    .timeout(java.time.Duration.ofMillis(5000))
                    .PUT(HttpRequest.BodyPublishers.ofString(
                            "{\"gameID\":\"%s\",\"playerColor\":\"%s\"}".formatted(gameID, playerColor)
                    ))
                    .header("authorization", authToken)
                    .build();

            // send
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.statusCode());
            // build instance from the response if possible
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new ResponseException(switch (response.statusCode()) {
                    case 400 -> "bad request";
                    case 401 -> "unauthorized";
                    case 403 -> "already taken";
                    default -> "an unknown error occurred";
                });
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
