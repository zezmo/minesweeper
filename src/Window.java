import java.awt.*;
import java.awt.event.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Window extends Frame implements WindowListener {
    private final String title = "Minesweeper";
    private int width, height;
    private Window gameWindow;
    private JPanel outerPanel;

    private MenuBar menuBar;
    private Menu gameMenu;
    private MenuItem newGame;

    private Label timerLabel;
    private Button newGameButton;
    private Label bombsRemainingLabel;

    private JButton[][] tiles;
    private int rows;
    private int columns;
    private int mines;

    private Icon mineIcon;
    private Icon tileIcon;
    private Icon flagIcon;
    private Icon one;
    private Icon two;
    private Icon three;
    private Icon four;
    private Icon five;
    private Icon six;
    private Icon seven;
    private Icon eight;
    private Icon zero;
    private Icon question;

    public Window(int row, int column, int mine) {
        this.rows = row;
        this.columns = column;
        this.mines = mine;
        tiles = new JButton[rows][columns];

        JPanel topPanel;
        JPanel gamePanel;

        outerPanel = new JPanel();
        topPanel = new JPanel();
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, columns, 0, 0));

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                tiles[i][j] = new JButton("");
                tiles[i][j].setIcon(new ImageIcon(getClass().getResource("/media/tile.png")));
                tiles[i][j].setName(Integer.toString(i) + "," + Integer.toString(j));

                gamePanel.add(tiles[i][j]);
            }
        }


        outerPanel.add(topPanel);
        outerPanel.add(gamePanel);

        add(outerPanel);

    }   

    public void setTileListeners(Game game) {
        addWindowListener(game);

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                tiles[i][j].addMouseListener(game);

            }
        }
    }

    public JButton[][] getTiles() {return tiles; }

    // window listener methods
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
}