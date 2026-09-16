package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator {

    public static Collection<ChessMove> calculateMoves (ChessBoard board, ChessPosition startPosition) {

        // get the piece information
        ChessPiece pawn = board.getPiece(startPosition);

        // ensure it is a pawn
        assert pawn.getPieceType() == ChessPiece.PieceType.PAWN: "not a PAWN";

        // get the team color
        ChessGame.TeamColor teamColor = pawn.getTeamColor();

        // prepare the valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();

        // black pawns and white pawns are different
        if (teamColor == ChessGame.TeamColor.WHITE) {

            if (startPosition.getRow() == 2) { // pawn is at the start, so can move two

            }

        }


        return validMoves;
    }
}
