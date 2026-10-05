package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessGameState {

    private ChessGame game;
    private final ChessPosition kingPosition;
    private final ChessGame.TeamColor teamColor;
    private final Collection<ChessMove> kingMoves;
    private final Collection<ChessMove> friendlyMoves;
    private final Collection<ChessMove> enemyMoves;

    public ChessGameState(ChessGame game, ChessGame.TeamColor teamColor) {
        // easy stuff
        this.game = game;
        this.teamColor = teamColor;

        // initialize variables
        ChessPosition kingPosition = null;
        ChessPosition testPosition;
        ChessPiece piece;
        ChessBoard board = game.getBoard();
        Collection<ChessMove> enemyMoves = new ArrayList<>();
        Collection<ChessMove> kingMoves = new ArrayList<>();
        Collection<ChessMove> friendlyMoves = new ArrayList<>();

        // compile all the moves
        int row; int col;
        for (row = 1; row <= 8; row++) {
            for (col = 1; col <= 8; col++) {

                // position information
                testPosition = new ChessPosition(row, col);
                piece = board.getPiece(testPosition);

                if (piece == null) {
                    continue;
                }

                // check for the friendly king
                if (piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = testPosition;
                    kingMoves = piece.pieceMoves(board, kingPosition);
                }

                // if an enemy piece, check its possible moves
                if (piece.getTeamColor() != teamColor) {
                    enemyMoves.addAll(piece.pieceMoves(board, testPosition));
                }

                // add friendly moves
                else if (piece.getPieceType() != ChessPiece.PieceType.KING) {
                    friendlyMoves.addAll(piece.pieceMoves(board, testPosition));
                }
            }
        }

        assert kingPosition != null: "no king found";
        this.kingPosition = kingPosition;
        this.enemyMoves = enemyMoves;
        this.friendlyMoves = friendlyMoves;
        this.kingMoves = kingMoves;
    }

    /**
     * Checks to see if the board state is in check
     *
     * @return true if in check
     */
    public boolean isInCheck() {
        for (ChessMove move : enemyMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if the board stat is in checkmate
     *
     * @return true if in checkmate
     */
    public boolean isInCheckmate() {
        return (!isInCheck() && isKingStuck() && !canBlock());
    }

    /**
     * Checks to see if the king has a legal move
     *
     * @return true if the king cannot move, regardless of the threat level
     */
    private boolean isKingStuck() {

        // look at each move and see if safe
        boolean kingCantMove;
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
                return false;
            }
        }
        return true;
    }

    private boolean canBlock() {
        // TODO: simulate each friendly move, if any are safe return true
    }
}
