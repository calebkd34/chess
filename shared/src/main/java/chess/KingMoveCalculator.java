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
        int x = startPosition.getRow();
        int y = startPosition.getColumn();

        // make sure it is actually a king
        assert king.getPieceType() == ChessPiece.PieceType.KING: "Not a KING!";

        // get the team color
        ChessGame.TeamColor team = king.getTeamColor();

        // all possible end positions
        ChessPosition[] possibleEndPositions = new ChessPosition[8];
        possibleEndPositions[0] = new ChessPosition(x + 1,y + 1);
        possibleEndPositions[1] = new ChessPosition(x + 1,y - 1);
        possibleEndPositions[2] = new ChessPosition(x - 1,y + 1);
        possibleEndPositions[3] = new ChessPosition(x - 1,y - 1);
        possibleEndPositions[4] = new ChessPosition(x + 1,y);
        possibleEndPositions[5] = new ChessPosition(x - 1,y);
        possibleEndPositions[6] = new ChessPosition(x,y + 1);
        possibleEndPositions[7] = new ChessPosition(x,y - 1);

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
