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
        ChessPosition testPosition;

        // black pawns and white pawns are different
        // note: we should not have to check if the new position is valid because a pawn can't start a turn at the edge
        if (teamColor == ChessGame.TeamColor.WHITE) {

            // check the space(s) immediately ahead
            testPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn());
            if (testPosition.isValid() && board.getPiece(testPosition) == null) { // empty space

                if (testPosition.getRow() < 8) { // not the end of the board
                    validMoves.add(new ChessMove(startPosition, testPosition, null));
                }
                else { // reached the end of the board
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                }

                // starting piece can move two forward
                if (startPosition.getRow() == 2) {
                    testPosition = new ChessPosition(startPosition.getRow() + 2, startPosition.getColumn());
                    if (board.getPiece(testPosition) == null) { // empty space
                        validMoves.add(new ChessMove(startPosition, testPosition, null));
                    }
                }
            }

            // check the left capture space
            testPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() - 1);
            if (
                    testPosition.isValid() &&
                    board.getPiece(testPosition) != null &&
                    board.getPiece(testPosition).getTeamColor() != teamColor) {

                if (testPosition.getRow() < 8) { // not the end
                    validMoves.add(new ChessMove(startPosition, testPosition, null));
                }
                else {
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                }
            }

            // check the right capture space
            testPosition = new ChessPosition(startPosition.getRow() + 1, startPosition.getColumn() + 1);
            if (
                    testPosition.isValid() &&
                    board.getPiece(testPosition) != null &&
                    board.getPiece(testPosition).getTeamColor() != teamColor) {

                if (testPosition.getRow() < 8) { // not the end
                    validMoves.add(new ChessMove(startPosition, testPosition, null));
                }
                else {
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                }
            }
        }

        if (teamColor == ChessGame.TeamColor.BLACK) {

            // check the space(s) immediately ahead
            testPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn());
            if (testPosition.isValid() && board.getPiece(testPosition) == null) { // empty space

                if (testPosition.getRow() > 1) { // not the end of the board
                    validMoves.add(new ChessMove(startPosition, testPosition, null));
                }
                else { // reached the end of the board
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                }

                // starting piece can move two forward
                if (startPosition.getRow() == 7) {
                    testPosition = new ChessPosition(startPosition.getRow() - 2, startPosition.getColumn());
                    if (board.getPiece(testPosition) == null) { // empty space
                        validMoves.add(new ChessMove(startPosition, testPosition, null));
                    }
                }
            }

            // check the left capture space
            testPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() - 1);
            if (
                    testPosition.isValid() &&
                    board.getPiece(testPosition) != null &&
                    board.getPiece(testPosition).getTeamColor() != teamColor) {

                if (testPosition.getRow() > 1) { // not the end
                    validMoves.add(new ChessMove(startPosition, testPosition, null));
                }
                else {
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                }
            }

            // check the right capture space
            testPosition = new ChessPosition(startPosition.getRow() - 1, startPosition.getColumn() + 1);
            if (
                    testPosition.isValid() &&
                    board.getPiece(testPosition) != null &&
                    board.getPiece(testPosition).getTeamColor() != teamColor) {

                if (testPosition.getRow() > 1) { // not the end
                    validMoves.add(new ChessMove(startPosition, testPosition, null));
                }
                else {
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                    validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                }
            }
        }

        return validMoves;
    }
}
