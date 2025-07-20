import java.util.Random;

// model
public class Minesweeper {
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

    public Minesweeper() {
        //this.boardRows = rows;
        //this.boardColumns = columns;
        //this.boardMines = mines;
        setDifficulty("expert");
        setUpBoard();
        placeMines();
        scanForMines();
    }

    public void setDifficulty(String difficulty) {
        switch(difficulty) {
            case "easy":
                this.gameColumns = 9;
                this.gameRows = 9;
                this.gameMines = 10;
                break;
            case "intermediate":
                this.gameColumns = 16;
                this.gameRows = 16;
                this.gameMines = 40;
                break;
            case "expert":
                this.gameColumns = 16;
                this.gameRows = 30;
                this.gameMines = 99;
                break;
            default:
                System.out.println("Select difficulty");
                break;
        }
    }
    
    public int getColumns() {return  this.gameColumns;}
    public int getRows() {return this.gameRows;}
    public int getMines() {return this.gameMines;}

    public Cell[][] getBoardCells() {
        return this.board;
    }

    public int getBoardCellValue(int row, int column) {
        return this.board[row][column].getCellLabel();
    }

    private void setUpBoard() {
        this.board = new Cell[gameRows][gameColumns];
        for(int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameColumns; j++) {
                this.board[i][j] = new Cell();
            }
        }
    }

    private void placeMines() {
        int randomRow, randomColumn;
        Random random = new Random();

        for (int i = 0; i < gameMines; i++) {
            randomRow = random.nextInt(gameRows);
            randomColumn = random.nextInt(gameColumns);


            board[randomRow][randomColumn].placeMine();

        }
    }

    public int getMine(int x, int y) {
        if (x >= 0 && x < gameRows && y >= 0 && y < gameColumns) {
            if(board[x][y].cellMine) {
                return 1;
            }
        }
        return 0;
    }


    private void scanForMines () {
        /*
        *    ex:
        *        [*][1][0][0]
        *        [1][2][1][1]
        *        [0][0][0][*]
        *
        *    checking for cell x at [i][j]
        *         -1  0  1
        *       -1[ ][ ][ ]
        *        0[ ][x][ ]
        *        1[ ][ ][ ]
        *
        *    // first scanning method that came to mind might want to make this more efficient somehow
        *    // need to skip out of bounds for the array
        */ 
        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameColumns; j++) {
                //skip the scan if current cell has a mine
                if( !board[i][j].cellMine ) {
                    int count = 0;
                    count = getMine(i-1, j-1) + getMine(i-1, j) + getMine(i-1, j+1) + getMine(i, j-1) + getMine(i, j+1) + getMine(i+1, j+1) + getMine(i+1, j) + getMine(i+1, j-1);
                    board[i][j].setNearbyMines(count);
                }
            }
        }
    }
}
