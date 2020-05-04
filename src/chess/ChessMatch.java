package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private int turn;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
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
    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
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

        //En Passant
        ChessPiece movedPiece = (ChessPiece)board.piece(target);
        if (movedPiece instanceof Pawn && ((target.getRow() == origin.getRow() + 2) || (target.getRow() == origin.getRow() - 2))) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
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
        ((ChessPiece)piece).increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        //Small Castling
        if (piece instanceof King && target.getColumn() == origin.getColumn() + 2) {
            Position rookOrigin = new Position(origin.getRow(), origin.getColumn() + 3);
            Position rookTarget = new Position(origin.getRow(), origin.getColumn() + 1);
            ChessPiece rook = ((ChessPiece)board.removePiece(rookOrigin));
            board.placePiece(rook, rookTarget);
            rook.increaseMoveCount();
        }
        //Big Castling
        if (piece instanceof King && target.getColumn() == origin.getColumn() - 2) {
            Position rookOrigin = new Position(origin.getRow(), origin.getColumn() - 4);
            Position rookTarget = new Position(origin.getRow(), origin.getColumn() - 1);
            ChessPiece rook = ((ChessPiece)board.removePiece(rookOrigin));
            board.placePiece(rook, rookTarget);
            rook.increaseMoveCount();
        }

        //En passant
        if (piece instanceof Pawn) {
            if (origin.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (((Pawn) piece).getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }
    private void undoMove(Position origin, Position target, Piece capturedPiece) {
        Piece piece = board.removePiece(target);
        ((ChessPiece)piece).decreaseMoveCount();
        board.placePiece(piece, origin);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }


        //Small Castling
        if (piece instanceof King && target.getColumn() == origin.getColumn() + 2) {
            Position rookOrigin = new Position(origin.getRow(), origin.getColumn() + 3);
            Position rookTarget = new Position(origin.getRow(), origin.getColumn() + 1);
            ChessPiece rook = ((ChessPiece)board.removePiece(rookTarget));
            board.placePiece(rook, rookOrigin);
            rook.increaseMoveCount();
        }
        //Big Castling
        if (piece instanceof King && target.getColumn() == origin.getColumn() - 2) {
            Position rookOrigin = new Position(origin.getRow(), origin.getColumn() - 4);
            Position rookTarget = new Position(origin.getRow(), origin.getColumn() - 1);
            ChessPiece rook = ((ChessPiece)board.removePiece(rookTarget));
            board.placePiece(rook, rookOrigin);
            rook.decreaseMoveCount();
        }

        //En passant
        if (piece instanceof Pawn) {
            if (origin.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece)board.removePiece(target);
                Position pawnPosition;
                if (((Pawn) piece).getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
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
        //White pieces
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        //placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        //placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        //placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        //placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        //placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));

        //Black pieces
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        //placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        //placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        //placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        //placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        //placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
    }
}
