import java.util.Arrays;

public class Game {
    private char[][] grid;
    private final int width = 7;
    private final int height = 6;

    // Constructor
    public Game() {
        grid = new char[height][];

        for (int i = 0; i < height; i++) {
            Arrays.fill(grid[i] = new char[width], '.');
        }
    }

    // Setters and getters
    public void setGrid(int i, int j, char c) {
        grid[i][j] = c;
    }

    public char getGrid(int i, int j) {
        return grid[i][j];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}