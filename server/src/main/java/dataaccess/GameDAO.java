package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    void addGame(GameData game) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    void updateGame(GameData game) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    Integer nextGameID() throws DataAccessException;

    void removeAllGames() throws DataAccessException;
}
