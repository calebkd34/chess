package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator {

    /**
     * Calculates all possible moves by a bishop, not necessarily ones that self-check
     *
     * @param board The chess board to use
     * @param startPosition The starting position of the bishop
     * @return The list of all possible moves
     */
    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition startPosition) {

        // initialize variables
        Collection<ChessMove> validMoves = new ArrayList<>();

        // easy checks
        ChessPiece bishop = board.getPiece(startPosition);
        ChessGame.TeamColor team = bishop.getTeamColor();
        assert bishop.getPieceType() == ChessPiece.PieceType.BISHOP : "Not a BISHOP";

        // check all the directions
        MoveHelper.addSlideMoves(board, startPosition, team, validMoves, 1, 1);
        MoveHelper.addSlideMoves(board, startPosition, team, validMoves, -1, 1);
        MoveHelper.addSlideMoves(board, startPosition, team, validMoves, -1, -1);
        MoveHelper.addSlideMoves(board, startPosition, team, validMoves, 1, -1);

        return validMoves;
    }
}
