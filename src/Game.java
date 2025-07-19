import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.*;

// =========================
// reminder to set up layout all elements in one child element from parent element
//==========================

public class Game extends Frame implements WindowListener{    
    private Button[][] boardTile;
    private int gameColumns;
    private int gameRows;
    private int tileSize;
    private int gameMines;

    public Game () {
        gameColumns = 9;
        gameRows = 9;
        tileSize = 16;
        setLayout(new FlowLayout());

        //outer parent panel for the rest of the elements
        Panel outerPnl = new Panel();
        outerPnl.setLayout(new BorderLayout());
        outerPnl.setSize(900, 300);

        //top panel for timer, mine count, and new game
        Panel topPnl = new Panel();
        topPnl.setLayout(new GridLayout(1, 3));
        Label timerLabel = new Label("Timer");
        Label newLabel = new Label("new");
        Label minesLabel = new Label("mines");
        topPnl.add(timerLabel);
        topPnl.add(newLabel);
        topPnl.add(minesLabel);

        //game panel for cover tiles and board
        Panel gamePnl = new Panel();
        gamePnl.setSize(160, 160);
        gamePnl.setLayout(new GridLayout(9, 9));
        boardTile = new Button[gameRows][gameColumns];

        for (int i=0; i < gameColumns; i++) {
            for (int j=0; j < gameRows; j++) {
                boardTile[i][j] = new Button();
                boardTile[i][j].setPreferredSize(new Dimension(24, 24));
                gamePnl.add(boardTile[i][j]);
            }
        }

        // set up menu bar, sub menu for difficulty
        MenuBar menuBar = new MenuBar();
        setMenuBar(menuBar);
        Menu menu = new Menu("Game");
        menuBar.add(menu);
        MenuItem difficultyMenuItem = new MenuItem("Difficulty");
        menu.add(difficultyMenuItem);

        //add elements
        outerPnl.add(topPnl, BorderLayout.PAGE_START);
        outerPnl.add(gamePnl);

        add(outerPnl);
        addWindowListener(this);

        setTitle("Minesweeper");
        setSize(600, 600);

        setVisible(true);
    }

    //use this to listen to menu inputs later
    public void setDifficulty() {
        gameColumns = 9;
        gameRows = 9;
        gameMines = 10;
    }

    public void windowClosed(WindowEvent e) {
    }
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    public void windowOpened(WindowEvent e) {
    }
    public void windowIconified(WindowEvent e) {
    }
    public void windowDeiconified(WindowEvent e) {
    }
    public void windowActivated(WindowEvent e) {
    }
    public void windowDeactivated(WindowEvent e) {
    }

    public static void main(String[] args) {
        new Game();
    }
}
