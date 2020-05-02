package chess;

import boardGame.Position;

public class ChessPosition {
    private Character column;
    private int row;

    public ChessPosition(Character column, int row) {
        column = column.toString().toLowerCase().charAt(0);
        if ((column < 'a' || column > 'h') || (row < 1 || row > 8)) {
            throw new ChessException("Error instantiating chess position.\n" +
                                     "Valid values range from A1 to H8.");
        }
        this.column = column;
        this.row = row;
    }

    public Character getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }

    protected Position toPosition() {
        return new Position((8-row), (column - 'a'));
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition(((char)('a' - position.getColumn())), (8-position.getRow()));
    }

    @Override
    public String toString() {
        return ""+ column + row;
    }
}
