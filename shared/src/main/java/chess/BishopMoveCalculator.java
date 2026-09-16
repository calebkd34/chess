package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoveCalculator {

    public static Collection<ChessMove> calculateMoves (ChessBoard board, ChessPosition startPosition) {

        // get the bishop information
        ChessPiece bishop = board.getPiece(startPosition);

        // make sure it is a bishop
        assert bishop.getPieceType() == ChessPiece.PieceType.BISHOP: "Not a bishop";

        // get the team color
        ChessGame.TeamColor teamColor = bishop.getTeamColor();

        // prepare valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();

        // check the northeast
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 1, 1);

        // check the southeast
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, -1, 1);

        // check the southwest
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, -1, -1);

        // check the northwest
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 1, -1);

        return validMoves;
    }
}
