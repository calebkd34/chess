package ui;

import java.util.Scanner;

public class PreLoginUI {

    private final ServerFacade server;
    private final Scanner scanner;

    public PreLoginUI() throws ResponseException {
        server = new ServerFacade(8080);
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.print("Welcome to CS 240 Chess. Type `help` to get started.\n");

        String result = "start";
        while (!result.equals("Quitting\n")) {
            System.out.print("[LOGGED_OUT] >>> ");
            String line = scanner.nextLine();
            try {
                result = eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                System.out.print(e.toString());
            }
        }
    }

    private String eval(String line) {

        try {
            return switch (line) {
                case "help" -> """
                        \tregister <USERNAME> <PASSWORD> <EMAIL> to register a new user
                        \tlogin <USERNAME> <PASSWORD> to log in an existing user
                        \tquit - end program
                        \thelp - show this menu
                        """;
                case "quit" -> "Quitting\n";
                case "register" -> register();
                case "login" -> login();
                default -> "Invalid Command. Type `help` for how to use the commands.\n";
            };
        } catch (ResponseException e) {
            return "Error: %s\n".formatted(e.getMessage());
        }
    }

    public String register() throws ResponseException {

        System.out.print("\tusername: ");
        String username = scanner.nextLine();
        System.out.print("\tpassword: ");
        String password = scanner.nextLine();
        System.out.print("\temail: ");
        String email = scanner.nextLine();

        server.register(username, password, email);
        return "Registered as %s\n".formatted(username);
    }

    private String login() {
        return null;
    }

}
