package ui;

import result.ListGamesResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PostLoginUI {

    private final ServerFacade server;
    private final Scanner scanner;
    private Map<Integer, Integer> gameIDs;
    private String authToken;

    public PostLoginUI(ServerFacade server, Scanner scanner) {
        this.server = server;
        this.scanner = scanner;
        gameIDs = new HashMap<>();
    }

    public Repl.State eval(String input) {
        try {
            if (input.equalsIgnoreCase("help")) {
                System.out.print("""
                        \tlogout
                        \tlist games
                        \tcreate game
                        \tplay game
                        \tobserve game
                        \tquit
                        \thelp
                        """);
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("quit")) {
                return Repl.State.QUIT;
            } else if (input.equalsIgnoreCase("logout")) {
                server.logout(authToken);
                return Repl.State.LOGGED_OUT;
            } else if (input.equalsIgnoreCase("list games")) {
                listGames();
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("create game")) {
                createGame();
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("play game")) {
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("observe game")) {
                return Repl.State.LOGGED_IN;
            } else {
                System.out.print("Invalid command. Type `help` for how to use the commands.\n");
                return Repl.State.LOGGED_IN;
            }

        } catch (ResponseException e) {
            System.out.printf("ErrorL %s\n", e.getMessage());
        }
        return Repl.State.LOGGED_IN;
    }


    public void listGames() throws ResponseException {
        Collection<ListGamesResult.Game> games = server.listGames(authToken);
        gameIDs.clear();
        int i = 1;
        System.out.print(" # | Game Name | Black Player | White Player |\n");
        for (ListGamesResult.Game game : games) {
            gameIDs.put(i++, game.gameID());
            System.out.printf(
                    "%s | %s | %s | %s |\n",
                    game.gameID(),
                    game.gameName(),
                    game.blackUsername(),
                    game.whiteUsername()
            );
        }
    }

    public void createGame() throws ResponseException {
        System.out.print("\tGame Name: ");
        String gameName = scanner.nextLine();
        server.createGame(authToken, gameName);
    }

    public void setAuthToken(String token) {
        authToken = token;
    }
}
