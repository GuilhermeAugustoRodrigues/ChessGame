package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
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
        return movesMatrix;
    }

    private boolean canMove(Position position) {
        ChessPiece piece = (ChessPiece) getBoard().piece(position);
        return ((piece == null) || (piece.getColor() != getColor()));
    }

    @Override
    public String toString() {
        return "K";
    }
}
