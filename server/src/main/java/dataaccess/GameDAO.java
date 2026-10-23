package dataaccess;

import model.GameData;

public interface GameDAO {

    void createGame(GameData game);

    GameData getGame(int gameId);

    Integer nextGameID();

    void removeGame(GameData game);

    void removeAllGames();
}
