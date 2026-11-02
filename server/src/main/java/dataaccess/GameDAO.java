package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {

    void addGame(GameData game) throws DataAccessException;

    GameData getGame(int gameId) throws DataAccessException;

    void joinGame(Integer gameID, String playerColor, String username) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    Integer nextGameID() throws DataAccessException;

    void removeAllGames() throws DataAccessException;
}
