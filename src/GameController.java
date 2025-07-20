public class GameController {
    private Minesweeper model;
    private Game view;

    public GameController (Game view, Minesweeper model) {
        this.model = new Minesweeper();
        this.view = new Game();
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

