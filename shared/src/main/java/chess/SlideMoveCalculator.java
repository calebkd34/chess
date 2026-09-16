package chess;

import java.util.Collection;

public class SlideMoveCalculator {

    public static void calculateMoves(
            ChessBoard board,
            ChessPosition startPosition,
            ChessGame.TeamColor teamColor,
            Collection<ChessMove> validMoves,
            int rowChange,
            int columnChange) {

        int row = startPosition.getRow();
        int col = startPosition.getColumn();

        while (true) {
            row += rowChange;
            col += columnChange;
            ChessPosition currentPosition = new ChessPosition(row, col);
            if (currentPosition.isValid()) {

                if (board.getPiece(currentPosition) == null) { // if the space is empty add it and keep going
                    validMoves.add(new ChessMove(startPosition, currentPosition, null));
                }

                else { // if enemy capture it, always stop
                    if (board.getPiece(currentPosition).getTeamColor() != teamColor) {
                        validMoves.add(new ChessMove(startPosition, currentPosition, null));
                    }
                    break;
                }

            }
            else break; // the space is off the board so stop
        }
    }
}
