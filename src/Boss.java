import java.awt.Color;
import java.awt.Graphics;

public class Boss {
    private int x, y;
    private int requiredSoldiers;
    private Color color;
    private static final int FALL_SPEED = 2; // 降下速度を一定に保つ

    public Boss(int x, int y, int requiredSoldiers, Color color) {
        this.x = x;
        this.y = y;
        this.requiredSoldiers = requiredSoldiers;
        this.color = color;
    }

    public void update() {
        y += FALL_SPEED; // ボスを下に移動させる
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, 200, 100); // ボスの幅を200に設定
        g.setColor(Color.BLACK);
        g.drawRect(x, y, 200, 100); // ボスの枠線を描画
        g.drawString("Boss", x + 75, y + 50); // テキストの位置を中央に調整
        g.drawString("Need: " + requiredSoldiers, x + 65, y + 70); // テキストの位置を中央に調整
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRequiredSoldiers() {
        return requiredSoldiers;
    }
}
