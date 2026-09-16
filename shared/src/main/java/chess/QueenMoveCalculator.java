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
        ChessGame.TeamColor teamColor = queen.getTeamColor();

        // prepare valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();

        // check the north
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 1, 0);

        // check the northeast
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 1, 1);

        // check the east
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 0, 1);

        // check the southeast
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, -1, 1);

        // check the south
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, -1, 0);

        // check the southwest
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, -1, -1);

        // check the west
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 0, -1);

        // check the northwest
        SlideMoveCalculator.calculateMoves(board, startPosition, teamColor, validMoves, 1, -1);

        return validMoves;
    }
}
