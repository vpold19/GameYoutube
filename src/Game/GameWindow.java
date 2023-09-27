package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static GameWindow gameWindow;
    private static Image backGround;
    private static Image drop;
    private static Image gameOver;
    private static Image restart;
    private static long lastFrameTime;
    private static float dropY = -150;
    private static float dropX;
    private static float dropV = 220;
    private static int score = 1;
    private static float restartX = 30;
    private static float restartY = 0;

    public static void main(String[] args) throws IOException {
        backGround = ImageIO.read(GameWindow.class.getResourceAsStream("background.jpg"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("burgeer.png"));
        gameOver = ImageIO.read(GameWindow.class.getResourceAsStream("gameOver1.png"));
        restart = ImageIO.read(GameWindow.class.getResourceAsStream("restart2.png"));
        gameWindow = new GameWindow();
        JLabel record = new JLabel("Рекорд : " + score);

        record.setSize(220, 150);
        record.setPreferredSize(new Dimension(100, 25));
        record.setFont(new Font("Рекорд : " + score, Font.PLAIN, 15));
        record.setOpaque(true);
        record.setBackground(Color.WHITE);
        record.setVisible(true);

        gameWindow.setSize(900, 600);
        gameWindow.setResizable(false);
        lastFrameTime = System.nanoTime();
        gameWindow.setTitle("Бургер");
        gameWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameWindow.setLocation(200, 150);
        GameField gameField = new GameField();
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float dropXRight = dropX + drop.getWidth(null);
                float dropTBottom = dropY + drop.getHeight(null);
                boolean isDrop = x >= dropX && x <= dropXRight && y >= dropY && y <= dropTBottom;
                if (isDrop) {
                    dropY = -100;
                    dropX = (int) (Math.random() * (gameField.getWidth() - drop.getHeight(null)));
                    gameWindow.setTitle("Рекорд : " + score);
                    record.setText("Рекорд : " + score);
                    score++;
                    dropV = dropV + 20;
                } else if (isDrop = false) {
                    if (score<=0){
                        score--;
                    }
                }
                float restartXLeft = restartX + restart.getWidth(null);
                float restartYBottom = restartY + restart.getHeight(null);
                boolean ifRestart = x >= restartX && x <= restartXLeft && y >= restartY && y <= restartYBottom;
                if (ifRestart) {
                    dropY = -100;
                    dropX = (int) (Math.random() * (gameField.getWidth() - drop.getHeight(null)));
                    score = 1;
                    dropV = 200;
                    gameWindow.setTitle("Рекорд: " + score);
                    record.setText("Рекорд : " + score);
                }
            }
        });
        gameWindow.add(gameField);
        gameField.add(record);
        gameWindow.setVisible(true);

    }

    private static void onRepaint(Graphics g) {
        g.drawImage(backGround, 0, 0, null);
        long correntTime = System.nanoTime();
        float deltaTime = (correntTime - lastFrameTime) * 0.000000001f;
        lastFrameTime = correntTime;
        dropY = dropY + dropV * deltaTime;
        g.drawImage(drop, (int) dropX, (int) dropY, null);
        if (dropY > gameWindow.getHeight()) {
            g.drawImage(gameOver, 200, -100, null);
            g.drawImage(restart, (int) restartX, (int) restartY, null);
        }
    }

    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
