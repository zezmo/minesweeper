import java.util.Random;

public class Board{
    // this class will handle game board set up and logic for selecting/flagging cells
    // determines the number of rows, columns, and mines. Used for difficulty setting or custom layouts.
    // easy            9x9      10 mines
    // intermediate    16x16    40 mines
    // expert          30x16    99 mines
    // add option to enter custom row, column, mines
    int gameColumns;
    int gameRows;
    int gameMines;
    Cell boardCells[][] ;

    public Board(int rows, int columns, int numMines) {
        this.gameRows = rows;
        this.gameColumns = columns;
        this.gameMines = numMines;

        setUpBoard();
        placeMines();
        scanForMines();
    }
    
    //1. create empty board
    public void setUpBoard() {
        this.boardCells = new Cell[gameRows][gameColumns];
        for(int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameColumns; j++) {
                this.boardCells[i][j] = new Cell();
            }
        }
    }

    //2. add mines to empty board
    public void placeMines() {
        int randomRow, randomColumn;
        Random random = new Random();
        int i = 0;

        while (i < gameMines) {
            randomRow = random.nextInt(gameRows);
            randomColumn = random.nextInt(gameColumns);

            if (!boardCells[randomRow][randomColumn].getMine()) {
                boardCells[randomRow][randomColumn].setMine(true);
                i++;
            }
        }
    }

    //3. set values for tiles
    public void scanForMines () {
        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameColumns; j++) {
                //skip the scan if current cell has a mine
                if( !boardCells[i][j].getMine() ) {
                    int count = 0;
                    count = validCellMine(i-1, j-1) + validCellMine(i-1, j) + validCellMine(i-1, j+1) + validCellMine(i, j-1) + validCellMine(i, j+1) + validCellMine(i+1, j+1) + validCellMine(i+1, j) + validCellMine(i+1, j-1);
                    boardCells[i][j].setNearbyMines(count);
                }
            }
        }
    }

    //helper for getting valid cells with nearby mines
    public int validCellMine(int x, int y) {
        if (x >= 0 && x < gameRows && y >= 0 && y < gameColumns) {
            if(boardCells[x][y].getMine()) {
                return 1;
            }
        }
        return 0;
    }

    public String whichIcon(int x, int y) {
        if (boardCells[x][y].getFlag()) {
            return "flag";
        }
        else if (boardCells[x][y].getMine()) {
            return "mine";
        } else {
            return Integer.toString(boardCells[x][y].getNearbyMines());
        }
    }
    
    public Cell[][] getBoardCells() {
        return boardCells;
    }
    public int getColumns() {return gameColumns;}
    public int getRows() {return gameRows;}
    public int getMines() {return gameMines;}

}


