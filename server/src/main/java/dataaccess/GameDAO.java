package dataaccess;

import model.GameData;

public interface GameDAO {

    void createGame(GameData game);

    GameData getGame(int gameId, String playerColor);

    Integer nextGameID();

    void removeGame(GameData game);

    void removeAllGames();
}
