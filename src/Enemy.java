import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
    private int x, y;
    private Color color;

    public Enemy(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void update() {
        y += 2; // 敵を下に移動させる
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 20, 20);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
