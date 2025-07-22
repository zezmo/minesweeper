import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import java.awt.*;
import java.awt.event.*;

public class Board extends Panel{
    private Board gameBoard;
    private static final int tileSize = 24;
    private int width, height;
    private Label[][] boardTiles;
    private GameWindow gameWindow;

    public Board(GameWindow g) {
        gameWindow = g;
        
    }

    public void setUpBoard (int rows, int columns, ) {
        removeAll();

        height = 900;
        width = 900;

        setSize(width, height);
        setLayout(new GridLayout(rows, columns));
        MouseHandler mouseListener = new MouseHandler();
        boardTiles = new Label[rows][columns];
        Dimension cellDimension = new Dimension(tileSize, tileSize);

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                Label label = new Label();
                label.setPreferredSize(cellDimension);
                label.setText();
        
            }
        }
    }

    public void setUpGame (int rows, int columns,  int mines, Cell[][] cell) {
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
        gamePnl.setSize((rows+1)*16, (columns+1) * 16);
        gamePnl.setLayout(new GridLayout(rows, columns));
        boardTile = new Button[rows][columns];

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                boardTile[i][j] = new Button();
                boardTile[i][j].setPreferredSize(new Dimension(tileSize, tileSize));
                boardTile[i][j].setLabel(String.valueOf(cell[i][j].cellMinesNearby));
                gamePnl.add(boardTile[i][j]);
            }
        }

        // set up menu bar, sub menu for difficulty
        MenuBar menuBar = new MenuBar();
        
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



}
