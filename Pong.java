/**
 * author @Kyle
 * 10 April 2023
 * The Pong Project: ICS4UE
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Pong extends Game implements ActionListener {
    // Add any state variables or object references here
    Ball ball;
    Paddle leftPaddle;
    Paddle rightPaddle;
    int leftScore;
    int rightScore;
    private long elapsedTime = 0; // in milliseconds
    private int delay = 25; // delay in milliseconds
    private boolean initialDelayDone = false;
    private int initialDelay = 2000; // 2000 milliseconds or 2 seconds
    int winningScore = 10;
    JLabel leftScoreLabel;
    JLabel rightScoreLabel;
    JLabel titleLabel;
    JPanel startMenu;
    JButton startButton;
    JButton exitButton;
    boolean gameStarted;
    /**
     * Fill in this method with code that tells the game what to do
     * before actual play begins
     */
    public void setup() {
        stopGame();
        setSize(815, 655); // Set the size of the game window
        // initialize start menu
        gameStarted = false;
        startMenu = new JPanel();
        startMenu.setSize(getFieldWidth(), getFieldHeight());
        startMenu.setLocation(0, 0);
        startMenu.setLayout(new GridBagLayout());
        startMenu.setBackground(new Color(0, 0, 0));
        add(startMenu);
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, ("Hello, welcome and enjoy :)"), "The Ultimate Pong Game", JOptionPane.INFORMATION_MESSAGE);
            remove(startMenu);
            gameStarted = true;
            revalidate();
            repaint();
        });
        startMenu.add(startButton);
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        startMenu.add(exitButton);
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        // initialize ball
        ball = new Ball(4, 4);
        ball.setSize(20, 20);
        ball.setX(100);
        ball.setY(getFieldHeight() / 2);
        add(ball);
        // initialize left paddle
        leftPaddle = new Paddle();
        leftPaddle.setSize(10, 50);
        leftPaddle.setY(getFieldHeight() / 2);
        leftPaddle.setColor(Color.BLUE);
        add(leftPaddle);
        // initialize right paddle
        rightPaddle = new Paddle();
        rightPaddle.setSize(10, 50);
        rightPaddle.setY(getFieldHeight() / 2);
        rightPaddle.setColor(Color.YELLOW);
        rightPaddle.setX(getFieldWidth() - rightPaddle.getWidth());
        add(rightPaddle);
        // initialize score labels
        leftScoreLabel = new JLabel("Left: ");
        leftScoreLabel.setSize(50, 20);
        leftScoreLabel.setLocation(50, 10);
        leftScoreLabel.setForeground(Color.WHITE);
        leftScoreLabel.setFont(new Font("Georgia", Font.PLAIN, 12));
        leftScoreLabel.setOpaque(true);
        leftScoreLabel.setBackground(new Color(50, 50, 50));
        add(leftScoreLabel);
        rightScoreLabel = new JLabel("Right: ");
        rightScoreLabel.setSize(50, 20);
        rightScoreLabel.setLocation(getFieldWidth() - 100, 10);
        rightScoreLabel.setForeground(Color.WHITE);
        rightScoreLabel.setFont(new Font("Georgia", Font.PLAIN, 12));
        rightScoreLabel.setOpaque(true);
        rightScoreLabel.setBackground(new Color(50, 50, 50));
        add(rightScoreLabel);
        // title label
        titleLabel = new JLabel("First to " + winningScore + " WINS!");
        titleLabel.setSize(100, 20);
        titleLabel.setLocation(getFieldWidth() / 2 - titleLabel.getWidth() / 2, 10);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 10));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(50, 50, 50));
        add(titleLabel);

    }
    /**
     * Fill in this method with code that tells the playing field what to do
     * from one moment to the next
     */
    public void act() {
        if (!gameStarted) {
            return;
        }
        // check for collisions with the top and bottom of the screen
        if (ball.getY() < 0 || ball.getY() > getFieldHeight() - ball.getWidth()) {
            ball.bounceY();
        }
        // check if the ball goes out of bounds and update the score
        if (ball.getX() < 0) {
            rightScore++;
            rightScoreLabel.setText(Integer.toString(rightScore));
            resetGame();
        } else if (ball.getX() > getFieldWidth() - ball.getWidth()) {
            leftScore++;
            leftScoreLabel.setText(Integer.toString(leftScore));
            resetGame();
        }
        // check for collisions with the paddles and bounce the ball
        // Create Rectangle objects for the ball and paddles
        Rectangle ballRect = new Rectangle(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        Rectangle leftPaddleRect = new Rectangle(leftPaddle.getX(), leftPaddle.getY(), leftPaddle.getWidth(), leftPaddle.getHeight());
        Rectangle rightPaddleRect = new Rectangle(rightPaddle.getX(), rightPaddle.getY(), rightPaddle.getWidth(), rightPaddle.getHeight());

        // Check for collisions using the intersects method
        boolean collisionWithLeftPaddle = ballRect.intersects(leftPaddleRect);
        boolean collisionWithRightPaddle = ballRect.intersects(rightPaddleRect);

        if (collisionWithLeftPaddle || collisionWithRightPaddle) {
            ball.bounce();
            ball.increaseSpeed();
        }
        // getting user input for moving the paddles
        // left paddle
        if (ZKeyPressed() && leftPaddle.getY() > 0) {
            leftPaddle.moveUp();
        } else if (XKeyPressed() && leftPaddle.getY() + leftPaddle.getHeight() < getFieldHeight()) {
            leftPaddle.moveDown();
        } else {
            leftPaddle.stop(); // Stop moving if no keys are pressed
        }
        leftPaddle.act(); // Call the act method for the left paddle
        // right paddle
        if (MKeyPressed() && rightPaddle.getY() > 0) {
            rightPaddle.moveUp();
        } else if (NKeyPressed() && rightPaddle.getY() + rightPaddle.getHeight() < getFieldHeight()) {
            rightPaddle.moveDown();
        } else {
            rightPaddle.stop(); // Stop moving if no keys are pressed
        }
        rightPaddle.act(); // Call the act method for the right paddle
        // winning conditions (currently the winning score is 10; it can be adjusted in the field)
        if (leftScore >= winningScore || rightScore >= winningScore) {
            stopGame();
            // the winner displayed :D
            JOptionPane.showMessageDialog(this, (leftScore > rightScore ? "Left" : "Right") + " player wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            // reset the game and scores
            leftScore = 0;
            rightScore = 0;
            resetGame();
            startGame();
        }
    }

    // Add any additional methods here
    @Override
    public int getFieldWidth() {
        return 800;
    }
    @Override
    public int getFieldHeight() {
        return 600;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            gameStarted = true;
            startButton.setVisible(false);
            exitButton.setVisible(false);
            startGame();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }


    /**
     * reset the game by resetting the ball and paddle positions and scores
     * pre-condition: -
     * post-condition: ball is reset to center of field, paddles are reset to center of field, and scores are reset to 0 and displayed on the screen.
     */
    public void resetGame() {
        // reset ball position and speed
        ball.setX(getFieldWidth() / 2 - ball.getWidth() / 2);
        ball.setY(getFieldHeight() / 2 - ball.getHeight() / 2);
        // reset paddle positions
        leftPaddle.setY(getFieldHeight() / 2 - leftPaddle.getHeight() / 2);
        rightPaddle.setY(getFieldHeight() / 2 - rightPaddle.getHeight() / 2);
        leftScoreLabel.setText("Left: " + leftScore);
        rightScoreLabel.setText("Right: " + rightScore);
        // reset ball speed
        ball.setSpeed(4, 4);
    }

    public static void main(String[] args) {
        Pong p = new Pong();
        p.setVisible(true);
        p.initComponents();
        p.startGame();
    }
}