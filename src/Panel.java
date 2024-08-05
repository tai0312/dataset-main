import java.awt.Color;
import java.awt.Graphics;

public class Panel {
    private int x, y;
    private Color color;
    private String operation;

    public Panel(int x, int y, String operation) {
        this.x = x;
        this.y = y;
        this.operation = operation;
        if (operation.startsWith("-")) {
            this.color = new Color(255, 182, 193); // 薄い赤色
        } else {
            this.color = new Color(173, 216, 230); // 薄い青色
        }
    }

    public void update() {
        y += 2; // パネルの降下速度を固定
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, 400, 50);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 400, 50);
        g.drawString(operation, x + 175, y + 30);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return 400;
    }

    public int getHeight() {
        return 50;
    }

    public String getOperation() {
        return operation;
    }
}
