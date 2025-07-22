public class App {
    public static void main(String[] args) throws Exception {
        Board model = new Board();
        Window view = new Window();
        Game controller = new Game(view, model);
        
        controller.setUpGame();

    }
}
