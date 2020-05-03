package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private int turn;
    private boolean check;
    private boolean checkMate;
    private Color currentPlayer;
    private Board board;
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }
    public Color getCurrentPlayer() {
        return currentPlayer;
    }
    public ChessPiece[][] getPieces() {
        ChessPiece[][] boardMatrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                boardMatrix[i][j] = (ChessPiece) board.piece(i,j);
            }
        }
        return boardMatrix;
    }
    public boolean getCheck() {
        return check;
    }
    public boolean getCheckMate() {
        return checkMate;
    }

    public boolean[][] possibleMoves(ChessPosition originPosition) {
        Position position = originPosition.toPosition();
        validateOriginPosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition originPosition, ChessPosition targetPosition) {
        Position origin = originPosition.toPosition();
        Position target = targetPosition.toPosition();
        validateOriginPosition(origin);
        validateTargetPosition(origin, target);
        Piece capturedPiece = makeMove(origin, target);
        if (testCheck(currentPlayer)) {
            undoMove(origin, target, capturedPiece);
            throw new ChessException("You can't put yourself in check.");
        }
        check = (testCheck(isOpponent(currentPlayer))) ? true : false;

        if (testCheckMate(isOpponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }
    private Piece makeMove(Position origin, Position target) {
        Piece piece = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }
    private void undoMove(Position origin, Position target, Piece capturedPiece) {
        Piece piece = board.removePiece(target);
        board.placePiece(piece, origin);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void validateOriginPosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on origin position.");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours.");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for this piece.");
        }
    }
    private void validateTargetPosition(Position origin, Position target) {
        if (!board.piece(origin).possibleMove(target)){
            throw new ChessException("Chosen piece can't move to the target position.");
        }
    }

    private boolean testCheckMate(Color color) {
        if(!testCheck(color)) {
            return false;
        }
        List<Piece> pieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == color).collect(Collectors.toList());
        for (Piece piece : pieces) {
            boolean[][] possibleMovesToEndCheck = piece.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (possibleMovesToEndCheck[i][j]) {
                        Position origin = ((ChessPiece)piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(origin, target);
                        boolean testCheck = testCheck(color);
                        undoMove(origin, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean testCheck(Color color) {
        Position kingPosition = isKing(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == isOpponent(color)).collect(Collectors.toList());
        for (Piece piece: opponentPieces) {
            boolean[][] opponentPieceMoves = piece.possibleMoves();
            if (opponentPieceMoves[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }
    private Color isOpponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    private ChessPiece isKing(Color color) {
        List<Piece> pieces = piecesOnTheBoard.stream().filter(piece -> ((ChessPiece)piece).getColor() == color).collect(Collectors.toList());
        for (Piece piece: pieces) {
            if (piece instanceof King) {
                return (ChessPiece) piece;
            }
        }
        throw new IllegalStateException("There is no "+ color +" king on the board.");
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void initialSetup() {
        placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));

        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new King(board, Color.BLACK));
    }
}
