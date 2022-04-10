import java.util.Arrays;

public class Player{
    private String name;
    private boolean[][] checkers;

    public Player() {
        name = "";
        checkers = new boolean[6][];

        for (int i = 0; i < 6; i++) {
            Arrays.fill(checkers[i] = new boolean[7], false);
        }
    }

    // Setters and getters
    public void setName(String n) {
        this.name = n;
    }

    public void setCheckers(int i, int j) {
        this.checkers[i][j] = true;
    }

    public String getName() {
        return name;
    }

    public boolean getCheckers(int i, int j) {
        return checkers[i][j];
    }
}
