package handler;

import service.ClearService;
import service.GameService;
import service.UserService;

public class Handler {

    public static final ClearService clearService = new ClearService();
    public static final GameService gameService = new GameService();
    public static final UserService userService = new UserService();
}
