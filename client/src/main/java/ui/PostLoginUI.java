package ui;

import java.util.Scanner;

public class PostLoginUI {

    private final ServerFacade server;
    private final Scanner scanner;
    private String authToken;

    public PostLoginUI(ServerFacade server, Scanner scanner) {
        this.server = server;
        this.scanner = scanner;
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

    }

    public void createGame() throws ResponseException {

    }

    public void setAuthToken(String token) {
        authToken = token;
    }
}
