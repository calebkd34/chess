package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {

    private static final Map<String, UserData> USER_DATA_MAP = new HashMap<>();

    @Override
    public void createUser(UserData user) {
        USER_DATA_MAP.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) {
        return USER_DATA_MAP.getOrDefault(username, null);
    }

    @Override
    public void removeAllUsers() {
    USER_DATA_MAP.clear();
    }
}
