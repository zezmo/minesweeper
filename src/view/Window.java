package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Window extends JFrame{
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

    public enum TileFace {
        COVERED,
        FLAG,
        QUESTION,
        MINE,
        RED_MINE,
        NUM0, NUM1, NUM2, NUM3, NUM4, NUM5, NUM6, NUM7, NUM8
    }

    public enum EndChoice { PLAY_AGAIN, EXIT, CANCEL };
    private final String title = "Minesweeper";
    private int width, height;
    private final int tileSize = 23;
    private final Dimension tileDimension = new Dimension(tileSize, tileSize);
    private final Dimension buttonDimension = new Dimension(42,42);
    private final Dimension segmentsDimension = new Dimension(97, 55);
    private SegmentDisplay flagsRemainingSegment[];
    private SegmentDisplay timerSegment[];
    Border raisedbevel = BorderFactory.createRaisedBevelBorder();
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    Border tileBorder = BorderFactory.createLineBorder(new Color(111, 111, 111));

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

    private JButton newGameButton;

    private JLabel[][] tiles;
    private int rows;
    private int cols;
    
    public Window(int r, int c) {
        setResizable(false);
        setLocationRelativeTo(null);
        ImageIcon windowIcon = new ImageIcon(getClass().getResource("/media/icon-minesweeper.png"));
        setIconImage(windowIcon.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIcons();
        setTitle(title);

        this.rows = r;
        this.cols = c;

        width = cols*tileSize +20;
        height = rows*tileSize + 80;

        setSize(width, height);

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
        flagsRemainingSegment = new SegmentDisplay[3];
        
        for(int i=0; i<3; i++) {
            timerSegment[i] = new SegmentDisplay();
            timerDisplay.add(timerSegment[i]);

        }

        for(int i=0; i<3; i++) {
            flagsRemainingSegment[i] = new SegmentDisplay();
            minesDisplay.add(flagsRemainingSegment[i]);
        }
        
        mainPanel = new JPanel();
        mainPanel.setBorder(loweredbevel);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setOpaque(true);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, cols, 0, 0));
        gamePanel.setBorder(loweredbevel);
        gamePanel.setSize(rows*tileSize, cols*tileSize);
        outerPanel.add(mainPanel, BorderLayout.CENTER);
        outerPanel.setBorder(raisedbevel);
        mainPanel.add(gamePanel);
        outerPanel.add(headerPanel, BorderLayout.NORTH);
        add(outerPanel);

        // ==============================================================
        // game tiles
        //===============================================================
        
        tiles = new JLabel[rows][cols];

        for (int i=0; i < rows; i++) {
            for (int j=0; j < cols; j++) {
                tiles[i][j] = new JLabel("");
                tiles[i][j].setName(Integer.toString(i) + "," + Integer.toString(j));
                tiles[i][j].setAlignmentX(JLabel.CENTER);
                tiles[i][j].setAlignmentY(JLabel.CENTER);
                tiles[i][j].setBorder(tileBorder);
                tiles[i][j].setIcon(tileIcon);
                tiles[i][j].setPreferredSize(tileDimension);

                gamePanel.add(tiles[i][j]);
            }
        }

        pack();
    }

    //====================================================
    // redraw functions
    //====================================================

    public void rebuildBoard(int newRows, int newCols) {
        this.rows = newRows;
        this.cols = newCols;

        gamePanel.removeAll();
        gamePanel.setLayout(new GridLayout(rows, cols, 0, 0));

        tiles = new JLabel[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tiles[i][j] = new JLabel("");
                tiles[i][j].setName(Integer.toString(i) + "," + Integer.toString(j));
                tiles[i][j].setAlignmentX(JLabel.CENTER);
                tiles[i][j].setAlignmentY(JLabel.CENTER);
                tiles[i][j].setBorder(tileBorder);
                tiles[i][j].setIcon(tileIcon);
                tiles[i][j].setPreferredSize(tileDimension);

                gamePanel.add(tiles[i][j]);
            }
        }

        pack();

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    //====================================================
    // segment display functions
    //====================================================

    public void setTimerValue(int t) {
        String s = String.format("%03d", t);
        for (int i=0; i<3; i++) {
            timerSegment[i].writeNumber(Character.getNumericValue(s.charAt(i)));
            timerSegment[i].repaint();
        }
    }

    public void setFlagsRemainingValue(int f) {
        String s = String.format("%03d", f);
        for (int i=0; i < 3; i++) {
            flagsRemainingSegment[i].writeNumber(Character.getNumericValue(s.charAt(i)));
            flagsRemainingSegment[i].repaint();
        }
    }

    //====================================================
    // dialog functions
    //====================================================

    public EndChoice winDialog(boolean won) {
        JDialog dialog;
        JLabel message;
        EndChoice[] choice = {EndChoice.CANCEL};

        if (won) {
            dialog = new JDialog(this, "Success!", true);
            message = new JLabel("You've won the game! :)", SwingConstants.CENTER );
        } else {
            dialog = new JDialog(this, "Kaboom!", true);
            message = new JLabel("You've found a mine, whoops! :(", SwingConstants.CENTER );
        }
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 3, 15, 0));
        JButton playAgain = new JButton("Play Again");
        JButton exit = new JButton("Exit");
        JButton cancel = new JButton("Cancel");
        
        exit.addActionListener(e -> {
            choice[0] = EndChoice.EXIT;
            dialog.dispose();
        });
        playAgain.addActionListener(e -> {
            choice[0] = EndChoice.PLAY_AGAIN;
            dialog.dispose();
        });
        cancel.addActionListener(e -> {
            choice[0] = EndChoice.CANCEL;
            dialog.dispose();
        });

        optionsPanel.add(playAgain);
        optionsPanel.add(exit);
        optionsPanel.add(cancel);

        JPanel winPanel = new JPanel();
        winPanel.setLayout(new BorderLayout(20, 20));
        winPanel.add(message, BorderLayout.NORTH);
        winPanel.add(optionsPanel, BorderLayout.SOUTH);
        winPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                choice[0] = EndChoice.CANCEL;
                dialog.dispose();
            }
        });

        dialog.add(winPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return choice[0];
    }


    //====================================================
    // listener functions
    //====================================================

    public void newGameButton(java.awt.event.ActionListener a) {
        newGameButton.addActionListener(a);
    }

    public void selectEasy(java.awt.event.ActionListener a) {
        easy.addActionListener(a);
    }

    public void selectIntermediate(java.awt.event.ActionListener a) {
        intermediate.addActionListener(a);
    }

    public void selectExpert(java.awt.event.ActionListener a) {
        expert.addActionListener(a);
    }

    public void tileClick(java.awt.event.MouseListener a) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                tiles[i][j].addMouseListener(a);
            }
        }
    }

    public void setTileMouseListener(java.awt.event.MouseListener a) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (var old : tiles[i][j].getMouseListeners()) {
                    tiles[i][j].removeMouseListener(old);
                }
                tiles[i][j].addMouseListener(a);
            }
        }
    }



    //=====================================================
    // Icons
    //=====================================================

    // helper for icons
    private Icon iconFor(TileFace face) {
        return switch (face) {
            case COVERED -> tileIcon;
            case FLAG -> flagIcon;
            case QUESTION -> question;
            case MINE -> mineIcon;
            case RED_MINE -> redMineIcon;
            case NUM0 -> zero;
            case NUM1 -> one;
            case NUM2 -> two;
            case NUM3 -> three;
            case NUM4 -> four;
            case NUM5 -> five;
            case NUM6 -> six;
            case NUM7 -> seven;
            case NUM8 -> eight;
        };
    }

    public void setTileFace(int r, int c, TileFace face) {
        JLabel tile = tiles[r][c];
        tile.setIcon(iconFor(face));
    }

    private ImageIcon resizeIcon(ImageIcon icon, int w, int h) {
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
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

}
