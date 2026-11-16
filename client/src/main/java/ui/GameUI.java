package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Map;
import java.util.Scanner;

public class GameUI {

    private final ServerFacade server;
    private final Scanner scanner;
    private String authToken;

    public GameUI(ServerFacade server, Scanner scanner) {
        this.server = server;
        this.scanner = scanner;
    }

    public Repl.State eval(Color color) {
        try {
            String input = scanner.nextLine();
            // help
            if (input.equalsIgnoreCase("help")) {
                System.out.print("Help menu not implemented\n");
            }
            // quit
            else if (input.equalsIgnoreCase("quit")) {
                return Repl.State.QUIT;
            } else {
                System.out.print("\t%s is not a valid option\n");
            }
        } catch (Exception e) {
            System.out.printf(
                    "%sError: %s%s\n",
                    EscapeSequences.SET_TEXT_COLOR_RED,
                    e.getMessage(),
                    EscapeSequences.RESET_TEXT_COLOR
            );
        }
        return (color == Color.BLACK) ? Repl.State.PLAYING_BLACK : Repl.State.PLAYING_WHITE;
    }

    public static final Map<String, String> pieces = Map.ofEntries(
            Map.entry("B", EscapeSequences.WHITE_BISHOP),
            Map.entry("b", EscapeSequences.BLACK_BISHOP),
            Map.entry("K", EscapeSequences.WHITE_KING),
            Map.entry("k", EscapeSequences.BLACK_KING),
            Map.entry("N", EscapeSequences.WHITE_KNIGHT),
            Map.entry("n", EscapeSequences.BLACK_KNIGHT),
            Map.entry("P", EscapeSequences.WHITE_PAWN),
            Map.entry("p", EscapeSequences.BLACK_PAWN),
            Map.entry("Q", EscapeSequences.WHITE_QUEEN),
            Map.entry("q", EscapeSequences.BLACK_QUEEN),
            Map.entry("R", EscapeSequences.WHITE_ROOK),
            Map.entry("r", EscapeSequences.BLACK_ROOK)
    );

    public enum Color {
        BLACK,
        WHITE
    }

    public static void whitePerspective(ChessGame game) {
        ChessBoard board = game.getBoard();
        boolean darkSquare = false;

        System.out.print("    a  b  c  d  e  f  g  h \n");
        for (int i = 8; i > 0; i--) {
            System.out.printf(" %s ", i);
            for (int j = 1; j <= 8; j++) {
                printSquare(
                        (darkSquare) ? Color.BLACK : Color.WHITE,
                        board.getPiece(new ChessPosition(i, j))
                );
                darkSquare = !darkSquare;
            }
            darkSquare = !darkSquare;
            System.out.printf(" %s \n", i);
        }
        System.out.print(" a  b  c  d  e  f  g  h \n");
    }

    public static void blackPerspective(ChessGame game) {
        ChessBoard board = game.getBoard();
        boolean darkSquare = false;

        System.out.print("    h  g  f  e  d  c  b  a \n");
        for (int i = 1; i <= 8; i++) {
            System.out.printf(" %s ", i);
            for (int j = 1; j <= 8; j++) {
                printSquare(
                        (darkSquare) ? Color.BLACK : Color.WHITE,
                        board.getPiece(new ChessPosition(i, j))
                );
                darkSquare = !darkSquare;
            }
            System.out.printf(" %s \n", i);
        }
        System.out.print(" h  g  f  e  d  c  b  a \n");
    }

    private static void printSquare(Color color, ChessPiece piece) {
        System.out.printf(
                "%s%s%s",
                (color == Color.BLACK) ? EscapeSequences.SET_BG_COLOR_BLACK : EscapeSequences.SET_BG_COLOR_WHITE,
                (piece != null) ? pieces.get(piece.toString()) : "   ",
                EscapeSequences.RESET_BG_COLOR
        );
    }
}
