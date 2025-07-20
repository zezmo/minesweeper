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
        setUpBoard();
        placeMines();
        scanForMines();
    }

    public void setDifficulty(String difficulty) {
        switch(difficulty) {
            case "easy":
                gameColumns = 9;
                gameRows = 9;
                gameMines = 10;
            case "intermediate":
                gameColumns = 9;
                gameRows = 9;
                gameMines = 10;
            case "expert":
                gameColumns = 9;
                gameRows = 9;
                gameMines = 10;
            default:
                System.out.println("Select difficulty");
        }
    }
    
    public int getColumns() {return gameColumns;}
    public int getRows() {return gameRows;}
    public int getMines() {return gameMines;}
    
    public int getBoardCellValue(int row, int column) {
        return board[column][row].getCellLabel();
    }

    private void setUpBoard() {
        board = new Cell[gameRows][gameColumns];
        for(int i = 0; i < gameColumns; i++) {
            for (int j = 0; j < gameRows; j++) {
                board[i][j] = new Cell();
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
        for (int i = 0; i < gameColumns; i++) {
            for (int j = 0; j < gameRows; j++) {
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
