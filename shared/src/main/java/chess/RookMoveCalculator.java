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
        ChessGame.TeamColor teamColor = rook.getTeamColor();

        // set up valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();

        // check to the north
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 1, 0);

        // check to the south
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, -1, 0);

        // check to the east
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 0, 1);

        // check to the west
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 0, -1);

        return validMoves;
    }
}
