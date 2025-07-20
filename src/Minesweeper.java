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
        setDifficulty("easy");
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
                this.gameColumns = 9;
                this.gameRows = 9;
                this.gameMines = 10;
                break;
            case "expert":
                this.gameColumns = 9;
                this.gameRows = 9;
                this.gameMines = 10;
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
        return this.board[column][row].getCellLabel();
    }

    private void setUpBoard() {
        this.board = new Cell[gameRows][gameColumns];
        for(int i = 0; i < gameColumns; i++) {
            for (int j = 0; j < gameRows; j++) {
                this.board[i][j] = new Cell();
            }
        }
    }

    private void placeMines() {
        int randomColumn, randomRow;
        Random random = new Random();

        for (int i = 0; i < gameMines; i++) {
            randomColumn = random.nextInt(gameColumns);
            randomRow = random.nextInt(gameRows);

            board[randomColumn][randomRow].placeMine();

        }
    }

    public int getMine(int x, int y) {
        if (x >= 0 && x < gameColumns && y >= 0 && y < gameRows) {
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
        for (int i = 0; i < gameColumns; i++) {
            for (int j = 0; j < gameRows; j++) {
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
