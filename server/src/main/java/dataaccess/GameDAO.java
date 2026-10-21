package dataaccess;

import model.GameData;

public interface GameDAO {

    void createGame(GameData game);

    GameData getGame(int gameId, String playerColor);

    void clearGame(GameData game);

    void clearAllGames();
}
