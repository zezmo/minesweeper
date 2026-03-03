package controller;

import model.Board;
import model.BoardConfig;
import model.Difficulty;
import model.GameState;
import view.Window;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {
    private Window window;
    private Board board;
    private BoardConfig config;
    private Timer timer;

    public Game (Difficulty difficulty) {
        config = BoardConfig.fromDifficulty(difficulty);
        board = new Board(config);
        window = new Window(board.getRows(), board.getColumns());
        timer = new Timer(1000, (ActionEvent e) -> onTimerTick());

        setViewListeners();
        window.setTileMouseListener(tileMouse);
        window.setFlagsRemainingValue(board.getFlagsCount());
        window.setTimerValue(board.getElapsedTime());

        window.setVisible(true);
    }

    private final MouseAdapter tileMouse = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int[] rc = parseTile(e);
            if (rc == null) return;
            int r = rc[0], c = rc[1];

            if (SwingUtilities.isLeftMouseButton(e)) handleLeftPress(r, c);
            else if (SwingUtilities.isRightMouseButton(e)) handleRightPress(r, c);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int[] rc = parseTile(e);
            if (rc == null) return;
            int r = rc[0], c = rc[1];

            if (SwingUtilities.isLeftMouseButton(e)) handleLeftRelease(r, c);
        }
    };

    private void newGame(BoardConfig config) {
        this.config = config;
        this.board = new Board(config);
        timer.stop();

        
        window.rebuildBoard(board.getRows(), board.getColumns());
        window.setTileMouseListener(tileMouse);
        
        window.setFlagsRemainingValue(board.getFlagsCount());
        window.setTimerValue(board.getElapsedTime());
        window.repaint();
    }

    private void setViewListeners() {
        window.selectEasy(e -> newGame(BoardConfig.fromDifficulty(Difficulty.EASY)));
        window.selectIntermediate(e -> newGame(BoardConfig.fromDifficulty(Difficulty.INTERMEDIATE)));
        window.selectExpert(e -> newGame(BoardConfig.fromDifficulty(Difficulty.EXPERT)));
        window.newGameButton(e -> newGame(config));
    }

    //=======================================
    // window rendering control
    //=======================================

    private void renderAllTiles() {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                window.setTileFace(i, j, mapCellToFace(i, j));
            }
        }

        window.setFlagsRemainingValue(board.getFlagsCount());
        window.setTimerValue(board.getElapsedTime());
    }

    private Window.TileFace mapCellToFace(int r, int c) {
        var cell = board.getBoardCell(r, c);

        if (!cell.isShowing()) {
            return switch (cell.getFlagState()) {
                case FLAG -> Window.TileFace.FLAG;
                case QUESTION -> Window.TileFace.QUESTION;
                default -> Window.TileFace.COVERED;
            };
        }
        if (cell.isDetonated()) {
            return Window.TileFace.RED_MINE;
        }
        if (cell.isMine()) {
            return Window.TileFace.MINE;
        }

        int n = cell.getMinesNearbyCount();
        return switch (n) {
            case 0 -> Window.TileFace.NUM0;
            case 1 -> Window.TileFace.NUM1;
            case 2 -> Window.TileFace.NUM2;
            case 3 -> Window.TileFace.NUM3;
            case 4 -> Window.TileFace.NUM4;
            case 5 -> Window.TileFace.NUM5;
            case 6 -> Window.TileFace.NUM6;
            case 7 -> Window.TileFace.NUM7;
            case 8 -> Window.TileFace.NUM8;
            default -> Window.TileFace.NUM0;
        };
    }

    private int[] parseTile(MouseEvent e){
        Object src = e.getSource();

        if (!(src instanceof JLabel tile)) {
            return null;
        }

        String name = tile.getName();
        String[] parts = name.split(",");

        int r = Integer.parseInt(parts[0]);
        int c = Integer.parseInt(parts[1]);

        return new int[] {r, c};
    }

    //=======================================
    // event handling
    //=======================================

    private void onTimerTick() {
        if (board.getGameState() == GameState.PLAYING) {
            board.incElapsedTime();
            window.setTimerValue(board.getElapsedTime());
        }
    }

    private void handleLeftPress(int r, int c) {
        // make tile click in on press. Don't show the tile contents until released
        

    }

    private void handleLeftRelease(int r, int c) {
        // show contents of clicked cell and check game state 
        board.revealCell(r, c);
        if (board.getGameState() == GameState.PLAYING && !timer.isRunning()) {
            timer.start();
        }
        renderAllTiles();
        window.repaint();

        if (board.getGameState() == GameState.LOST) {
            timer.stop();
            Window.EndChoice choice = window.winDialog(false);
            handleDialogOptions(choice);
        }

        if (board.getGameState() == GameState.WON) {
            timer.stop();
            Window.EndChoice choice = window.winDialog(true);
            handleDialogOptions(choice);
        }

    }

    private void handleRightPress(int r, int c) {
        // used for cycling flags
        board.cycleFlag(r, c);
        renderAllTiles();
        window.repaint();

    }

    private void handleDialogOptions(Window.EndChoice choice) {
        switch (choice) {
            case PLAY_AGAIN -> newGame(config);
            case EXIT       -> System.exit(0);
            case CANCEL     -> {}
        }
    }
}
