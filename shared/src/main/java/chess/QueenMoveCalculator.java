package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoveCalculator {

    public static Collection<ChessMove> calculateMoves (ChessBoard board, ChessPosition startPosition) {

        // get the queen information
        ChessPiece queen = board.getPiece(startPosition);

        // check it is a queen
        assert queen.getPieceType() == ChessPiece.PieceType.QUEEN: "Not a QUEEN";

        // get the team color
        ChessGame.TeamColor team = queen.getTeamColor();

        // prepare valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();

        // check all the directions
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 1, 0);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 1, 1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 0, 1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, -1, 1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, -1, 0);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, -1, -1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 0, -1);
        MoveHelper.slideMoves(board, startPosition, team, validMoves, 1, -1);

        return validMoves;
    }
}
