package result;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public record ListGamesResult(String message, Collection<Game> games) {

    public record Game(int gameID, String whiteUsername, String blackUsername, String gameName) {
    }

    public ListGamesResult(String message) {
        this(message, null);
    }

    public ListGamesResult(Collection<Game> games) {

        this(null, games);
    }
}
