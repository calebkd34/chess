package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Calculates valid moves for king.
 */
public class KingMoveCalculator {

    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition startPosition) {

        // get the king information
        ChessPiece king = board.getPiece(startPosition);

        // make sure it is actually a king
        assert king.getPieceType() == ChessPiece.PieceType.KING: "Not a KING!";

        // get the team color
        ChessGame.TeamColor teamColor = king.getTeamColor();

        /* possible moves, counting clockwise
        | | | | | | | |
        | | | | | | | |
        | | |7|8|1| | |
        | | |6|K|2| | |
        | | |5|4|3| | |
        | | | | | | | |
        | | | | | | | |
         */

        // all possible end positions
        ChessPosition[] possibleEndPositions = new ChessPosition[8];
        possibleEndPositions[0] = new ChessPosition(startPosition.getRow() + 1,startPosition.getColumn() + 1);
        possibleEndPositions[1] = new ChessPosition(startPosition.getRow() + 1,startPosition.getColumn() - 1);
        possibleEndPositions[2] = new ChessPosition(startPosition.getRow() - 1,startPosition.getColumn() + 1);
        possibleEndPositions[3] = new ChessPosition(startPosition.getRow() - 1,startPosition.getColumn() - 1);
        possibleEndPositions[4] = new ChessPosition(startPosition.getRow() + 1,startPosition.getColumn());
        possibleEndPositions[5] = new ChessPosition(startPosition.getRow() - 1,startPosition.getColumn());
        possibleEndPositions[6] = new ChessPosition(startPosition.getRow(),startPosition.getColumn() + 1);
        possibleEndPositions[7] = new ChessPosition(startPosition.getRow(),startPosition.getColumn() - 1);

        // check each position
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (ChessPosition endPosition : possibleEndPositions) { // check each move
            if (endPosition.isValid()) { // don't add off the board moves

                // must be empty or a capturable piece
                if (board.getPiece(endPosition) == null || board.getPiece(endPosition).getTeamColor() != teamColor) {
                    validMoves.add(new ChessMove(startPosition, endPosition, null));
                }
            }
        }
        return validMoves;
    }
}
