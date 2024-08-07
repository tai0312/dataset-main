import java.awt.Color;
import java.awt.Font; // Fontをインポート
import java.awt.Graphics;

public class Boss {
    private int x, y;
    private int requiredSoldiers;
    private Color color;
    private static final int WIDTH = 800; // ボスの横幅を広げる
    private static final int HEIGHT = 50;

    public Boss(int x, int y, int requiredSoldiers, Color color) {
        this.x = x;
        this.y = y;
        this.requiredSoldiers = requiredSoldiers;
        this.color = color;
    }

    public void update() {
        y += 2; // ボスの降下速度
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Boss Score: " + requiredSoldiers, x + WIDTH / 2 - 50, y + HEIGHT / 2 + 5); // ボスのスコアを中央に表示
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getRequiredSoldiers() {
        return requiredSoldiers;
    }
}
