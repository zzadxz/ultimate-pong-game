import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball extends GameObject {
    // Add any state variables here
    private int dx; // horizontal speed of the ball
    private int dy; // vertical speed of the ball
    private int prevX;
    private int prevY;
    private final int MAX_SPEED = 7; // maximum speed of the ball
    private Random random; // random object to add randomness to the vertical speed
    private double accelerationFactor = 1.35; // acceleration factor for the ball's speed
    public Ball(int speedX, int speedY) {
        super();
        this.dx = speedX;
        this.dy = speedY;
        random = new Random();
    }
    public void act() {
        // store previous position
        prevX = getX();
        prevY = getY();
        // ball's position based on its speed
        setX(getX() + dx);
        setY(getY() + dy);
    }
    // Add any additional methods here
    public void setSpeed(int speedX, int speedY) {
        this.dx = speedX;
        this.dy = speedY;
    }
    public void increaseSpeed() {
        if (dx < MAX_SPEED){
            dx *= accelerationFactor;
        }
        if (dx >= MAX_SPEED) {
            dx = MAX_SPEED;
        }
    }
    public int getPreviousX() {
        return prevX;
    }
    public int getPreviousY() {
        return prevY;
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, getWidth() - 1, getHeight() - 1);
        g2d.setColor(c);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
        // draw black dots on the ball
        int dotRadius = 2;
        g2d.fillOval(getWidth() / 2 - dotRadius, dotRadius, dotRadius * 2, dotRadius * 2);
        g2d.fillOval(getWidth() / 2 - dotRadius, getHeight() - dotRadius * 3, dotRadius * 2, dotRadius * 2);
        g2d.fillOval(dotRadius, getHeight() / 2 - dotRadius, dotRadius * 2, dotRadius * 2);
        g2d.fillOval(getWidth() - dotRadius * 3, getHeight() / 2 - dotRadius, dotRadius * 2, dotRadius * 2);
        g2d.dispose();
    }
    public void bounce() {
        prevX = getX();
        prevY = getY();
        dx = -dx;
        // Generate a random number between -4 and 4 (excluding 0) for the vertical speed
        int randomDy = random.nextInt(9) - 5;
        while (randomDy == 0) {
            randomDy = random.nextInt(9) - 5;
        }
        dy = randomDy;
    }
    public void bounceY() {
        dy = -dy;
    }
}