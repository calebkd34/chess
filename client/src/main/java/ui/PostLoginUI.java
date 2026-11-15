package ui;

import java.util.Scanner;

public class PostLoginUI {

    private final ServerFacade server;
    private final Scanner scanner;

    public PostLoginUI(ServerFacade server, Scanner scanner) {
        this.server = server;
        this.scanner = scanner;
    }

    public Repl.State eval(String input) {
        Repl.State state;
        return null;
    }
}
