//custom object to populate in game board

import java.awt.Button;

public class Cell extends Button{
    private boolean mine;
    private boolean flag;
    private int minesNearby;
    private String cellLabel;

    

    public Cell() {
        //make new cell with default values
        mine = false;
        flag = true; //true to test view
        minesNearby = 0;
        cellLabel = "";
    }

    

    public void placeMine() {
        this.cellMine = true;
        this.cellMinesNearby = -1;
    }

    public void showCell() {
        this.cellShow = true;
    }

    public void flagCell() {
        this.cellFlag = true;
    }

    public void setNearbyMines(int mines) {
        this.cellMinesNearby = mines;
    }
    public int getCellLabel() {
        return this.cellMinesNearby;
    }
}
