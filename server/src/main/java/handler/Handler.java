package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import service.AuthService;
import service.ClearService;
import service.GameService;
import service.UserService;

public class Handler {

    public static final AuthService authService = new AuthService();
    public static final ClearService clearService = new ClearService();
    public static final GameService gameService = new GameService();
    public static final UserService userService = new UserService();
}
