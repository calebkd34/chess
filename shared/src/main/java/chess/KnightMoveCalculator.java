package chess;

import java.util.*;

public class KnightMoveCalculator {

    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition startPosition) {

        // get the knight information
        ChessPiece knight = board.getPiece(startPosition);

        // make sure it is actually a knight there
        assert knight.getPieceType() == ChessPiece.PieceType.KNIGHT: "Not a knight";

        // get the team color
        ChessGame.TeamColor team = knight.getTeamColor();

        /* possible moves, counting clockwise
        | | | | | | | |
        | | |8| |1| | |
        | |7| | | |2| |
        | | | |N| | | |
        | |6| | | |3| |
        | | |5| |4| | |
        | | | | | | | |
         */

        // all possible positions
        ChessPosition[] possibleMoves = new ChessPosition[8];
        possibleMoves[0] = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 2);
        possibleMoves[1] = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() + 1);
        possibleMoves[2] = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 2);
        possibleMoves[3] = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn() - 1);
        possibleMoves[4] = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 2);
        possibleMoves[5] = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() + 1);
        possibleMoves[6] = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 2);
        possibleMoves[7] = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn() - 1);

        // check each position
        Collection<ChessMove> validMoves = new ArrayList<>();
        for (ChessPosition position : possibleMoves) {
            if (position.isValid()) { // don't add off the board
                // TODO: make sure position.isValid actually works, test cases imply are trying to go off board
                if (board.getPiece(position) == null || board.getPiece(position).getTeamColor() != team) {
                    validMoves.add(new ChessMove(startPosition, position, null));
                }
            }
        }
        return validMoves;
    }
}
