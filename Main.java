import javax.swing.JFrame;

public class Main {
public static void main(String[] args) {
    final int HEIGHT = 600;
    final int WIDTH = 600;

    JFrame frame = new JFrame();
    frame.setSize(600,600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    SnakeGame snakeGame = new SnakeGame(HEIGHT, WIDTH);
    
    frame.add(snakeGame);
    frame.pack();
    snakeGame.requestFocus();
}
}
