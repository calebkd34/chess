package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO{

    private static final Map<String, AuthData> authDataMap = new HashMap<>();

    @Override
    public void addAuth(AuthData authData) {
        authDataMap.put(authData.authToken(), authData);
    }

    @Override
    public AuthData getAuth(String authToken) {
        return authDataMap.get(authToken);
    }

    @Override
    public void removeAuth(AuthData authData) {
        authDataMap.remove(authData.authToken());
    }

    @Override
    public void removeAllAuths() {
        authDataMap.clear();
    }

}
