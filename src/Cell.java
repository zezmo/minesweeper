//custom object to populate in game board

import java.awt.Button;

public class Cell{
    private boolean mine;
    private String flag;
    private int minesNearby;
    private String cellLabel;
    private boolean show;


    public Cell() {
        //make new cell with default values
        mine = false;
        flag = "";
        show = false;
        minesNearby = 0;
        cellLabel = "";
    }


    public boolean getShow() {
        return show;
    }

    public void setShow(boolean s) {
        this.show = s;
    }
    public boolean getMine() {
        return mine;
    }

    public void setMine(boolean m) {
        this.mine = m;
    }

    public String getCellLabel() {
        return cellLabel;
    }

    public void setCellLabel(String l) {
        this.cellLabel = l;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String f) {
        this.flag = f;
    }

    public void setNearbyMines(int nearby) {
        this.minesNearby = nearby;
    }

    public int getNearbyMines() {
        return minesNearby;
    }

    
}
