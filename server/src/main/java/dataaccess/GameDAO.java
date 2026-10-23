package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    void createGame(GameData game);

    GameData getGame(int gameId);

    Collection<GameData> listGames();

    Integer nextGameID();

    void removeGame(GameData game);

    void removeAllGames();
}
