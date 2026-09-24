package chess;

import java.util.Collection;

public class MoveHelper {

    public static boolean check(
            ChessBoard board,
            ChessPosition endPosition,
            ChessGame.TeamColor team) {

        if (endPosition.isValid()) {
            return board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != team;
        } else return false;
    }

    public static void slideMoves(
            ChessBoard board,
            ChessPosition startPosition,
            ChessGame.TeamColor team,
            Collection<ChessMove> validMoves,
            int xDiff, int yDiff) {

        // start at the startPosition, then go in the given direction
        int x = startPosition.getRow();
        int y = startPosition.getColumn();
        ChessPosition testPosition;
        while (true) {
            x += xDiff;
            y += yDiff;
            testPosition = new ChessPosition(x, y);
            if (check(board, testPosition, team)) {
                validMoves.add(new ChessMove(startPosition, testPosition, null));
                if (board.getPiece(testPosition) != null) break; // break after capturing
            } else break; // break if move not valid
        }
    }
}