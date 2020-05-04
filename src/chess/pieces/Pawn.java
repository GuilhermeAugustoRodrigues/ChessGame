package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
    ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] movesMatrix = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position auxiliaryPosition = new Position(0, 0);

        if (getColor() == Color.WHITE) {
            auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
                if (getMoveCount() == 0) {
                    auxiliaryPosition.setValues(position.getRow() - 2, position.getColumn());
                    if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
                        movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
                    }
                }
            }
            auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
            auxiliaryPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }

            //En Passant
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    movesMatrix[left.getRow() - 1][left.getColumn()] = true;
                }
            }
            if (position.getRow() == 3) {
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                    movesMatrix[right.getRow() - 1][right.getColumn()] = true;
                }
            }
        } else {
            auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
                if (getMoveCount() == 0) {
                    auxiliaryPosition.setValues(position.getRow() + 2, position.getColumn());
                    if (getBoard().positionExists(auxiliaryPosition) && !getBoard().thereIsAPiece(auxiliaryPosition) && getMoveCount() == 0) {
                        movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
                    }
                }
            }
            auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }
            auxiliaryPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(auxiliaryPosition) && isThereOpponentPiece(auxiliaryPosition)) {
                movesMatrix[auxiliaryPosition.getRow()][auxiliaryPosition.getColumn()] = true;
            }

            //En Passant
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
                    movesMatrix[left.getRow() + 1][left.getColumn()] = true;
                }
            }
            if (position.getRow() == 4) {
                Position right = new Position(position.getRow(), position.getColumn() + 1);
                if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
                    movesMatrix[right.getRow() + 1][right.getColumn()] = true;
                }
            }
        }

        return movesMatrix;
    }

    @Override
    public String toString() {
        return "P";
    }
}
