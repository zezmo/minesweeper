import java.awt.*;
import java.awt.event.*;

public class Window extends Frame implements WindowListener {
    private int width, height;
    private Window gameWindow;
    private Panel outerPanel;

    private Label timerLabel;
    private Button newGameButton;
    private Label bombsRemainingLabel;

    private MenuBar menuBar;
    private Menu menu;
    private MenuItem menuItem;

    public Window() {
        
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