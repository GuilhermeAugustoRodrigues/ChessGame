package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] movesMatrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxiliaryPosition = new Position(0, 0);

        auxiliaryPosition.setValues((position.getRow() - 1), position.getColumn() - 2);
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        auxiliaryPosition.setValues((position.getRow() - 1), position.getColumn() + 2);
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        auxiliaryPosition.setValues(position.getRow() + 1, (position.getColumn() - 2));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        auxiliaryPosition.setValues(position.getRow() + 1, (position.getColumn() + 2));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        auxiliaryPosition.setValues((position.getRow() - 2), (position.getColumn() - 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        auxiliaryPosition.setValues((position.getRow() - 2), (position.getColumn() + 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        auxiliaryPosition.setValues((position.getRow() + 2), (position.getColumn() - 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        auxiliaryPosition.setValues((position.getRow() + 2), (position.getColumn() + 1));
        if (getBoard().positionExists(auxiliaryPosition) && canMove(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        return movesMatrix;
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return ((piece == null) || (piece.getColor() != getColor()));
    }
}
