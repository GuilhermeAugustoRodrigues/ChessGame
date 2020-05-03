package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] movesMatrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxiliaryPosition = new Position(0, 0);

        if (getColor() == Color.WHITE) {
            auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
            auxiliaryPosition.setValues(position.getRow() - 2, position.getColumn());
            if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition) && getMoveCount() == 0) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
            auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
            auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
        } else {
            auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
            auxiliaryPosition.setValues(position.getRow() + 2, position.getColumn());
            if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition) && getMoveCount() == 0) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
            auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
            auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
        }

        return movesMatrix;
    }

    @Override
    public String toString() {
        return "P";
    }
}
