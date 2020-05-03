package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] movesMatrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxiliaryPosition = new Position(0, 0);
        //up-left
        auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn()-1);
        while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(auxiliaryPosition.getRow() - 1, auxiliaryPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //up-right
        auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(auxiliaryPosition.getRow() - 1, auxiliaryPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //down-left
        auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(auxiliaryPosition.getRow() + 1, auxiliaryPosition.getColumn() - 1);
        }
        if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }
        //down-left
        auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            auxiliaryPosition.setValues(auxiliaryPosition.getRow() + 1, auxiliaryPosition.getColumn() + 1);
        }
        if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
            movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
        }

        return movesMatrix;
    }
}
