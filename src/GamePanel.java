import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private Thread thread;
    private boolean running;
    private Soldier soldier;
    private List<Panel> panels;
    private List<Obstacle> obstacles;
    private Boss boss;
    private boolean gameOver;
    private boolean gameWon;
    private int panelPasses;
    private boolean bossFight;
    private boolean startScreen;
    private int score;
    private int level;
    private static final int PANEL_PASS_COUNT = 5;
    private static final int PANEL_FALL_SPEED = 2;
    private static final int OBSTACLE_FALL_SPEED = 3;
    private Random random;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        soldier = new Soldier(WIDTH / 2, HEIGHT - 50, Color.BLUE);
        panels = new ArrayList<>();
        obstacles = new ArrayList<>();
        gameOver = false;
        gameWon = false;
        panelPasses = 0;
        bossFight = false;
        startScreen = true;
        score = 0;
        level = 1;
        random = new Random();
        initializeGameObjects();
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
    }

    private void startGame() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (running) {
            updateGame();
            repaint();
            try {
                Thread.sleep(16); // 約60FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        if (gameOver || startScreen || gameWon) {
            return;
        }

        if (bossFight) {
            boss.update();
            if (boss.getY() >= soldier.getY()) {
                if (score >= boss.getRequiredSoldiers()) {
                    gameWon = true;
                    bossFight = false;
                    panelPasses = 0;
                    soldier.setY(HEIGHT - 50);
                    initializeGameObjects();
                } else {
                    gameOver = true;
                    System.out.println("You lose!");
                }
            }
            return;
        }

        for (Panel panel : panels) {
            panel.update();
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }

        if (panels.isEmpty() || panels.get(panels.size() - 1).getY() > HEIGHT / 2) {
            generatePanels();
            generateObstacles();
        }

        checkCollisions();

        if (panelPasses >= PANEL_PASS_COUNT) {
            bossFight = true;
            panels.clear();
            obstacles.clear();
            boss = new Boss(WIDTH / 2 - 100, -100, calculateMaxScore(score) / 2, Color.RED);
        }
    }

    private void generatePanels() {
        int y = -50;
        int leftX = 0;
        int rightX = WIDTH - 400;
        String[] operations = {"+5", "+10", "*2", "*3", "-3", "-5"};
        String leftOperation = operations[(int) (Math.random() * operations.length)];
        String rightOperation = operations[(int) (Math.random() * operations.length)];

        panels.add(new Panel(leftX, y, leftOperation));
        panels.add(new Panel(rightX, y, rightOperation));
    }

    private void generateObstacles() {
        int x = random.nextInt(WIDTH - 30);
        obstacles.add(new Obstacle(x, -30));
    }

    private void checkCollisions() {
        for (int i = 0; i < panels.size(); i++) {
            Panel panel = panels.get(i);
            if (soldier.getX() < panel.getX() + panel.getWidth() &&
                soldier.getX() + 20 > panel.getX() &&
                soldier.getY() < panel.getY() + panel.getHeight() &&
                soldier.getY() + 20 > panel.getY()) {
                applyOperation(panel.getOperation());
                panels.remove(i);
                panelPasses++;
                break;
            }
        }

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obstacle = obstacles.get(i);
            if (soldier.getX() < obstacle.getX() + obstacle.getSize() &&
                soldier.getX() + 20 > obstacle.getX() &&
                soldier.getY() < obstacle.getY() + obstacle.getSize() &&
                soldier.getY() + 20 > obstacle.getY()) {
                gameOver = true;
                System.out.println("Hit an obstacle!");
                return;
            }
        }
    }

    private void applyOperation(String operation) {
        if (operation.startsWith("+")) {
            int value = Integer.parseInt(operation.substring(1));
            score += value;
        } else if (operation.startsWith("*")) {
            int value = Integer.parseInt(operation.substring(1));
            score *= value;
        } else if (operation.startsWith("-")) {
            int value = Integer.parseInt(operation.substring(1));
            score -= value;
        }
    }

    private void initializeGameObjects() {
        generatePanels();
    }

    private int calculateMaxScore(int currentScore) {
        int max = currentScore;
        for (int i = 0; i < PANEL_PASS_COUNT; i++) {
            max += 10;
        }
        return max;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE); // 背景色を白に設定
        if (startScreen) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Press any key to Start", WIDTH / 2 - 150, HEIGHT / 2);
            return;
        }

        if (gameOver) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Press R to Retry", WIDTH / 2 - 100, HEIGHT / 2 + 50);
            return;
        }

        if (gameWon) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("You Win!", WIDTH / 2 - 100, HEIGHT / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Press any key to continue", WIDTH / 2 - 150, HEIGHT / 2 + 50);
            return;
        }

        if (bossFight) {
            boss.draw(g);
            soldier.draw(g);
        } else {
            soldier.draw(g);
            for (Panel panel : panels) {
                panel.draw(g);
            }
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(g);
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Score: " + score, 10, 25); // スコアのみ表示
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A) {
            soldier.moveLeft();
        } else if (key == KeyEvent.VK_D) {
            soldier.moveRight();
        } else if (key == KeyEvent.VK_R && gameOver) {
            resetGame();
        } else if (!gameOver && gameWon) {
            gameWon = false;
        } else if (startScreen) {
            startScreen = false;
            startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (startScreen) {
            startScreen = false;
            startGame();
        } else if (gameOver && e.getButton() == MouseEvent.BUTTON1) {
            resetGame();
        } else if (!gameOver && gameWon) {
            gameWon = false;
        } else {
            soldier.moveTo(e.getX() - 10);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        soldier.moveTo(e.getX() - 10);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void resetGame() {
        soldier = new Soldier(WIDTH / 2, HEIGHT - 50, Color.BLUE);
        panels.clear();
        obstacles.clear();
        gameOver = false;
        gameWon = false;
        panelPasses = 0;
        bossFight = false;
        score = 0;
        level = 1;
        initializeGameObjects();
        startGame();
    }
}
