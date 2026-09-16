package chess;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        if (type == PieceType.KNIGHT) return KnightMoveCalculator.calculateMoves(board, myPosition);

        if (type == PieceType.KING) return KingMoveCalculator.calculateMoves(board, myPosition);

        if (type == PieceType.ROOK) return RookMoveCalculator.calculateMoves(board, myPosition);

        if (type == PieceType.BISHOP) return BishopMoveCalculator.calculateMoves(board, myPosition);

        if (type == PieceType.QUEEN) return QueenMoveCalculator.calculateMoves(board, myPosition);

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        Map<ChessPiece, String> simplePieces = new HashMap<>();
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KING), "K");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.QUEEN), "Q");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT), "N");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP), "B");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK), "R");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.PAWN), "P");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KING), "k");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.QUEEN), "q");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT), "n");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP), "b");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK), "r");
        simplePieces.put(new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.PAWN), "p");
    return simplePieces.get(this);
    }
}
