package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoveCalculator {

    public static Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition startPosition) {

        // get the rook information
        ChessPiece rook = board.getPiece(startPosition);

        // make sure it is a rook
        assert rook.getPieceType() == ChessPiece.PieceType.ROOK: "Not a rook";

        // get the team color
        ChessGame.TeamColor teamColor = rook.getTeamColor();

        // set up valid moves
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        // check to the north
        int i = startPosition.getRow();
        while (true) {
            i++;
            ChessPosition currentPosition = new ChessPosition(i, startPosition.getColumn());
            if (currentPosition.isValid()) {

                if (board.getPiece(currentPosition) == null) { // if the space is empty add it and keep going
                    validMoves.add(new ChessMove(startPosition, currentPosition, null));
                }

                else { // if enemy capture it, always stop
                    if (board.getPiece(currentPosition).getTeamColor() != teamColor) {
                        validMoves.add(new ChessMove(startPosition, currentPosition, null));
                    }
                    break;
                }

            }
            else break; // the space is off the board so stop
        }

        // check to the south
        i = startPosition.getRow();
        while (true) {
            i--;
            ChessPosition currentPosition = new ChessPosition(i, startPosition.getColumn());
            if (currentPosition.isValid()) {

                if (board.getPiece(currentPosition) == null) { // if the space is empty add it and keep going
                    validMoves.add(new ChessMove(startPosition, currentPosition, null));
                }

                else { // if enemy capture it, always stop
                    if (board.getPiece(currentPosition).getTeamColor() != teamColor) {
                        validMoves.add(new ChessMove(startPosition, currentPosition, null));
                    }
                    break;
                }

            }
            else break; // the space is off the board so stop
        }

        // check to the east
        i = startPosition.getColumn();
        while (true) {
            i++;
            ChessPosition currentPosition = new ChessPosition(startPosition.getRow(), i);
            if (currentPosition.isValid()) {

                if (board.getPiece(currentPosition) == null) { // if the space is empty add it and keep going
                    validMoves.add(new ChessMove(startPosition, currentPosition, null));
                }

                else { // if enemy capture it, always stop
                    if (board.getPiece(currentPosition).getTeamColor() != teamColor) {
                        validMoves.add(new ChessMove(startPosition, currentPosition, null));
                    }
                    break;
                }

            }
            else break; // the space is off the board so stop
        }

        // check to the west
        i = startPosition.getColumn();
        while (true) {
            i--;
            ChessPosition currentPosition = new ChessPosition(startPosition.getRow(), i);
            if (currentPosition.isValid()) {

                if (board.getPiece(currentPosition) == null) { // if the space is empty add it and keep going
                    validMoves.add(new ChessMove(startPosition, currentPosition, null));
                }

                else { // if enemy capture it, always stop
                    if (board.getPiece(currentPosition).getTeamColor() != teamColor){
                        validMoves.add(new ChessMove(startPosition, currentPosition, null));
                    }
                    break;
                }

            }
            else break; // the space is off the board so stop
        }

        return validMoves;
    }
}
