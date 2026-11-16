package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Map;

public class GameUI {

    public static final Map<String, String> pieces = Map.ofEntries(
            Map.entry("B", "B"),
            Map.entry("b", "B"),
            Map.entry("K", "K"),
            Map.entry("k", "K"),
            Map.entry("N", "N"),
            Map.entry("n", "N"),
            Map.entry("P", "P"),
            Map.entry("p", "P"),
            Map.entry("Q", "Q"),
            Map.entry("q", "Q"),
            Map.entry("R", "R"),
            Map.entry("r", "R")
    );

    public enum Color {
        BLACK,
        WHITE
    }

    public static void whitePerspective(ChessGame game) {
        ChessBoard board = game.getBoard();
        boolean darkSquare = false;

        System.out.print("    a  b  c  d  e  f  g  h\n");
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
        System.out.print("    a  b  c  d  e  f  g  h\n");
    }

    public static void blackPerspective(ChessGame game) {
        ChessBoard board = game.getBoard();
        boolean darkSquare = false;

        System.out.print("    h  g  f  e  d  c  a\n");
        for (int i = 1; i <= 8; i++) {
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
        System.out.print("    h  g  f  e  d  c  a\n");
    }

    private static void printSquare(Color color, ChessPiece piece) {
        System.out.printf(
                "%s%s %s %s%s",
                (piece != null && piece.getTeamColor() == ChessGame.TeamColor.BLACK)
                        ? EscapeSequences.SET_TEXT_COLOR_RED : EscapeSequences.SET_BG_COLOR_YELLOW,
                (color == Color.BLACK) ? EscapeSequences.SET_BG_COLOR_BLACK : EscapeSequences.SET_BG_COLOR_WHITE,
                (piece != null) ? pieces.get(piece.toString()) : " ",
                EscapeSequences.RESET_TEXT_COLOR,
                EscapeSequences.RESET_BG_COLOR
        );
    }
}
