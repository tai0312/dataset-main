import java.awt.Color;
import java.awt.Graphics;

public class PowerUp {
    private int x, y;
    private static final int SIZE = 20;
    private static final int FALL_SPEED = 2;
    private String type;

    public PowerUp(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update() {
        y += FALL_SPEED;
    }

    public void draw(Graphics g) {
        if (type.equals("speed")) {
            g.setColor(Color.YELLOW);
        } else if (type.equals("invincibility")) {
            g.setColor(Color.MAGENTA);
        }
        g.fillOval(x, y, SIZE, SIZE);
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

    public String getType() {
        return type;
    }
}
