package model;

public class Cell {
    private boolean isMine;
    private int minesNearby;
    private boolean show;
    private boolean detonate;
    private FlagState flagState;

    public Cell() {
        this.isMine = false;
        this.minesNearby = 0;
        this.show = false;
        this.detonate = false;
        this.flagState = FlagState.NONE;
    }

    // Getters
    //==========================================
    public boolean isMine() {
        return this.isMine;
    }
    public boolean isDetonated() {
        return this.detonate;
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
    public void explode() {
        this.detonate = true;
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

}
