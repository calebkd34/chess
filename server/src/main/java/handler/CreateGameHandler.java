package handler;

import io.javalin.http.Context;
import json.Serializer;
import request.CreateGameRequest;
import result.CreateGameResult;
import service.BadRequestException;
import service.GameService;

public class CreateGameHandler extends Handler{

    public static final GameService gameService = new GameService();

    public void handleRequest(Context context) {

        CreateGameResult result;
    }
}
