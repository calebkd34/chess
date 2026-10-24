package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.max;

public class MemoryGameDAO implements GameDAO {

    private static final Map<Integer, GameData> gameDataMap = new HashMap<>();

    @Override
    public void createGame(GameData game) {
        gameDataMap.put(game.gameID(), game);
    }

    @Override
    public GameData getGame(int gameID) {
        return gameDataMap.get(gameID);
    }

    @Override
    public Collection<GameData> listGames() {
        return gameDataMap.values();
    }

    @Override
    public Integer nextGameID() {
        if (gameDataMap.isEmpty()) {
            return 1;
        }
        return max(gameDataMap.keySet()) + 1;
    }

    @Override
    public void joinGame(Integer gameID, String playerColor, String username) {
        GameData oldGame = gameDataMap.get(gameID);
        GameData newGame;
        if (playerColor.equals("WHITE")) {
            newGame = new GameData(
                    oldGame.gameID(),
                    username,
                    oldGame.blackUsername(),
                    oldGame.gameName(),
                    oldGame.game()
            );
        }
        else {
            newGame = new GameData(
                    oldGame.gameID(),
                    oldGame.whiteUsername(),
                    username,
                    oldGame.gameName(),
                    oldGame.game()
            );
        }
        gameDataMap.put(gameID, newGame);
    }

    @Override
    public void removeGame(GameData game) {
        gameDataMap.remove(game.gameID());
    }

    @Override
    public void removeAllGames() {
        gameDataMap.clear();
    }

}
