public class Game {
    private Board model;
    private Window view;

    public Game (Window view, Board model) {
        this.model = new Board();
        this.view = new Window();
    }

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

    public int getCellLabel(int row, int column) {
        return model.getBoardCellValue(row, column);
    }

    public void setUpGame() {
        view.SetUpGame(model.getRows(), model.getColumns(), model.getMines(), model.getBoardCells());
    }
}
