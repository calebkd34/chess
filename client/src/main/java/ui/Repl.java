package ui;

import java.util.Scanner;

public class Repl {

    private final ServerFacade server;
    private final Scanner scanner;

    public Repl() {
        server = new ServerFacade(8080);
        scanner = new Scanner(System.in);
    }

    public enum State {
        LOGGED_IN,
        LOGGED_OUT,
        QUIT,
        PLAYING_WHITE,
        PLAYING_BLACK
    }

    public void run() {

        PreLoginUI preLogin = new PreLoginUI(server, scanner);
        PostLoginUI postLogin = new PostLoginUI(server, scanner);
        GameUI playing = new GameUI(server, scanner);
        State state = State.LOGGED_OUT;

        System.out.print("Welcome to CS 240 Chess. Type `help` to get started.\n");
        while (!state.equals(State.QUIT)) {

                if (state == State.LOGGED_OUT) {

                    System.out.print("[LOGGED_OUT] >>> ");
                    state = preLogin.eval(scanner.nextLine());

                    // if they have now logged in, pass the authToken over
                    if (state.equals(State.LOGGED_IN)) {
                        postLogin.setAuthToken(preLogin.getAuthToken());
                    }
                }
                if (state == State.LOGGED_IN) {
                    System.out.print("[LOGGED_IN] >>> ");
                    state = postLogin.eval(scanner.nextLine());
                }
                if (state == State.PLAYING_WHITE) {
                    state = playing.eval(GameUI.Color.WHITE);
                }
                if (state == State.PLAYING_BLACK) {
                    state = playing.eval(GameUI.Color.BLACK);
            }
        }
    }
}
