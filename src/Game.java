import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Game implements MouseListener, ActionListener, WindowListener{
    private int cellsRemaining;
    private int cellsTarget;
    private String currentDifficulty;
    private Board board;
    private Window window;
    private boolean gameRunning;
    private boolean gameComplete;

    public Game (String difficulty) {
        currentDifficulty = difficulty;
        newBoardSetUp(difficulty);
        gameComplete = false;
        this.window = new Window(board.getRows(), board.getColumns(), board.getMines());
        this.window.setTileListeners(this);
        this.window.setWindowAndMenuListeners(this);
        this.window.setMinesValue(board.getMines());
        this.gameRunning = false;
        window.setVisible(true);

    }

    public void newGame(String difficulty) {
        currentDifficulty = difficulty;
        gameComplete = false;
        this.gameRunning = false;
        newBoardSetUp(difficulty);
        this.window.resetTimer();
        switch(difficulty) {
            case "easy": 
                this.window.redrawTiles(9, 9, 10);
                this.window.setTileListeners(this);
                this.window.setMinesValue(10);
                break;
            case "intermediate": 
                this.window.redrawTiles(16, 16, 40);
                this.window.setTileListeners(this);
                this.window.setMinesValue(40);
                break;
            case "expert": 
                this.window.redrawTiles(16, 32, 99);
                this.window.setTileListeners(this);
                this.window.setMinesValue(99);
                break;
            default: 
                break;
        }
    }

    public void newBoardSetUp(String d) {
        switch(d) {
            case "easy": 
                this.board = new Board(9, 9, 10);
                cellsRemaining = 9*9;
                cellsTarget = 10;
                break;
            case "intermediate": 
                this.board = new Board(16, 16, 40);
                cellsRemaining = 16*16;
                cellsTarget = 40;
                break;
            case "expert": 
                this.board = new Board(16, 32, 99);
                cellsRemaining = 16*32;
                cellsTarget = 99;
                break;
            default: 
                break;
        }
    }
    
    public void checkGame() {
        if (cellsRemaining == cellsTarget) {
            gameWon();
        } else return;
    }

    public void gameWon() {
        gameComplete = true;
        gameRunning = false;
        window.stopTimer();
        showAllMines(-1, -1); //position does not matter here just want to show all mines
        winDialog();
    }

    public void winDialog() {

        JDialog winDialog = new JDialog(window, "Success!", true);
        
        JLabel winMessage = new JLabel("You've won the game! :)", SwingConstants.CENTER );

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 3, 15, 0));
        JButton playAgain = new JButton("Play Again");
        JButton exit = new JButton("Exit");
        JButton cancel = new JButton("Cancel");
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        playAgain.addActionListener((ActionEvent e) -> {
            winDialog.dispose();
            newGame(currentDifficulty);
        });
        cancel.addActionListener((ActionEvent e) -> {
            winDialog.dispose();
            windowClosing(null);
        });
        optionsPanel.add(playAgain);
        optionsPanel.add(exit);
        optionsPanel.add(cancel);

        JPanel winPanel = new JPanel();
        winPanel.setLayout(new BorderLayout(20, 20));
        winPanel.add(winMessage, BorderLayout.NORTH);
        winPanel.add(optionsPanel, BorderLayout.SOUTH);

        winPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        winDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        winDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                winDialog.dispose();
            }
        });

        winDialog.add(winPanel);
        winDialog.pack();
        winDialog.setLocationRelativeTo(window);
        winDialog.setVisible(true);
    }

    public void gameLost() {
        gameComplete = true;
        gameRunning = false;
        window.stopTimer();
        loseDialog();
    }

    public void loseDialog() {
        JDialog loseDialog = new JDialog(window, "Kaboom!", true);
        
        JLabel loseMessage = new JLabel("You've found a mine, whoops! :(", SwingConstants.CENTER );

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 3, 15, 0));
        JButton playAgain = new JButton("Play Again");
        JButton exit = new JButton("Exit");
        JButton cancel = new JButton("Cancel");
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        playAgain.addActionListener((ActionEvent e) -> {
            loseDialog.dispose();
            newGame(currentDifficulty);
        });
        cancel.addActionListener((ActionEvent e) -> {
            loseDialog.dispose();
            windowClosing(null);
        });
        optionsPanel.add(playAgain);
        optionsPanel.add(exit);
        optionsPanel.add(cancel);

        JPanel losePanel = new JPanel();
        losePanel.setLayout(new BorderLayout(20, 20));
        losePanel.add(loseMessage, BorderLayout.NORTH);
        losePanel.add(optionsPanel, BorderLayout.SOUTH);

        losePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        loseDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        loseDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                loseDialog.dispose();
            }
        });

        loseDialog.add(losePanel);
        loseDialog.pack();
        loseDialog.setLocationRelativeTo(window);
        loseDialog.setVisible(true);
    }

    public void setLabelImage(int row, int column) {
        Cell cells[][] = board.getBoardCells();
        if (cells[row][column].getShow()) {
            return;
        }
        JLabel labels[][] = window.getTiles();
        String whichIcon = board.whichIcon(row, column);

        cells[row][column].setShow(true);
        labels[row][column].setBorder(window.getLoweredBorder());
        cellsRemaining --;

        switch (whichIcon) {
            case "mine":
                labels[row][column].setIcon(window.getMineIcon());
                break;
            case "0":
                labels[row][column].setIcon(window.getZeroIcon());
                break;
            case "1":
                labels[row][column].setIcon(window.getOneIcon());
                break;
            case "2":
                labels[row][column].setIcon(window.getTwoIcon());
                break;
            case "3":
                labels[row][column].setIcon(window.getThreeIcon());
                break;
            case "4":
                labels[row][column].setIcon(window.getFourIcon());
                break;
            case "5":
                labels[row][column].setIcon(window.getFiveIcon());
                break;
            case "6":
                labels[row][column].setIcon(window.getSixIcon());
                break;
            case "7":
                labels[row][column].setIcon(window.getSevenIcon());
                break;
            case "8":
                labels[row][column].setIcon(window.getEightIcon());
                break;
            default:
                break;
        }

    } 

    //recursive funtion to clear out blank tiles when a zero is found
    public void clearAdjacentZeros(int x, int y) {
        Cell cells[][] = board.getBoardCells();
        if (x < 0 || y < 0 || x >= board.getRows() || y >= board.getColumns()) {
            return;
        }
        if (cells[x][y].getMine() || cells[x][y].getShow()) {
            return;
        };

        setLabelImage(x, y);        
        //setLabelText(x, y);

        if(cells[x][y].getNearbyMines() == 0) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    } else {
                        clearAdjacentZeros(x + i, y + j);
                    }
                    
                }
            }
        }
    }

    public void showAllMines(int x, int y) {
        Cell cells[][] = board.getBoardCells();
        //JLabel tiles[][] = window.getTiles();

        for (int i=0; i < board.getRows(); i++) {
            for (int j=0; j < board.getRows(); j++) {
                if(i==x && j==y) { 
                    continue;
                }
                if(cells[i][j].getMine()) {
                setLabelImage(i, j);

                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gameComplete){
            return;
        }
        if (!gameRunning) {
            window.startTimer();
            gameRunning = true;
            System.out.println("game is running!");
        }

        if (gameRunning) {
            JLabel tile = (JLabel)e.getSource();
            String[] coords = tile.getName().split(",");
            int row = Integer.parseInt(coords[0]);
            int column = Integer.parseInt(coords[1]);
            boolean checkIfMine = board.getBoardCells()[row][column].getMine();
            int nearbyMines = board.getBoardCells()[row][column].getNearbyMines();
            
            if (SwingUtilities.isLeftMouseButton(e)) {

                if(checkIfMine) {
                    JPanel panel = window.getGamePanel();
                    panel.setIgnoreRepaint(true);
                    window.getTiles()[row][column].setIcon(window.getRedMineIcon());
                    window.stopTimer();
                    showAllMines(row, column);
                    panel.setIgnoreRepaint(false);
                    panel.revalidate();
                    panel.repaint();
                    gameLost();

                } else if(nearbyMines == 0) {
                    JPanel panel = window.getGamePanel();
                    panel.setIgnoreRepaint(true);
                    clearAdjacentZeros(row, column);
                    panel.setIgnoreRepaint(false);
                    panel.revalidate();
                    panel.repaint();
                    checkGame();
                } 
                else {
                    setLabelImage(row, column);
                    checkGame();
                }
            }

            else if (SwingUtilities.isRightMouseButton(e)) {
                if (!board.getBoardCells()[row][column].getShow()) {
                    if(board.getBoardCells()[row][column].getFlag() == "Q") {
                        board.getBoardCells()[row][column].setFlag("");
                        tile.setIcon(window.getTileIcon());

                    } else if (board.getBoardCells()[row][column].getFlag() == "F") {
                        board.getBoardCells()[row][column].setFlag("Q");
                        tile.setIcon(window.getQuestionIcon());
                        window.plusMine();
                    }
                    else {
                        board.getBoardCells()[row][column].setFlag("F");
                        tile.setIcon(window.getFlagIcon());
                        window.minusMine();
                    }
                } 
            }

        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        switch (menuItem.getName()) {
            case "easy":
                newGame("easy");
                break;
            case "intermediate":
                newGame("intermediate");
                break;
            case "expert":
                newGame("expert");
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void windowOpened(java.awt.event.WindowEvent e) {
    }
    @Override
    public void windowClosing(java.awt.event.WindowEvent e) {
    }
    @Override
    public void windowClosed(java.awt.event.WindowEvent e) {
    }
    @Override
    public void windowIconified(java.awt.event.WindowEvent e) {
    }
    @Override
    public void windowDeiconified(java.awt.event.WindowEvent e) {
    }
    @Override
    public void windowActivated(java.awt.event.WindowEvent e) {
    }
    @Override
    public void windowDeactivated(java.awt.event.WindowEvent e) {
    }
    
}
