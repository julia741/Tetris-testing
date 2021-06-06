
package tetris;

public class Piece implements Grid {

    private final char[][] blocks;

    public Piece(String shape) {
        this.blocks = Grid.parse(shape);
    }

    @Override
    public int rows() {
        return blocks.length;
    }

    @Override
    public int columns() {
        return blocks[0].length;
    }

    @Override
    public char cellAt(int row, int col) {
        return blocks[row][col];
    }

    @Override
    public String toString() {
        return Grid.toString(this);
    }
}
