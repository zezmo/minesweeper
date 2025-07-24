import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Window extends Frame implements WindowListener {
    private final String title = "Minesweeper";
    private final int width = 600, height = 400;
    private final int tileSize = 17;
    private final Dimension tileDimension = new Dimension(tileSize, tileSize);
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();

    //private Window gameWindow;
    private JPanel outerPanel;

    private MenuBar menuBar;
    private Menu gameMenu;
    private MenuItem newGame;

    private Label timerLabel;
    private Label newGameLabel;
    private Label bombsRemainingLabel;

    private JLabel[][] tiles;
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
        setIcons();
        setSize(width, height);
        this.rows = row;
        this.columns = column;
        this.mines = mine;
        tiles = new JLabel[rows][columns];

        JPanel topPanel;
        JPanel gamePanel;

        outerPanel = new JPanel();

        topPanel = new JPanel();
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, columns, 0, 0));
        gamePanel.setSize(rows*tileSize, columns*tileSize);
        outerPanel.add(topPanel);
        outerPanel.add(gamePanel);
        add(outerPanel);

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                tiles[i][j] = new JLabel("");
                //tiles[i][j].setIcon(new ImageIcon(getClass().getResource("/media/tile.png")));
                tiles[i][j].setName(Integer.toString(i) + "," + Integer.toString(j));
                tiles[i][j].setAlignmentX(JLabel.CENTER);
                tiles[i][j].setAlignmentY(JLabel.CENTER);
                tiles[i][j].setBorder(raisedbevel);
                tiles[i][j].setIcon(tileIcon);
                tiles[i][j].setPreferredSize(tileDimension);

                gamePanel.add(tiles[i][j]);
            }
        }
    }

    public void setTileListeners(Game game) {
        addWindowListener(game);

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                tiles[i][j].addMouseListener(game);

            }
        }
    }

    public Border getLoweredBorder() {
        return loweredbevel;
    }

    public void setIcons() {
        mineIcon = new ImageIcon(getClass().getResource("/media/mine.png"));
        tileIcon = new ImageIcon(getClass().getResource("/media/tile.png"));
        flagIcon = new ImageIcon(getClass().getResource("/media/flag.png"));
        one = new ImageIcon(getClass().getResource("/media/one.png"));
        two = new ImageIcon(getClass().getResource("/media/two.png"));
        three = new ImageIcon(getClass().getResource("/media/three.png"));
        four = new ImageIcon(getClass().getResource("/media/four.png"));
        five = new ImageIcon(getClass().getResource("/media/five.png"));
        six = new ImageIcon(getClass().getResource("/media/six.png"));
        seven = new ImageIcon(getClass().getResource("/media/seven.png"));
        eight = new ImageIcon(getClass().getResource("/media/eight.png"));
        zero = new ImageIcon(getClass().getResource("/media/zero.png"));
        question = new ImageIcon(getClass().getResource("/media/question.png"));
    }

    public Icon getMineIcon() {
        return mineIcon;
    }
    public Icon getTileIcon() {
        return tileIcon;
    }
    public Icon getOneIcon() {
        return one;
    }
    public Icon getTwoIcon() {
        return two;
    }
    public Icon getThreeIcon() {
        return three;
    }
    public Icon getFourIcon() {
        return four;
    }
    public Icon getFiveIcon() {
        return five;
    }
    public Icon getSixIcon() {
        return six;
    }
    public Icon getSevenIcon() {
        return seven;
    }
    public Icon getEightIcon() {
        return eight;
    }
    public Icon getZeroIcon() {
        return zero;
    }
    public Icon getQuestionIcon() {
        return question;
    }
    public Icon getFlagIcon() {
        return flagIcon;
    }

    public JLabel[][] getTiles() {return tiles;}

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