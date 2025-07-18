import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.*;


public class Game extends Frame implements WindowListener{    

    public Game () {
        setLayout(new GridLayout());
        
        Panel pnl = new Panel();

        MenuBar menuBar = new MenuBar();
        setMenuBar(menuBar);

        Menu menu = new Menu("Game");
        menuBar.add(menu);

        MenuItem newGameItem = new MenuItem("New Game");

        menu.add(newGameItem);
        

        add(pnl);
        addWindowListener(this);

        setTitle("Minesweeper");
        setSize(300, 300);

        setVisible(true);
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
