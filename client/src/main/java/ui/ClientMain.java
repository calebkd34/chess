package ui;

public class ClientMain {
    public static void main(String[] args) {
        String serverURL = "http://localhost:8080";
        if (args.length == 1) {
            serverURL = args[0];
        }

        try {
            new PreLoginUI().run();

        } catch (ResponseException e) {
            System.out.printf("Unable to connect to server: %s%n", e.getMessage());
        }

    }
}
