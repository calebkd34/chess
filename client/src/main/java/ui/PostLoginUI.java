package ui;

import result.ListGamesResult;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PostLoginUI {

    private final ServerFacade server;
    private final Scanner scanner;
    private final Map<Integer, Integer> gameIDs;
    private String authToken;

    public PostLoginUI(ServerFacade server, Scanner scanner) {
        this.server = server;
        this.scanner = scanner;
        gameIDs = new HashMap<>();
    }

    public Repl.State eval(String input) {
        try {
            if (input.equalsIgnoreCase("help")) {
                System.out.printf("""
                                \t%slogout %s - log out
                                \t%slist games %s - list all available games
                                \t%screate game %s - create a new game
                                \t%splay game %s - join an existing game
                                \t%sobserve game %s - observe and existing game
                                \t%squit %s - end program
                                \t%shelp %s - view this menu
                                """,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR
                );
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("quit")) {
                return Repl.State.QUIT;
            } else if (input.equalsIgnoreCase("logout")) {
                server.logout(authToken);
                System.out.printf(
                        "%sLogged out successfully%s",
                        EscapeSequences.SET_TEXT_ITALIC,
                        EscapeSequences.RESET_TEXT_ITALIC
                );
                return Repl.State.LOGGED_OUT;
            } else if (input.equalsIgnoreCase("list games")) {
                listGames();
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("create game")) {
                createGame();
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("play game")) {
                joinGame();
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("observe game")) {
                return Repl.State.LOGGED_IN;
            } else {
                System.out.printf(
                        "%sInvalid command. Type `help` for how to use the commands.%s\n",
                        EscapeSequences.SET_TEXT_COLOR_RED,
                        EscapeSequences.RESET_TEXT_COLOR
                );
                return Repl.State.LOGGED_IN;
            }

        } catch (ResponseException e) {
            System.out.printf(
                    "%sError: %s%s\n",
                    EscapeSequences.SET_TEXT_COLOR_RED,
                    e.getMessage(),
                    EscapeSequences.RESET_TEXT_COLOR
            );
        }
        return Repl.State.LOGGED_IN;
    }

    public void listGames() throws ResponseException {
        Collection<ListGamesResult.Game> games = server.listGames(authToken);
        gameIDs.clear();
        int i = 0;

        // column widths
        int nameWidth = 9;
        int blackWidth = 12;
        int whiteWidth = 12;
        int indexWidth = Integer.toString(games.size()).length();

        // get the width of each section
        for (ListGamesResult.Game game : games) {
            nameWidth = Integer.max(nameWidth, game.gameName().length());
            if (game.blackUsername() != null) {
                blackWidth = Integer.max(blackWidth, game.blackUsername().length());
            }
            if (game.whiteUsername() != null) {
                whiteWidth = Integer.max(whiteWidth, game.whiteUsername().length());
            }
        }

        System.out.printf(
                "%s| #%s | Game Name%s | Black Player%s | White Player%s |%s\n",
                EscapeSequences.SET_TEXT_UNDERLINE,
                " ".repeat(indexWidth - 1),
                " ".repeat(nameWidth - 9),
                " ".repeat(blackWidth - 12),
                " ".repeat(whiteWidth - 12),
                EscapeSequences.RESET_TEXT_UNDERLINE
        );
        for (ListGamesResult.Game game : games) {
            i++;
            gameIDs.put(i, game.gameID());
            String blackEntry;
            String whiteEntry;
            if (game.blackUsername() != null) {
                blackEntry = game.blackUsername() + " ".repeat(blackWidth - game.blackUsername().length());
            } else {
                blackEntry = " ".repeat(blackWidth);
            }
            if (game.whiteUsername() != null) {
                whiteEntry = game.whiteUsername() + " ".repeat(whiteWidth - game.whiteUsername().length());
            } else {
                whiteEntry = " ".repeat(whiteWidth);
            }

            System.out.printf(
                    "| %s%s | %s%s | %s | %s |\n",
                    i, " ".repeat(indexWidth - Integer.toString(games.size()).length()),
                    game.gameName(), " ".repeat(nameWidth - game.gameName().length()),
                    blackEntry,
                    whiteEntry
            );
        }
    }

    public void createGame() throws ResponseException {
        System.out.print("\tGame Name: ");
        String gameName = scanner.nextLine();
        server.createGame(authToken, gameName);
    }

    public void joinGame() throws ResponseException {
        try {
            System.out.print("\tGame Number: ");
            int gameIndex = Integer.parseInt(scanner.nextLine());
            System.out.print("\tJoin game as <BLACK/WHITE>: ");
            String playerColor = scanner.nextLine();
            if (playerColor.equalsIgnoreCase("black")) {
                server.joinGame(authToken, gameIDs.get(gameIndex), "BLACK");
                System.out.print("Joined game as black\n");
            } else if (playerColor.equalsIgnoreCase("white")) {
                server.joinGame(authToken, gameIDs.get(gameIndex), "WHITE");
                System.out.print("Joined game as white\n");
            } else {
                System.out.print("\t%s is not a valid option\n");
            }
        } catch (NumberFormatException e) {
            System.out.printf(
                    "\t%sThat is not a valid game number%s\n",
                    EscapeSequences.SET_TEXT_COLOR_RED,
                    EscapeSequences.RESET_TEXT_COLOR
            );
        }
    }

    public void setAuthToken(String token) {
        authToken = token;
    }
}
