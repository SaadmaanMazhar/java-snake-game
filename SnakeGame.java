import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    final int HEIGHT;
    final int WIDTH;
    final int TILESIZE = 25;

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Tile snakeHead;
    Tile food;
    Random random;
    double velocityX;
    double velocityY;
    Timer timer;
    ArrayList<Tile> snakeBody;
    boolean gameOver;

    SnakeGame(int HEIGHT, int WIDTH) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;

        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(HEIGHT / (TILESIZE * 2), WIDTH / (TILESIZE * 2));
        random = new Random();
        food = new Tile(HEIGHT, WIDTH);
        timer = new Timer(100, this);
        snakeBody = new ArrayList<Tile>();

        velocityX = 1;
        velocityY = 0;
        gameOver = false;

        placeFood();
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < HEIGHT * TILESIZE; i++) {
            g.drawLine(i * TILESIZE, 0, i * TILESIZE, HEIGHT);
            g.drawLine(0, i * TILESIZE, WIDTH, i * TILESIZE);
        }

        g.setColor(Color.green);
        g.fillRect(snakeHead.x * TILESIZE, snakeHead.y * TILESIZE, TILESIZE, TILESIZE);

        g.setColor(Color.red);
        g.fillRect(food.x * TILESIZE, food.y * TILESIZE, TILESIZE, TILESIZE);

        g.setColor(Color.green);
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * TILESIZE, snakePart.y * TILESIZE, TILESIZE, TILESIZE);
        }
       if(gameOver){
        g.setFont(new Font("MV Boli",Font.PLAIN,26));
        g.drawString("Game Over\n Score:"+ snakeBody.size(), 200, 200);
       }
       else{
        g.drawString("Score " + snakeBody.size(), 5, 10);
       }
    }

    public void placeFood() {
        food.x = random.nextInt(24);
        food.y = random.nextInt(24);

    }

    public void move() {

        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;

            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        if (snakeHead.x * TILESIZE < 0 || snakeHead.x * TILESIZE > WIDTH || snakeHead.y * TILESIZE < 1
                || snakeHead.y * TILESIZE > WIDTH) {
            gameOver = true;
        }

    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        move();
        if (gameOver) {
            timer.stop();
        }
    }
}
