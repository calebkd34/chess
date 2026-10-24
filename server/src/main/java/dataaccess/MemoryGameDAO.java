package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.max;

public class MemoryGameDAO implements GameDAO {

    private static final Map<Integer, GameData> GAME_DATA_MAP = new HashMap<>();

    @Override
    public void createGame(GameData game) {
        GAME_DATA_MAP.put(game.gameID(), game);
    }

    @Override
    public GameData getGame(int gameID) {
        return GAME_DATA_MAP.get(gameID);
    }

    @Override
    public Collection<GameData> listGames() {
        return GAME_DATA_MAP.values();
    }

    @Override
    public Integer nextGameID() {
        if (GAME_DATA_MAP.isEmpty()) {
            return 1;
        }
        return max(GAME_DATA_MAP.keySet()) + 1;
    }

    @Override
    public void joinGame(Integer gameID, String playerColor, String username) {
        GameData oldGame = GAME_DATA_MAP.get(gameID);
        GameData newGame;
        if (playerColor.equals("WHITE")) {
            newGame = new GameData(
                    oldGame.gameID(),
                    username,
                    oldGame.blackUsername(),
                    oldGame.gameName(),
                    oldGame.game()
            );
        } else {
            newGame = new GameData(
                    oldGame.gameID(),
                    oldGame.whiteUsername(),
                    username,
                    oldGame.gameName(),
                    oldGame.game()
            );
        }
        GAME_DATA_MAP.put(gameID, newGame);
    }

    @Override
    public void removeAllGames() {
        GAME_DATA_MAP.clear();
    }

}
