package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessGameStatusCalculator {

    // TODO: make the checkMoveResult look at the move and return a literal describing
    //  the game state (WHITE_CHECKED, BLACK_CHECKMATED, etc.)
    // TODO: move some of these things into ChessMove?

    /**
     *  Enum identifying the six possible game states
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
        Collection<Collection<ChessMove>> enemyMoves = new ArrayList<>();
        Collection<ChessMove> kingMoves = new ArrayList<>();
        ChessPosition kingPosition = null;
        Boolean kingCanMove = false;
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
                if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = testPosition;
                    kingMoves = piece.pieceMoves(game.board, kingPosition);
                }

                // get the enemy piece information
                if (piece.getTeamColor() != game.getTeamTurn()){
                    enemyMoves.add(piece.pieceMoves(game.board, testPosition));
                }
            }
        }

        // check black moves
        for (ChessMove kingMove : kingMoves) {
            // TODO: look at each of the moves and if any are valid, set kingCanMove = true
            for (Collection<ChessMove> moves : enemyMoves) {
                for (ChessMove move : moves) {
                    if (move.getEndPosition().equals(kingPosition)) {
                        kingCanMove = true;
                        break;
                    }
                }
            }
        }
        // TODO: then, if the the king's current position is unsafe,
        //  use this & kingCanMove & game.TeamTurn to determine game state
    }
}
