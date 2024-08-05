import java.awt.Color;
import java.awt.Graphics;

public class Obstacle {
    private int x, y;
    private static final int SIZE = 30;
    private static final int FALL_SPEED = 3;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y += FALL_SPEED;
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, SIZE, SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return SIZE;
    }
}
