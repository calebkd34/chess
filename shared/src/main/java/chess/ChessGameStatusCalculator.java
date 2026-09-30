package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessGameStatusCalculator {

    // TODO: make the checkMoveResult look at the move and return a literal describing
    //  the game state (WHITE_CHECKED, BLACK_CHECKMATED, etc.)
    // TODO: move some of these things into ChessMove?

    /**
     * Enum identifying the six possible game states
     */
    public enum GameState {
        BLACK_IN_CHECK,
        WHITE_IN_CHECK,
        BLACK_IN_CHECKMATE,
        WHITE_IN_CHECKMATE,
        STALEMATE,
        NORMAL
    }

    /**
     * Checks to see if the move results in check, checkmate, or stalemate and for who
     *
     * @param testMove the move to see if is valid
     * @return True if the move can be made, else false
     */
    public boolean checkMoveResult(ChessGame game, ChessMove testMove) {

        // make sure the move is possible
        if (testMove != null && game.board.getPiece(testMove.getStartPosition()) != null) {
            if (MoveHelper.check(game.board, testMove.getEndPosition(), game.teamTurn)) {

                // test the testMove in the new copy
                ChessGame testGame = new ChessGame(game);
                testGame.makeUnsafeMove(testMove);

                // if the test game is in check, then the testMove is not valid
                return !testGame.isInCheck(game.board.getPiece(testMove.getStartPosition()).getTeamColor());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public GameState getGameState(ChessGame game) {

        // initialize variables
        Collection<ChessMove> enemyMoves = new ArrayList<>();
        Collection<ChessMove> kingMoves = new ArrayList<>();
        ChessPosition kingPosition = null;
        boolean kingCantMove = false;
        boolean kingIsStuck = true;
        boolean kingIsThreatened = false;
        ChessPosition testPosition;
        ChessPiece piece;
        int row;
        int col;

        // first get the location of the kings
        for (row = 1; row <= 8; row++) {
            for (col = 1; col <= 8; col++) {

                // position information
                testPosition = new ChessPosition(row, col);
                piece = game.board.getPiece(testPosition);

                if (piece == null) {
                    continue;
                }

                // get the friendly king information
                if (piece.getTeamColor() == game.getTeamTurn() && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = testPosition;
                    kingMoves = piece.pieceMoves(game.board, kingPosition);
                }

                // get the enemy piece information
                if (piece.getTeamColor() != game.getTeamTurn()) {
                    enemyMoves.addAll(piece.pieceMoves(game.board, testPosition));
                }
            }
        }

        /*
        This part can be confusing.
         */
        for (ChessMove kingMove : kingMoves) {
            for (ChessMove move : enemyMoves) {
                if (move.getEndPosition().equals(kingMove.getEndPosition())) {
                    kingCantMove = true;
                    break;
                }
            }
            if (!kingCantMove) {
                kingIsStuck = false;
            }
        }

        // check if the king is threatened in his current position
        for (ChessMove move : enemyMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                kingIsThreatened = true;
                break;
            }
        }

        // logic for what game state is here
        if (game.teamTurn == ChessGame.TeamColor.BLACK) {
            if (kingIsStuck && kingIsThreatened) {
                return GameState.BLACK_IN_CHECKMATE;
            } else if (kingIsStuck) {
                return GameState.STALEMATE;
            } else {
                return GameState.NORMAL;
            }
        } else {
            if (kingIsStuck && kingIsThreatened) {
                return GameState.WHITE_IN_CHECKMATE;
            } else if (kingIsStuck) {
                return GameState.STALEMATE;
            } else {
                return GameState.NORMAL;
            }
        }
    }
}
