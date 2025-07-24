//custom object to populate in game board

import java.awt.Button;

public class Cell extends Button{
    private boolean mine;
    private boolean flag;
    private int minesNearby;
    private String cellLabel;
    private boolean show;

    

    public Cell() {
        //make new cell with default values
        mine = false;
        flag = false;
        show = false;
        minesNearby = 0;
        cellLabel = "";
    }


    public boolean getShow() {
        return mine;
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

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean f) {
        this.flag = f;
    }

    public void setNearbyMines(int nearby) {
        this.minesNearby = nearby;
    }

    public int getNearbyMines() {
        return minesNearby;
    }

    
}
