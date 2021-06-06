
package tetris;

public class Block implements RotatableGrid {

    private final char color;

    public Block(char color) {
        this.color = color;
    }

    @Override
    public int rows() {
        return 1;
    }

    @Override
    public int columns() {
        return 1;
    }

    @Override
    public char cellAt(int row, int col) {
        return color;
    }

    @Override
    public RotatableGrid rotateCW() {
        return this;
    }

    @Override
    public RotatableGrid rotateCCW() {
        return this;
    }
}
