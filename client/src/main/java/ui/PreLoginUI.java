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
                System.out.print("""
                        \tregister <USERNAME> <PASSWORD> <EMAIL> to register a new user
                        \tlogin <USERNAME> <PASSWORD> to log in an existing user
                        \tquit - end program
                        \thelp - show this menu
                        """);
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
                System.out.print("Invalid command. Type `help` for how to use the commands.\n");
                return Repl.State.LOGGED_OUT;
            }
        } catch (ResponseException e) {
            System.out.printf("Error: %s\n", e.getMessage());
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
        System.out.printf("Logged in as %s\n", username);
    }

    public String getAuthToken() {return authToken;}
}
