package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessGameStateCalculator {

    // TODO: make the checkMoveResult look at the move and return a literal describing
    //  the game state (WHITE_CHECKED, BLACK_CHECKMATED, etc.)
    // TODO: move some of these things into ChessMove?

    /**
     * Enum identifying the six possible game states
     */
    public enum GameState {
        CHECK,
        CHECKMATE,
        STALEMATE,
        NORMAL
    }

    /**
     * Checks to see if the move results in check, checkmate, or stalemate and for who
     *
     * @param testMove the move to see if is valid
     * @return True if the move can be made, else false
     */
    public static GameState checkMoveResult(ChessGame game, ChessMove testMove) {

        // we will simulate a game with the testMove and return the result
        ChessGame testGame = new ChessGame(game);
        testGame.makeUnsafeMove(testMove);
        return getGameState(testGame, game.getTeamTurn(), true);
    }

    public static GameState getGameState(ChessGame game, ChessGame.TeamColor teamColor, boolean isMyTurn) {

        // initialize variables
        Collection<ChessMove> enemyMoves = new ArrayList<>();
        Collection<ChessMove> kingMoves = new ArrayList<>();
        ChessPosition kingPosition = null;
        boolean kingCantMove;
        boolean kingIsStuck;
        boolean kingIsThreatened;
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
                if (piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
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
        This part can be confusing. It looks at every enemy move and sees if threatens
        the king's possible moves. If even one piece is threatened then kingCantMove to the square.
        Then, if any possible kingMove is unthreatened, then the king is not stuck
         */
        kingIsStuck = true;
        for (ChessMove kingMove : kingMoves) {
            kingCantMove = false;
            for (ChessMove move : enemyMoves) {
                // check the king's surroundings
                if (move.getEndPosition().equals(kingMove.getEndPosition())) {
                    kingCantMove = true;
                    break;
                }
            }
            if (!kingCantMove) { // if any spot is open the king can move
                kingIsStuck = false;
            }
        }

        // check if the king is threatened in his current position
        kingIsThreatened = false;
        for (ChessMove move : enemyMoves) {
            if (move.getEndPosition().equals(kingPosition)) {

                // check if the move self-checks

                kingIsThreatened = true;
                break;
            }
        }

        // edge case: the king can't move because it is blocked by its own pieces
        System.out.println(kingMoves);
        if (kingMoves.isEmpty()) {
            kingIsStuck = false;
        }

        // logic for game state
        if (kingIsStuck) {
            if (kingIsThreatened) {
                return GameState.CHECKMATE;
            } else {
                return GameState.STALEMATE;
            }
        } else if (kingIsThreatened) {
            return GameState.CHECK;
        } else {
            return GameState.NORMAL;
        }
    }
}
