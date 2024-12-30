import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class AppPanel extends JPanel {

    static BufferedImage playerCarImage;
    static BufferedImage bgImage;
    static BufferedImage obstacleCarImage;

    Timer timer;
    int playerX = 200;
    int playerY = 350;

    int obstacleX = 200;
    int obstacleY = -150;
    boolean gameOver = false;

    AppPanel() {
        setSize(500, 500);
        showBgImage();
        showPlayerCarImage();
        showObstacleCarImage();
        startGameLoop();
        setupKeyboardControls();
        setFocusable(true);
    }

    // background image
    static void showBgImage() {
        try {
            bgImage = ImageIO.read(AppPanel.class.getResource("road1.png"));
        } catch (IOException e) {
            System.out.println("No background image found. Ensure 'road1.png' is in the correct location.");
            e.printStackTrace();
        }
    }

    // player car image
    static void showPlayerCarImage() {
        try {
            playerCarImage = ImageIO.read(AppPanel.class.getResource("car.png")); 
        } catch (IOException e) {
            System.out.println("No player car image found. Ensure 'car.png' is in the correct location.");
            e.printStackTrace();
        }
    }

    // the obstacle car image
    static void showObstacleCarImage() {
        try {
            obstacleCarImage = ImageIO.read(AppPanel.class.getResource("car1.png")); 
        } catch (IOException e) {
            System.out.println("No obstacle car image found. Ensure 'car1.png' is in the correct location.");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
        }

        
        if (playerCarImage != null) {
            g.drawImage(playerCarImage, playerX, playerY, 90, 100, null);
        }

        
        if (obstacleCarImage != null) {
            g.drawImage(obstacleCarImage, obstacleX, obstacleY, 100, 105, null);
        }

        
        if (checkCollision()) {
            gameOver = true;
            g.setColor(Color.RED);
            g.drawString("Game Over! Press SPACEBAR to Restart", 150, 250);
        }
    }

    void startGameLoop() {
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver) {
                    obstacleY += 5; 

                    
                    if (obstacleY > getHeight()) {
                        obstacleY = -100;
                        obstacleX = (int) (Math.random() * (getWidth() - 100));
                    }

                    repaint();
                }
            }
        });
        timer.start();
    }

    
    void setupKeyboardControls() {
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!gameOver) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT:
                            playerX += 10;
                            break;
                        case KeyEvent.VK_LEFT:
                            playerX -= 10;
                            break;
                        case KeyEvent.VK_UP:
                            playerY -= 10;
                            break;
                        case KeyEvent.VK_DOWN:
                            playerY += 10;
                            break;
                    }
                }

                if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
                    restartGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
        
            }
        });
    }

    boolean checkCollision() {
        int playerWidth = 100;
        int playerHeight = 100;
        int obstacleWidth = 100;
        int obstacleHeight = 100;

        return playerX < obstacleX + obstacleWidth &&
               playerX + playerWidth > obstacleX &&
               playerY < obstacleY + obstacleHeight &&
               playerY + playerHeight > obstacleY;
    }

    // Restart the game
    void restartGame() {
        gameOver = false;
        playerX = 200;
        playerY = 350;
        obstacleX = 200;
        obstacleY = -150;
        repaint();
    }
}