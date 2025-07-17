public class Cell {
    int cellColumn;
    int cellRow;
    int cellMinesNearby;
    boolean cellMine;
    boolean cellFlag;
    boolean cellShow;


    public Cell(int row, int column) {
        cellColumn = column;
        cellRow = row;
    }

    public void placeMine() {
        cellMine = true;
        cellMinesNearby = -1;
    }


}
