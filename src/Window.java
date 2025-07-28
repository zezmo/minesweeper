import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class Window extends JFrame implements WindowListener {
    private final String title = "Minesweeper";
    private int width, height;
    private final int tileSize = 23;
    private final Dimension tileDimension = new Dimension(tileSize, tileSize);
    private final Dimension buttonDimension = new Dimension(42,42);
    private final Dimension segmentsDimension = new Dimension(97, 55);
    private SegmentDisplay minesSegment[];
    private SegmentDisplay timerSegment[];
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();

    //private Window gameWindow;
    private JPanel outerPanel;
    private JPanel gamePanel; //game board
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel buttonPanel;
    private JPanel timerDisplay;
    private JPanel minesDisplay;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu newGame;
    private JMenuItem easy;
    private JMenuItem intermediate;
    private JMenuItem expert;

    private Timer timer;
    private boolean timerRunning;
    private int gameTime;

    private JButton newGameButton;
    
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
    private Icon newGameIcon;

    public Window(int row, int column, int mine) {
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon windowIcon = new ImageIcon(getClass().getResource("/media/icon-minesweeper.png"));
        setIconImage(windowIcon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIcons();
        setSize(width, height);
        setTitle(title);

        this.rows = row;
        this.columns = column;
        minesRemaining = mine;

        width = columns*tileSize +20;
        height = rows*tileSize + 80;

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

        //==========================================
        // header with mines left and timer
        //==========================================
        JPanel leftWrapper = new JPanel(new GridBagLayout());
        JPanel rightWrapper = new JPanel(new GridBagLayout());
        JPanel centerWrapper = new JPanel(new GridBagLayout());

        headerPanel = new JPanel();
        headerPanel.setSize(900, 63);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(400, 80));
        headerPanel.setBorder(loweredbevel);
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.setOpaque(true);
        
        buttonPanel = new JPanel();
        timerDisplay = new JPanel();
        timerDisplay.setBorder(loweredbevel);
        timerDisplay.setLayout(new FlowLayout(0, 0, 0));
        timerDisplay.setPreferredSize(segmentsDimension);

        minesDisplay = new JPanel();
        minesDisplay.setLayout(new FlowLayout(0, 0, 0));
        minesDisplay.setBorder(loweredbevel);
        minesDisplay.setPreferredSize(segmentsDimension);

        newGameButton = new JButton("");
        newGameButton.setIcon(newGameIcon);
        newGameButton.setPreferredSize(buttonDimension);
        newGameButton.setBorder(raisedbevel);
        newGameButton.setName("newGame");
        buttonPanel.add(newGameButton);
        buttonPanel.setOpaque(false);

        leftWrapper.add(minesDisplay);
        leftWrapper.setOpaque(false);
        rightWrapper.add(timerDisplay);
        rightWrapper.setOpaque(false);
        centerWrapper.add(buttonPanel);
        centerWrapper.setOpaque(false);

        headerPanel.add(leftWrapper, BorderLayout.WEST);
        headerPanel.add(centerWrapper, BorderLayout.CENTER);
        headerPanel.add(rightWrapper, BorderLayout.EAST);

        timerSegment = new SegmentDisplay[3];
        minesSegment = new SegmentDisplay[3];
        
        for(int i=0; i<3; i++) {
            timerSegment[i] = new SegmentDisplay();
            timerDisplay.add(timerSegment[i]);

        }

        for(int i=0; i<3; i++) {
            minesSegment[i] = new SegmentDisplay();
            minesDisplay.add(minesSegment[i]);
        }
        gameTime = 0;
        
        mainPanel = new JPanel();
        mainPanel.setBorder(loweredbevel);
        mainPanel.setBackground(Color.GRAY);
        mainPanel.setOpaque(true);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, columns, 0, 0));
        gamePanel.setBorder(loweredbevel);
        gamePanel.setSize(rows*tileSize, columns*tileSize);
        outerPanel.add(mainPanel, BorderLayout.CENTER);
        outerPanel.setBorder(raisedbevel);
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
                //tiles[i][j].setBorder(raisedbevel);
                tiles[i][j].setIcon(tileIcon);
                tiles[i][j].setPreferredSize(tileDimension);

                gamePanel.add(tiles[i][j]);
            }
        }

        pack();
    }

    public void startTimer() {
        timerRunning = true;

        timer = new Timer(1000, e -> {
            gameTime++;
            setTimerValue(gameTime);
        });

        timer.start();
    }

    public void stopTimer() {
        timerRunning = false;
        if(timer != null) {
            timer.stop();
        }
    }

    public boolean getTimerRunning() {
        return timerRunning;
    }


    public void resetTimer() {
        gameTime = 0;
        setTimerValue(gameTime);
    }

    public void setTimerValue(int t) {
        String s = String.format("%03d", t);
        for (int i=0; i<3; i++) {
            timerSegment[i].writeNumber(Character.getNumericValue(s.charAt(i)));
            timerSegment[i].repaint();
        }
    }

    public void setMinesValue(int m) {
        String s = String.format("%03d", m);
        for (int i=0; i<3; i++) {
            minesSegment[i].writeNumber(Character.getNumericValue(s.charAt(i)));
            minesSegment[i].repaint();
        }
    }

    public void minusMine() {
        minesRemaining--;
        //System.out.print(minesRemaining);
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

        //System.out.println(rows);
        //System.out.println(columns);

        
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
        pack();
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
        newGameButton.addActionListener(game);
    }

    //==================================
    // set and get icons
    //==================================
    private ImageIcon resizeIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public Border getLoweredBorder() {
        return loweredbevel;
    }
    public void setIcons() {
        mineIcon = resizeIcon( new ImageIcon(getClass().getResource("/media/mine.png")), tileSize, tileSize);
        redMineIcon = resizeIcon(new ImageIcon(getClass().getResource("/media/redmine.png")), tileSize, tileSize);
        tileIcon = resizeIcon(new ImageIcon(getClass().getResource("/media/tile.png")), tileSize, tileSize);
        flagIcon = resizeIcon(new ImageIcon(getClass().getResource("/media/flag.png")), tileSize, tileSize);
        one = resizeIcon(new ImageIcon(getClass().getResource("/media/one.png")), tileSize, tileSize);
        two = resizeIcon(new ImageIcon(getClass().getResource("/media/two.png")), tileSize, tileSize);
        three = resizeIcon(new ImageIcon(getClass().getResource("/media/three.png")), tileSize, tileSize);
        four = resizeIcon(new ImageIcon(getClass().getResource("/media/four.png")), tileSize, tileSize);
        five = resizeIcon(new ImageIcon(getClass().getResource("/media/five.png")), tileSize, tileSize);
        six = resizeIcon(new ImageIcon(getClass().getResource("/media/six.png")), tileSize, tileSize);
        seven = resizeIcon(new ImageIcon(getClass().getResource("/media/seven.png")), tileSize, tileSize);
        eight = resizeIcon(new ImageIcon(getClass().getResource("/media/eight.png")), tileSize, tileSize);
        zero = resizeIcon(new ImageIcon(getClass().getResource("/media/zero.png")), tileSize, tileSize);
        question = resizeIcon(new ImageIcon(getClass().getResource("/media/question.png")), tileSize, tileSize);
        newGameIcon = resizeIcon(new ImageIcon(getClass().getResource("/media/button.png")),40, 40);
    }
    public Icon getNewGameIcon() {
        return newGameIcon;
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
    public JButton getNewgameButton() {
        return newGameButton;
    }
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