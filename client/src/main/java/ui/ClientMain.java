package ui;

public class ClientMain {
    public static void main(String[] args) {

        try {
            new Repl().run();

        } catch (Exception e) {
            System.out.printf("Unknown Error: %s%n", e.getMessage());
        }

    }
}
