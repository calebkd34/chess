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
    private TeamColor teamTurn;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * Constructs a new ChessGame, copying an existing one instead
     * of using the default state
     *
     * @param game The game to build the copy of
     */
    public ChessGame(ChessGame game) {
        teamTurn = game.getTeamTurn();
        board = new ChessBoard(game.getBoard());
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
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

        // initialize variables
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece startPiece = board.getPiece(startPosition);

        // get possible moves from the piece
        if (startPiece != null) {
            possibleMoves = startPiece.pieceMoves(board, startPosition);

            // remove any moves that self-check
            possibleMoves.removeIf(move -> !testMoveLegality(move));
        }
        return possibleMoves;
    }

    /**
     * Checks to see if the move results in self-checking
     *
     * @param testMove the move to see if is valid
     * @return True if the move can be made, else false
     */
    public boolean testMoveLegality(ChessMove testMove) {

        // make sure the move is possible
        if (testMove != null && board.getPiece(testMove.getStartPosition()) != null) {

            TeamColor teamColor = board.getPiece(testMove.getStartPosition()).getTeamColor();
            if (MoveHelper.check(board, testMove.getEndPosition(), teamTurn)) {

                // test the testMove in the new copy
                ChessGame testGame = new ChessGame(this);
                testGame.makeUnsafeMove(testMove);
                ChessGameState simulatedState = new ChessGameState(testGame, teamColor);

                // if the test game is in check, then the testMove is not valid
                return !simulatedState.isInCheck();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Makes a legal move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        // check if the piece is there and is the right color and is on the board
        if (testMoveLegality(move) && board.getPiece(move.getStartPosition()).getTeamColor() == teamTurn) {

            // go through each of the allowed moves from the start position
            for (ChessMove testMove : validMoves(move.getStartPosition())) {
                if (move.equals(testMove)) {
                    makeUnsafeMove(move);
                    switch(teamTurn){ // make sure to change the turn
                        case WHITE -> setTeamTurn(TeamColor.BLACK);
                        case BLACK -> setTeamTurn(TeamColor.WHITE);
                    }
                    return;
                }
            }
        }
        throw new InvalidMoveException("Invalid move");
    }

    /**
     * Makes a move in the chess game without checking if it is valid
     *
     * @param move chess move to perform
     */
    public void makeUnsafeMove(ChessMove move) {

        // make the move
        if (move.getPromotionPiece() == null) {
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        } else {
            board.addPiece(move.getEndPosition(), new ChessPiece(teamTurn, move.getPromotionPiece()));
        }
        // remove the original piece
        board.addPiece(move.getStartPosition(), null);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return new ChessGameState(this, teamColor).isInCheck();
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return new ChessGameState(this, teamColor).isInCheckmate();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return new ChessGameState(this, teamColor).isInStalemate();
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
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
