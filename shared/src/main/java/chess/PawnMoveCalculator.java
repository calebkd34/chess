package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator {

    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition startPosition) {

        // get the piece information
        ChessPiece pawn = board.getPiece(startPosition);
        int x = startPosition.getRow();
        int y = startPosition.getColumn();

        // ensure it is a pawn
        assert pawn.getPieceType() == ChessPiece.PieceType.PAWN : "not a PAWN";

        // get the team color
        ChessGame.TeamColor team = pawn.getTeamColor();

        // prepare the valid moves
        Collection<ChessMove> validMoves = new ArrayList<>();


        // assign to black and white
        int i;
        if (team == ChessGame.TeamColor.WHITE) {
            i = 1;
        } else {
            i = -1;
        }

        // check the startRow
        int startRow;
        if (team == ChessGame.TeamColor.BLACK) {
            startRow = 7;
        } else {
            startRow = 2;
        }
        int endRow;
        if (team == ChessGame.TeamColor.WHITE) {
            endRow = 8;
        } else {
            endRow = 1;
        }

        // check the space directly forward
        ChessPosition forwardPosition = new ChessPosition(x + i, y);
        if (forwardPosition.isValid() && board.getPiece(forwardPosition) == null) {
            if (forwardPosition.getRow() != endRow) { // cannot promote
                validMoves.add(new ChessMove(startPosition, forwardPosition, null));
            } else { // can promote
                validMoves.add(new ChessMove(startPosition, forwardPosition, ChessPiece.PieceType.QUEEN));
                validMoves.add(new ChessMove(startPosition, forwardPosition, ChessPiece.PieceType.BISHOP));
                validMoves.add(new ChessMove(startPosition, forwardPosition, ChessPiece.PieceType.ROOK));
                validMoves.add(new ChessMove(startPosition, forwardPosition, ChessPiece.PieceType.KNIGHT));
            }

            // check the space two-forward
            if (startPosition.getRow() == startRow) {
                ChessPosition doubleForwardPosition = new ChessPosition(x + i * 2, y);
                if (doubleForwardPosition.isValid() && board.getPiece(doubleForwardPosition) == null) {
                    validMoves.add(new ChessMove(startPosition, doubleForwardPosition, null));
                }
            }
        }

        // check the space forward-west
        ChessPosition westPosition = new ChessPosition(x + i, y + 1);
        if (westPosition.isValid() &&
                board.getPiece(westPosition) != null &&
                board.getPiece(westPosition).getTeamColor() != team) {
            if (westPosition.getRow() != endRow) { // cannot promote
                validMoves.add(new ChessMove(startPosition, westPosition, null));
            } else { // can promote
                validMoves.add(new ChessMove(startPosition, westPosition, ChessPiece.PieceType.QUEEN));
                validMoves.add(new ChessMove(startPosition, westPosition, ChessPiece.PieceType.BISHOP));
                validMoves.add(new ChessMove(startPosition, westPosition, ChessPiece.PieceType.ROOK));
                validMoves.add(new ChessMove(startPosition, westPosition, ChessPiece.PieceType.KNIGHT));
            }
        }

        // check the space forward-east
        ChessPosition eastPosition = new ChessPosition(x + i, y - 1);
        if (eastPosition.isValid() &&
                board.getPiece(eastPosition) != null &&
                board.getPiece(eastPosition).getTeamColor() != team) {
            if (eastPosition.getRow() != endRow) { // cannot promote
                validMoves.add(new ChessMove(startPosition, eastPosition, null));
            } else { // can promote
                validMoves.add(new ChessMove(startPosition, eastPosition, ChessPiece.PieceType.QUEEN));
                validMoves.add(new ChessMove(startPosition, eastPosition, ChessPiece.PieceType.BISHOP));
                validMoves.add(new ChessMove(startPosition, eastPosition, ChessPiece.PieceType.ROOK));
                validMoves.add(new ChessMove(startPosition, eastPosition, ChessPiece.PieceType.KNIGHT));
            }
        }

        return validMoves;
    }
}