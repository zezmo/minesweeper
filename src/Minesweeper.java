import java.util.Random;

public class Minesweeper {
    // this class will handle game board set up and logic for selecting/flagging cells
    // determines the number of rows, columns, and mines. Used for difficulty setting or custom layouts.
    // easy            9x9      10 mines
    // intermediate    16x16    40 mines
    // expert          30x16    99 mines
    // add option to enter custom row, column, mines
    int boardColumns;
    int boardRows;
    int boardMines;
    Cell[][] board;

    public Minesweeper(int columns, int rows, int mines) {
        this.boardRows = rows;
        this.boardColumns = columns;

        setUpBoard();
        placeMines(mines);
        scanForMines();
    }

    private void setUpBoard() {
        board = new Cell[boardRows][boardColumns];
        for(int i = 0; i < boardColumns; i++) {
            for (int j = 0; j < boardRows; j++) {
                board[i][j] = new Cell(i, j);
            }
        }
    }

    private void placeMines(int mines) {
        int randomColumn, randomRow;
        Random random = new Random();

        for (int i = 0; i < boardMines; i++) {
            randomColumn = random.nextInt(boardColumns);
            randomRow = random.nextInt(boardRows);

            board[randomColumn][randomRow].placeMine();

        }
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
        */ 
        for (int i = 0; i < boardColumns; i++) {
            for (int j = 0; j < boardRows; j++) {
                //skip the scan if current cell has a mine
                if( !board[i][j].cellMine ) {
                    //check row above
                    if ( board[i-1][j-1].cellMine) {
                        board[i][j].cellMinesNearby++;
                    }
                    if ( board[i][j-1].cellMine) {
                        board[i][j].cellMinesNearby++;
                    }
                    if ( board[i+1][j-1].cellMine) {
                        board[i][j].cellMinesNearby++;
                    }
                    //check each side
                    if ( board[i-1][j].cellMine) {
                        board[i][j].cellMinesNearby++;
                    }
                    if ( board[i+1][j].cellMine) {
                        board[i][j].cellMinesNearby++;
                    }
                    //check row below
                    if ( board[i-1][j+1].cellMine) {
                        board[i][j].cellMinesNearby++;
                    }
                    if ( board[i][j+1].cellMine) {
                        board[i][j].cellMinesNearby++;
                    }
                    if ( board[i+1][j+1].cellMine) {
                        board[i][j].cellMinesNearby++;
                    }
                }
            }
        }
    }
}
