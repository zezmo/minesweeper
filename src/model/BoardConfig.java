package model;

public class BoardConfig {
    private int rows;
    private int cols;
    private int mines;

    public BoardConfig(int r, int c, int m) {
        rows = r;
        cols = c;
        mines = m;
    }

    public static BoardConfig fromDifficulty(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                return new BoardConfig(9, 9, 10);
            case INTERMEDIATE:
                return new BoardConfig(16, 16, 40);
            case EXPERT:
                return new BoardConfig(16, 30, 99);
            default:
                return new BoardConfig(9, 9, 10);
        }
    }

    //==========================
    // Getters
    //==========================
    public int getRows() {return rows;}
    public int getCols() {return cols;}
    public int getMines() {return mines;}
}
