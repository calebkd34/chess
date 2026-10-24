package handler;

import service.ClearService;
import service.GameService;
import service.UserService;

public class Handler {

    public static final ClearService CLEAR_SERVICE = new ClearService();
    public static final GameService GAME_SERVICE = new GameService();
    public static final UserService USER_SERVICE = new UserService();
}
