import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game implements MouseListener, ActionListener, WindowListener{
    private Board board;
    private Window window;
    private boolean gameRunning;

    public Game (String difficulty) {
        newBoardSetUp(difficulty);
        this.window = new Window(board.getRows(), board.getColumns(), board.getMines());
        this.window.setTileListeners(this);
        this.window.setWindowAndMenuListeners(this);
        this.gameRunning = false;
        window.setVisible(true);
    }

    public void newGame(String difficulty) {
        this.gameRunning = false;
        newBoardSetUp(difficulty);
        switch(difficulty) {
            case "easy": 
                this.window.redrawTiles(9, 9);
                this.window.setTileListeners(this);
                break;
            case "intermediate": 
                this.window.redrawTiles(16, 16);
                this.window.setTileListeners(this);
                break;
            case "expert": 
                this.window.redrawTiles(16, 32);
                this.window.setTileListeners(this);
                break;
            default: 
                break;
        }
    }

    public void newBoardSetUp(String d) {
        switch(d) {
            case "easy": 
                this.board = new Board(9, 9, 10);
                break;
            case "intermediate": 
                this.board = new Board(16, 16, 40);
                break;
            case "expert": 
                this.board = new Board(16, 32, 99);
                break;
            default: 
                break;
        }
    }

    public void checkGame() {

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

        switch (whichIcon) {
            case "mine":
                labels[row][column].setIcon(window.getRedMineIcon());
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameRunning) {
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
                    setLabelImage(row, column);
                    //setLabelText(row, column);
                } else if(nearbyMines == 0) {
                    JPanel panel = window.getGamePanel();
                    panel.setIgnoreRepaint(true);
                    clearAdjacentZeros(row, column);
                    panel.setIgnoreRepaint(false);
                    panel.revalidate();
                    panel.repaint();

                } 
                else {
                    setLabelImage(row, column);
                    //setLabelText(row, column);

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
                    }
                    else {
                        board.getBoardCells()[row][column].setFlag("F");
                        tile.setIcon(window.getFlagIcon());
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
