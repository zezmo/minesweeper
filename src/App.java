public class App {
    public static void main(String[] args) throws Exception {
        Minesweeper model = new Minesweeper();
        GameBoard view = new GameBoard();
        Game controller = new Game(view, model);
        
        controller.setUpGame();

    }
}
