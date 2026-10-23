package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {

    private static final Map<String, UserData> userDataMap = new HashMap<>();

    @Override
    public void createUser(UserData user) {
        userDataMap.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) {
        return userDataMap.getOrDefault(username, null);
    }

    @Override
    public void removeUser(UserData user) {
        userDataMap.remove(user.username());
    }

    @Override
    public void removeAllUsers() {
    userDataMap.clear();
    }
}
