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
        int x = startPosition.getRow();
        int y = startPosition.getColumn();

        // make sure it is actually a knight there
        assert knight.getPieceType() == ChessPiece.PieceType.KNIGHT: "Not a KNIGHT";

        // get the team color
        ChessGame.TeamColor team = knight.getTeamColor();

        // all possible positions
        ChessPosition[] possibleEndPositions = new ChessPosition[8];
        possibleEndPositions[0] = new ChessPosition(x + 1, y + 2);
        possibleEndPositions[1] = new ChessPosition(x + 2, y + 1);
        possibleEndPositions[2] = new ChessPosition(x + 1, y - 2);
        possibleEndPositions[3] = new ChessPosition(x + 2, y - 1);
        possibleEndPositions[4] = new ChessPosition(x - 1, y + 2);
        possibleEndPositions[5] = new ChessPosition(x - 2, y + 1);
        possibleEndPositions[6] = new ChessPosition(x - 1, y - 2);
        possibleEndPositions[7] = new ChessPosition(x - 2, y - 1);

        // check each position
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (ChessPosition endPosition : possibleEndPositions) { // check each move
            if (MoveHelper.check(board, endPosition, team)) {
                validMoves.add(new ChessMove(startPosition, endPosition, null));
            }
        }
        return validMoves;
    }
}