import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Window extends JFrame implements WindowListener {
    private final String title = "Minesweeper";
    private final int width = 600, height = 400;
    private final int tileSize = 17;
    private final Dimension tileDimension = new Dimension(tileSize, tileSize);
    private SegmentDisplay minesSegmentDisplay[];
    private SegmentDisplay timerSegmentDisplay[];
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();

    //private Window gameWindow;
    private JPanel outerPanel;
    private JPanel gamePanel;
    private JPanel mainPanel;
    private JPanel headerPanel;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu newGame;
    private JMenuItem easy;
    private JMenuItem intermediate;
    private JMenuItem expert;

    private Thread timer;
    private boolean timerRunning;
    private int gameTime;

    private JLabel newGameLabel;
    
    private int minesRemaining;

    private JLabel[][] tiles;
    private int rows;
    private int columns;

    private Icon mineIcon;
    private Icon redMineIcon;
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIcons();
        setSize(width, height);
        setTitle(title);

        this.rows = row;
        this.columns = column;
        minesRemaining = mine;

        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        newGame = new JMenu("New Game");
        easy = new JMenuItem("easy");
        easy.setName("easy");
        intermediate = new JMenuItem("intermediate");
        intermediate.setName("intermediate");
        expert = new JMenuItem("expert");
        expert.setName("expert");

        newGame.add(easy);
        newGame.add(intermediate);
        newGame.add(expert);
        gameMenu.add(newGame);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);



        outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout());
        headerPanel = new JPanel();

        //==========================================
        // header with mines left and timer
        //==========================================
        newGameLabel = new JLabel("new game");

        timerSegmentDisplay = new SegmentDisplay[3];
        minesSegmentDisplay = new SegmentDisplay[2];
        
        for(int i=0; i<3; i++) {
            timerSegmentDisplay[i] = new SegmentDisplay();
            headerPanel.add(timerSegmentDisplay[i]);

        }
        headerPanel.add(newGameLabel);

        for(int i=0; i<2; i++) {
            minesSegmentDisplay[i] = new SegmentDisplay();
            headerPanel.add(minesSegmentDisplay[i]);
        }
        gameTime = 0;
        
        mainPanel = new JPanel();
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, columns, 0, 0));
        gamePanel.setSize(rows*tileSize, columns*tileSize);
        outerPanel.add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(gamePanel);
        outerPanel.add(headerPanel, BorderLayout.NORTH);
        add(outerPanel);

        // ==============================================================
        // game tiles
        //===============================================================
        
        tiles = new JLabel[rows][columns];

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                tiles[i][j] = new JLabel("");
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

    public void startTimer() {
        timerRunning = true;

        timer = new Thread() {
            @Override
            public void run() {
                while(timerRunning) {
                    gameTime++;
                    setTimerValue(gameTime);
                    try {
                        sleep(1000); // one second
                    }
                    catch(InterruptedException ex){}
                }
            }
        };

        timer.start();
    }

    public void setTimerValue(int t) {
        String s = String.format("%03d", t);
        for (int i=0; i<3; i++) {
            timerSegmentDisplay[i].writeNumber(Character.getNumericValue(s.charAt(i)));
            timerSegmentDisplay[i].repaint();
        }
    }

    public void setMinesValue(int m) {
        String s = String.format("%02d", m);
        for (int i=0; i<2; i++) {
            minesSegmentDisplay[i].writeNumber(Character.getNumericValue(s.charAt(i)));
            minesSegmentDisplay[i].repaint();
        }
    }


    public void minusMine() {
        minesRemaining--;
        System.out.print(minesRemaining);
        setMinesValue(minesRemaining);
    }
    public void plusMine() {
        minesRemaining++;
        setMinesValue(minesRemaining);
    }

    public void redrawTiles(int r, int c, int m) {
        gamePanel.removeAll();
        rows = r;
        columns = c;
        minesRemaining = m;

        System.out.println(rows);
        System.out.println(columns);

        
        gamePanel.setSize(rows*tileSize, columns*tileSize);
        gamePanel.setLayout(new GridLayout(rows, columns));
        tiles = new JLabel[rows][columns];

        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {

                tiles[i][j] = new JLabel("");
                tiles[i][j].setName(Integer.toString(i) + "," + Integer.toString(j));
                tiles[i][j].setAlignmentX(JLabel.CENTER);
                tiles[i][j].setAlignmentY(JLabel.CENTER);
                tiles[i][j].setBorder(raisedbevel);
                tiles[i][j].setIcon(tileIcon);
                tiles[i][j].setPreferredSize(tileDimension);
                gamePanel.add(tiles[i][j]);
            }
        }
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void setTileListeners(Game game) {
        for (int i=0; i < rows; i++) {
            for (int j=0; j < columns; j++) {
                tiles[i][j].addMouseListener(game);
            }
        }
    }

    public void setWindowAndMenuListeners(Game game) {
        addWindowListener(game);
        easy.addActionListener(game);
        intermediate.addActionListener(game);
        expert.addActionListener(game);
    }

    //==================================
    // set and get icons
    //==================================
    public Border getLoweredBorder() {
        return loweredbevel;
    }
    public void setIcons() {
        mineIcon = new ImageIcon(getClass().getResource("/media/mine.png"));
        redMineIcon = new ImageIcon(getClass().getResource("/media/redmine.png"));
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
    public Icon getRedMineIcon() {
        return redMineIcon;
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
    public JPanel getGamePanel() {
        return gamePanel;
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