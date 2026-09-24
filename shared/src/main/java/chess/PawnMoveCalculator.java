package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator {

    public static Collection<ChessMove> calculateMoves (ChessBoard board, ChessPosition startPosition) {

        // get the piece information
        ChessPiece pawn = board.getPiece(startPosition);
        int x = startPosition.getRow();
        int y = startPosition.getColumn();

        // ensure it is a pawn
        assert pawn.getPieceType() == ChessPiece.PieceType.PAWN: "not a PAWN";

        // get the team color
        ChessGame.TeamColor team = pawn.getTeamColor();

        // prepare the valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();


        // assign to black and white
        int i; if (team == ChessGame.TeamColor.WHITE) i = 1; else i = -1;

        // check the startRow
        int startRow; if (team == ChessGame.TeamColor.BLACK) startRow = 7; else startRow = 2;
        int endRow; if (team == ChessGame.TeamColor.WHITE) endRow = 8; else endRow = 1;

        // check the space directly forward
        ChessPosition testPosition = new ChessPosition(x + i, y);
        if (testPosition.isValid() && board.getPiece(testPosition) == null) {
            if (testPosition.getRow() != endRow) { // cannot promote
                validMoves.add(new ChessMove(startPosition, testPosition, null));
            } else { // can promote
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
            }

            // check the space two-forward
            if (startPosition.getRow() == startRow) {
                testPosition = new ChessPosition(x + i * 2, y);
                if (testPosition.isValid() && board.getPiece(testPosition) == null) {
                    validMoves.add(new ChessMove(startPosition, testPosition, null));
                }
            }
        }

        // check the space forward-west
        testPosition = new ChessPosition(x + i, y + 1);
        if (testPosition.isValid() &&
                board.getPiece(testPosition) != null &&
                board.getPiece(testPosition).getTeamColor() != team) {
            if (testPosition.getRow() != endRow) { // cannot promote
                validMoves.add(new ChessMove(startPosition, testPosition, null));
            } else { // can promote
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
            }
        }

        // check the space forward-east
        testPosition = new ChessPosition(x + i, y - 1);
        if (testPosition.isValid() &&
                board.getPiece(testPosition) != null &&
                board.getPiece(testPosition).getTeamColor() != team) {
            if (testPosition.getRow() != endRow) { // cannot promote
                validMoves.add(new ChessMove(startPosition, testPosition, null));
            } else { // can promote
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.QUEEN));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.BISHOP));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.ROOK));
                validMoves.add(new ChessMove(startPosition, testPosition, ChessPiece.PieceType.KNIGHT));
            }
        }

        return validMoves;
    }
}
