package model;

public class Cell {
    private boolean isMine;
    private int minesNearby;
    private boolean show;
    private FlagState flagState;

    public Cell() {
        this.isMine = false;
        this.minesNearby = 0;
        this.show = false;
        this.flagState = FlagState.NONE;
    }

    // Getters
    //==========================================
    public boolean isMine() {
        return this.isMine;
    }
    public boolean isShowing() {
        return this.show;
    }
    public FlagState getFlagState() {
        return this.flagState;
    }
    public int getMinesNearbyCount() {
        return this.minesNearby;
    }

    // Setters
    //==========================================
    public void setMine(boolean b) {
        this.isMine = b;
    }
    public void setNearbyMines(int n) {
        this.minesNearby = n;
    }
    public void hide() {
        this.show = false;
    }
    public void reveal() {
        this.show = true;
    }

    public void setFlag(FlagState fs) {
        this.flagState = fs;
    }

    /*public void resetFlag() {
        this.flagState = FlagState.NONE;
    }*/

}
