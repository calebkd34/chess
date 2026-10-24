package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO{

    private static final Map<String, AuthData> AUTH_DATA_MAP = new HashMap<>();

    @Override
    public void addAuth(AuthData authData) {
        AUTH_DATA_MAP.put(authData.authToken(), authData);
    }

    @Override
    public AuthData getAuth(String authToken) {
        return AUTH_DATA_MAP.get(authToken);
    }

    @Override
    public void removeAuth(AuthData authData) {
        AUTH_DATA_MAP.remove(authData.authToken());
    }

    @Override
    public void removeAllAuths() {
        AUTH_DATA_MAP.clear();
    }

}
