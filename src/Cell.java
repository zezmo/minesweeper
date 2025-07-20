//custom object to populate in game board

import java.awt.Button;

public class Cell extends Button{
    int cellMinesNearby;
    boolean cellMine;
    boolean cellFlag;
    boolean cellShow;

    public Cell() {
        //make new cell with default values
        this.cellFlag = false;
        this.cellShow = true; //true to test view
        this.cellMinesNearby = 0;
        this.cellMine = false;
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

    public void incrementMines() {
        this.cellMinesNearby++;
    }
    public int getCellLabel() {
        return this.cellMinesNearby;
    }
}
