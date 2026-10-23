package dataaccess;

import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {

    private static final Map<Integer, GameData> gameDataMap = new HashMap<>();

    @Override
    public void createGame(GameData game) {
        gameDataMap.put(game.gameID(), game);
    }

    @Override
    public GameData getGame(int gameID, String playerColor) {
        return gameDataMap.get(gameID);
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
