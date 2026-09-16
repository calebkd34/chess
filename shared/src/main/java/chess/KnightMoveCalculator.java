package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Calculates valid moves for knight.
 */
public class KnightMoveCalculator {

    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition startPosition) {

        // get the knight information
        ChessPiece knight = board.getPiece(startPosition);

        // make sure it is actually a knight there
        assert knight.getPieceType() == ChessPiece.PieceType.KNIGHT: "Not a KNIGHT";

        // get the team color
        ChessGame.TeamColor teamColor = knight.getTeamColor();

        /* possible moves, counting clockwise
        | | | | | | | |
        | | |8| |1| | |
        | |7| | | |2| |
        | | | |N| | | |
        | |6| | | |3| |
        | | |5| |4| | |
        | | | | | | | |
         */

        // all possible positions
        ChessPosition[] possibleEndPositions = new ChessPosition[8];
        possibleEndPositions[0] = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 2);
        possibleEndPositions[1] = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() + 1);
        possibleEndPositions[2] = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 2);
        possibleEndPositions[3] = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() - 1);
        possibleEndPositions[4] = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 2);
        possibleEndPositions[5] = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() + 1);
        possibleEndPositions[6] = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 2);
        possibleEndPositions[7] = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() - 1);

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