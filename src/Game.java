import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import javafx.stage.WindowEvent;

public class Game implements MouseListener, ActionListener, WindowListener{
    private Board board;
    private Window window;
    private boolean gameRunning;

    public Game (String difficulty) {
        newBoardSetUp(difficulty);
        this.window = new Window(board.getRows(), board.getColumns(), board.getMines());
        this.window.setTileListeners(this);
        this.gameRunning = false;
        window.setVisible(true);
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


    public void setLabelImage(int row, int column) {
        Cell cells[][] = board.getBoardCells();
        JLabel labels[][] = window.getTiles();
        String whichIcon = board.whichIcon(row, column);

        if (cells[row][column].getShow()) {
            return;
        }

        cells[row][column].setShow(true);
        labels[row][column].setBorder(window.getLoweredBorder());

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
        
        JLabel tiles[][] = window.getTiles();
        Cell cells[][] = board.getBoardCells();
        Border loweredbevel = window.getLoweredBorder();
        
        if (x < 0 || y < 0 || x >= board.getRows() || y >= board.getColumns()) {
            return;
        }
        if (cells[x][y].getMine() || cells[x][y].getShow()) {
            return;
        };

        setLabelImage(x, y);        

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
                if(nearbyMines == 0) {
                    clearAdjacentZeros(row, column);
                }
                tile.setBorder(window.getLoweredBorder());
                setLabelImage(row, column);
            }

            else if (SwingUtilities.isRightMouseButton(e)) {
                tile.setIcon(window.getFlagIcon());
            }

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
    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
