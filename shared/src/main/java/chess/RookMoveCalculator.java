package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator {

    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition startPosition) {

        // get the rook information
        ChessPiece rook = board.getPiece(startPosition);

        // make sure it is a rook
        assert rook.getPieceType() == ChessPiece.PieceType.ROOK: "Not a ROOK";

        // get the team color
        ChessGame.TeamColor team = rook.getTeamColor();

        // set up valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();

        // check all the directions
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 1, 0);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, -1, 0);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 0, 1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 0, -1);

        return validMoves;
    }
}
