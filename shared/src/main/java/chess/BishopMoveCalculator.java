package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator {

    public static Collection<ChessMove> calculateMoves (ChessBoard board, ChessPosition startPosition) {

        // get the bishop information
        ChessPiece bishop = board.getPiece(startPosition);

        // make sure it is a bishop
        assert bishop.getPieceType() == ChessPiece.PieceType.BISHOP: "Not a BISHOP";

        // get the team color
        ChessGame.TeamColor team = bishop.getTeamColor();

        // prepare valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();

        // check all the directions
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 1, 1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, -1, 1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, -1, -1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 1, -1);

        return validMoves;
    }
}
