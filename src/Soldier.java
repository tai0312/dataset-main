import java.awt.Color;
import java.awt.Graphics;

public class Soldier {
    private int x, y;
    private Color color;

    public Soldier(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void moveLeft() {
        if (x > 0) {
            x -= 10; // 左に移動
        }
    }

    public void moveRight() {
        if (x < 780) { // 画面右端の制限
            x += 10; // 右に移動
        }
    }

    public void moveTo(int newX) {
        if (newX > 0 && newX < 780) { // 画面外に出ないように制限
            this.x = newX;
        }
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

    public void setY(int y) {
        this.y = y;
    }
}
