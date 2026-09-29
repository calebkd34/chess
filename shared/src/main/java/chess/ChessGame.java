package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    public ChessBoard board;
    public TeamColor teamTurn;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    public ChessGame(ChessGame game) {
        teamTurn = game.teamTurn;
        board = game.board;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // checks if the move is valid
        if (move.getStartPosition().isValid() && move.getEndPosition().isValid()) {
            // TODO: I need to make a copy of the board and see if it has the current player in check or checkmate.
            // TODO: make sure the board copy constructor and game copy constructor is working
        } else throw new InvalidMoveException("Move is not valid.");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // I should go through each of the squares, check the valid moves of the opponent's piece,
        // then check to see if the current piece is visible

        // initialize variables
        ArrayList<Collection<ChessMove>> enemyMoves = new ArrayList<Collection<ChessMove>>();
        ChessPosition kingPosition = null;
        ChessPosition testPosition;
        ChessPiece piece;
        int i;
        int j;

        // get piece information
        for (i = 1; i < 9; i++); {
            for (j = 1; j < 9; j++); {
                // square information
                testPosition = new ChessPosition(i, j);
                piece = board.getPiece(testPosition);

                // check for the friendly king
                if (piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = testPosition;
                }

                // if an enemy piece, check its possible moves
                if (piece.getTeamColor() != teamColor) {
                    enemyMoves.add(piece.pieceMoves(board, testPosition));
                }
            }
        }
        assert kingPosition != null: "No king located";

        // check enemy possible moves
        for (Collection<ChessMove> moves : enemyMoves) {
            for (ChessMove move : moves) {
                if (move.getEndPosition() == kingPosition) return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {

    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }
}
