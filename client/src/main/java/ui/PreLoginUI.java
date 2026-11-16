package ui;

import java.util.Scanner;

public class PreLoginUI {

    private String authToken;
    private final ServerFacade server;
    private final Scanner scanner;

    public PreLoginUI(ServerFacade server, Scanner scanner) {
        this.server = server;
        this.scanner = scanner;
    }

    public Repl.State eval(String input) {

        try {
            if (input.equalsIgnoreCase("help")) {
                System.out.printf("""
                                \t%sregister %s- to register a new user
                                \t%slogin %s- to log in an existing user
                                \t%squit %s- end program
                                \t%shelp %s- show this menu
                                """,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR,
                        EscapeSequences.SET_TEXT_COLOR_BLUE,
                        EscapeSequences.RESET_TEXT_COLOR);
                return Repl.State.LOGGED_OUT;
            } else if (input.equalsIgnoreCase("quit")) {
                return Repl.State.QUIT;
            } else if (input.equalsIgnoreCase("register")) {
                register();
                return Repl.State.LOGGED_IN;
            } else if (input.equalsIgnoreCase("login")) {
                login();
                return Repl.State.LOGGED_IN;
            } else {
                System.out.printf(
                        "%sInvalid command. Type `help` for how to use the commands.%s\n",
                        EscapeSequences.SET_TEXT_COLOR_RED,
                        EscapeSequences.RESET_TEXT_COLOR
                );
                return Repl.State.LOGGED_OUT;
            }
        } catch (ResponseException e) {
            System.out.printf(
                    "%sError: %s%s\n",
                    EscapeSequences.SET_TEXT_COLOR_RED,
                    e.getMessage(),
                    EscapeSequences.RESET_TEXT_COLOR
            );
            return Repl.State.LOGGED_OUT;
        }
    }

    public void register() throws ResponseException {

        System.out.print("\tusername: ");
        String username = scanner.nextLine();
        System.out.print("\tpassword: ");
        String password = scanner.nextLine();
        System.out.print("\temail: ");
        String email = scanner.nextLine();

        authToken = server.register(username, password, email).authToken();
        System.out.printf("Registered as %s\n", username);
    }

    private void login() throws ResponseException {

        System.out.print("\tusername: ");
        String username = scanner.nextLine();
        System.out.print("\tpassword: ");
        String password = scanner.nextLine();

        authToken = server.login(username, password).authToken();
        System.out.printf(
                "%sLogged in as %s%s\n",
                EscapeSequences.SET_TEXT_ITALIC,
                username,
                EscapeSequences.RESET_TEXT_ITALIC
        );
    }

    public String getAuthToken() {
        return authToken;
    }
}
