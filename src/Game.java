import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import javafx.stage.WindowEvent;

public class Game implements MouseListener, ActionListener, WindowListener{
    private Board board;
    private Window window;
    private boolean gameRunning;

    public Game (String difficulty) {
        newBoardSetUp(difficulty);
        this.window = new Window(board.getRows(), board.getColumns(), board.getMines());
        this.gameRunning = false;
        setButtonImage();
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

    public void setButtonImage() {
        Cell cells[][] = board.getBoardCells();
        JButton buttons[][] = window.getTiles();

        for (int i=0; i < board.getRows(); i++) {
            for (int j=0; j < board.getColumns(); j++) {
                buttons[i][j].setIcon(null);
                String whichIcon = board.whichIcon(i, j);

                if (cells[i][j].getMine()) {
                    buttons[i][j].setIcon(window.getMineIcon());
                } else {
                    switch (whichIcon) {
                        case "0":
                            buttons[i][j].setIcon(window.getZeroIcon());
                            break;
                        case "1":
                            buttons[i][j].setIcon(window.getOneIcon());
                            break;
                        case "2":
                            buttons[i][j].setIcon(window.getTwoIcon());
                            break;
                        case "3":
                            buttons[i][j].setIcon(window.getThreeIcon());
                            break;
                        case "4":
                            buttons[i][j].setIcon(window.getFourIcon());
                            break;
                        case "5":
                            buttons[i][j].setIcon(window.getFiveIcon());
                            break;
                        case "6":
                            buttons[i][j].setIcon(window.getSixIcon());
                            break;
                        case "7":
                            buttons[i][j].setIcon(window.getSevenIcon());
                            break;
                        case "8":
                            buttons[i][j].setIcon(window.getEightIcon());
                            break;
                        default: 
                            break;
                    }
                }

            }
        }
    }


    public void mouseClicked(MouseEvent e) {
        if (!gameRunning) {
            gameRunning = true;
        }

        if (gameRunning) {
            JButton tile = (JButton)e.getSource();

            String[] coords = tile.getName().split(",");

            int row = Integer.parseInt(coords[0]);
            int column = Integer.parseInt(coords[1]);

            boolean checkIfMine = board.getBoardCells()[row][column].getMine();
            int nearbyMines = board.getBoardCells()[row][column].getNearbyMines();

            if (SwingUtilities.isLeftMouseButton(e)) {

            }

            else if (SwingUtilities.isRightMouseButton(e)) {

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
