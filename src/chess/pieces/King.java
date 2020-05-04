package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean testRookCastling(Position position) {
        ChessPiece piece = (ChessPiece)getBoard().piece(position);
        return ((piece != null) && (piece instanceof Rook) && (piece.getColor() == getColor()) && (piece.getMoveCount() == 0));
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] movesMatrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxiliaryPosition = new Position(0, 0);

        //up
        auxiliaryPosition.setValues((position.getRow() - 1), position.getColumn());
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //down
        auxiliaryPosition.setValues((position.getRow() + 1), position.getColumn());
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //left
        auxiliaryPosition.setValues(position.getRow(), (position.getColumn() - 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //right
        auxiliaryPosition.setValues(position.getRow(), (position.getColumn() + 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //up-left
        auxiliaryPosition.setValues((position.getRow() - 1), (position.getColumn() - 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //up-right
        auxiliaryPosition.setValues((position.getRow() - 1), (position.getColumn() + 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //down-left
        auxiliaryPosition.setValues((position.getRow() + 1), (position.getColumn() - 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //down-right
        auxiliaryPosition.setValues((position.getRow() + 1), (position.getColumn() + 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        //Castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            //Small castling
            Position rookKingRightSide = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(rookKingRightSide)) {
                Position kingRightFirstSpace = new Position(position.getRow(), position.getColumn() + 1);
                Position kingRightSecondSpace = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(kingRightFirstSpace) == null && getBoard().piece(kingRightSecondSpace) == null) {
                    movesMatrix[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            Position rookKingLeftSide = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(rookKingLeftSide)) {
                Position kingLeftFirstSpace = new Position(position.getRow(), position.getColumn() - 1);
                Position kingLeftSecondSpace = new Position(position.getRow(), position.getColumn() - 2);
                Position kingLeftThirdSpace = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(kingLeftFirstSpace) == null && getBoard().piece(kingLeftSecondSpace) == null && getBoard().piece(kingLeftThirdSpace) == null) {
                    movesMatrix[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return movesMatrix;
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return ((piece == null) || (piece.getColor() != getColor()));
    }
}
