import java.awt.*;

public class Paddle extends GameObject {
    // Add any state variables here
    private int speed;
    public void act() {
        setY(getY() + speed);
    }
    // Add any additional methods here
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void moveUp() {
        setSpeed(-5); // we can set the speed of the paddles here (upward speed)
    }
    public void moveDown() {
        setSpeed(5); // we can set the speed of the paddles here (downward speed)
    }

    public void stop() {
        setSpeed(0);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(c);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }
}