package model;

import java.util.Random;

public class Board {
    //variables for start of game related to current mode/board size
    // easy            9x9      10 mines
    // intermediate    16x16    40 mines
    // expert          30x16    99 mines
    private int cols;
    private int rows;
    private int mines;
    private int cellsTarget;

    //variables for state of game
    private int flagsCount;
    private int elapsedTime;
    private Cell[][] boardCells;
    private GameState gameState;
    private boolean firstClick;

    public Board(BoardConfig config) {
        rows = config.getRows();
        cols = config.getCols();
        mines = config.getMines();
        cellsTarget = rows * cols - mines; // if cellsTarget == 0, win
        elapsedTime = 0;
        flagsCount = mines; //available flags to place = number of mines at the start
        gameState = GameState.READY;
        firstClick = true;
        setUpBoard();
    }

    //=============================================
    // Game initialization and mine placement/counts
    //=============================================

    // initialize new 2D array of cells
    public void setUpBoard() {
        boardCells = new Cell[rows][cols];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.boardCells[i][j] = new Cell();
            }
        }
    }

    public void placeMines(int safeRow, int safeColumn) {
        int randomRow, randomColumn;
        Random random = new Random();
        int i = 0;

        while (i < mines) {
            randomRow = random.nextInt(rows);
            randomColumn = random.nextInt(cols);

            if (validPlacement(safeRow, safeColumn, randomRow, randomColumn)) {
                if (!boardCells[randomRow][randomColumn].isMine()) {
                    boardCells[randomRow][randomColumn].setMine(true);
                    i++;
                }
            }
        }
    }

    // helper for placing mines not in the 8 cells around or on the first click
    private boolean validPlacement(int safeRow, int safeCol, int rRow, int rCol) {
        if (rRow >= 0 && rRow < rows && rCol >= 0 && rCol < cols) {
            if (Math.abs(rRow - safeRow) <= 1 && Math.abs(rCol - safeCol) <= 1) {
                return false;
            }
            return true;
        }
        return false;
    }

    // set values for Cell minesNearby
    public void scanForMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                //skip the scan if current cell has a mine
                if( !boardCells[i][j].isMine() ) {
                    int count = 0;
                    count = validCellMine(i-1, j-1) + validCellMine(i-1, j) + validCellMine(i-1, j+1) + validCellMine(i, j-1) + validCellMine(i, j+1) + validCellMine(i+1, j+1) + validCellMine(i+1, j) + validCellMine(i+1, j-1);
                    boardCells[i][j].setNearbyMines(count);
                }
            }
        }
    }
    //helper for getting valid cells with nearby mines
    public int validCellMine(int x, int y) {
        if (x >= 0 && x < rows && y >= 0 && y < cols) {
            if(boardCells[x][y].isMine()) {
                return 1;
            }
        }
        return 0;
    }


    //=============================================
    // Main functions
    //=============================================

    public void gameWinCheck() {
        if (gameState == GameState.PLAYING) {
            if (cellsTarget == 0) {
                gameState = GameState.WON;
            }
        }
    }
    public void gameLost() {
        if (gameState == GameState.PLAYING) {
            gameState = GameState.LOST;
        }
    }

    // show all of the mines at the end of the game
    public void showAllMines(int r, int c) {
        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                if((i==r && j==c) || !boardCells[i][j].isMine()) { 
                    continue;
                } else {
                boardCells[i][j].reveal();

                }
            }
        }
    }
    
    public void revealCell(int r, int c) {
        if (gameState != GameState.PLAYING && gameState != GameState.READY) return;
        if (firstClick) {
            placeMines(r, c);
            scanForMines();
            firstClick = false;
            gameState = GameState.PLAYING;
        }
        if (boardCells[r][c].isMine()) {
            boardCells[r][c].explode();
            gameLost();
            showAllMines(r, c);
        }
        if (boardCells[r][c].getMinesNearbyCount() == 0) {
            clearAdjacentZeros(r, c);
        }
        if (!boardCells[r][c].isShowing()) {
            boardCells[r][c].reveal();
            cellsTarget--;
        }
        gameWinCheck();
        if (gameState == GameState.WON) {
            showAllMines(r, c);
        }
    }

    public void clearAdjacentZeros(int r, int c) {
        if (r < 0 || c < 0 || r >= rows || c >= cols) {
            return;
        }
        if (boardCells[r][c].isMine() || boardCells[r][c].isShowing()) {
            return;
        }

        boardCells[r][c].reveal(); // update model that these are showing
        cellsTarget--;

        if (boardCells[r][c].getMinesNearbyCount() == 0) {
            for (int i= -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    } else {
                        clearAdjacentZeros(r + i, c + j);
                    }
                }
            }
        }
    }

    public void incFlagsCount() {
        flagsCount++;
    }
    public void decFlagsCount() {
        flagsCount--;
    }
    public void incElapsedTime() {
        if (gameState == GameState.PLAYING && elapsedTime < 999)
        elapsedTime++;
    }

    public void cycleFlag(int r, int c) {
        switch (boardCells[r][c].getFlagState()) {
            case FLAG:
                boardCells[r][c].setFlag(FlagState.QUESTION);
                incFlagsCount();
                break;
            case QUESTION:
                boardCells[r][c].setFlag(FlagState.NONE);
                break;
            default:
                if (flagsCount == 0) {
                    boardCells[r][c].setFlag(FlagState.QUESTION);
                    break;
                }
                boardCells[r][c].setFlag(FlagState.FLAG);
                decFlagsCount();
                break;
        }
    }

    //=============================================
    // Getters
    //=============================================

    public Cell[][] getBoardCells() {return boardCells;}
    public Cell getBoardCell(int r, int c) {
        return boardCells[r][c];
    }
    public int getColumns() {return cols;}
    public int getRows() {return rows;}
    public int getMines() {return mines;}
    public int getElapsedTime() {return elapsedTime;}
    public GameState getGameState() {return gameState;}
    public int getFlagsCount() {return flagsCount;}
}
