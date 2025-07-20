public class GameController {
    private Minesweeper model;
    private Game view;

    //call when menu inputs for difficulty are selected
    public void selectDifficulty(String difficulty) {
        model.setDifficulty(difficulty);

    }

    public int getColumns() {
        return model.getColumns();
    }
    public int getRows() {
        return model.getRows();
    }
    public int getMines() {
        return model.getMines();
    }

    public void updateView() {
        
    }

    public int getCellLabel(int column, int row) {
        return model.getBoardCellValue(row, column);
    }

}

