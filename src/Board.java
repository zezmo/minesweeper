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
    Cell[][] board;

    public Board(int rows, int columns, int numMines) {
        this.gameRows = rows;
        this.gameColumns = columns;
        this.gameMines = numMines;

        setUpBoard();
        placeMines();
        scanForMines();
    }
    
    //1. create empty board
    private void setUpBoard() {
        this.board = new Cell[gameRows][gameColumns];
        for(int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameColumns; j++) {
                this.board[i][j] = new Cell();
            }
        }
    }

    //2. add mines to empty board
    private void placeMines() {
        int randomRow, randomColumn;
        Random random = new Random();

        for (int i = 0; i < gameMines; i++) {
            randomRow = random.nextInt(gameRows);
            randomColumn = random.nextInt(gameColumns);
            board[randomRow][randomColumn].setMine(true);
        }
    }

    //3. set values for tiles
    private void scanForMines () {
        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameColumns; j++) {
                //skip the scan if current cell has a mine
                if( !board[i][j].getMine() ) {
                    int count = 0;
                    count = validCellMine(i-1, j-1) + validCellMine(i-1, j) + validCellMine(i-1, j+1) + validCellMine(i, j-1) + validCellMine(i, j+1) + validCellMine(i+1, j+1) + validCellMine(i+1, j) + validCellMine(i+1, j-1);
                    board[i][j].setNearbyMines(count);
                }
            }
        }
    }

    //helper for getting valid cells with nearby mines
    public int validCellMine(int x, int y) {
        if (x >= 0 && x < gameRows && y >= 0 && y < gameColumns) {
            if(board[x][y].getMine()) {
                return 1;
            }
        }
        return 0;
    }

    public Cell[][] getBoard() {return board;}
    public int getColumns() {return gameColumns;}
    public int getRows() {return gameRows;}
    public int getMines() {return gameMines;}

}


