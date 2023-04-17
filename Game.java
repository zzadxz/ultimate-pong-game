import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
public abstract class Game extends JFrame {
    private boolean _isSetup = false;
    private boolean _initialized = false;
    private ArrayList _ObjectList = new ArrayList();
    private boolean running = false;
    private boolean p1Left = false;
    private boolean p1Right = false;
    private boolean p2Left = false;
    private boolean p2Right = false;
    public boolean ZKeyPressed() {
        return this.p1Left;
    }
    public boolean XKeyPressed() {
        return this.p1Right;
    }
    public boolean NKeyPressed() {
        return this.p2Left;
    }
    public boolean MKeyPressed() {
        return this.p2Right;
    }
    public abstract void setup();
    public abstract void act();
    public void initComponents() {
        this.getContentPane().setBackground(Color.black);
        this.setup();
        for(int i = 0; i < this._ObjectList.size(); ++i) {
            GameObject o = (GameObject)this._ObjectList.get(i);
            o.repaint();
        }
    }
    public void add(GameObject o) {
        this._ObjectList.add(o);
        this.getContentPane().add(o);
    }
    public void remove(GameObject o) {
        this._ObjectList.remove(o);
        this.getContentPane().remove(o);
    }

    public void setBackground(Color c) {
        this.getContentPane().setBackground(c);
    }
    public Game() {
        this.setSize(400, 400);
        this.getContentPane().setBackground(Color.black);
        this.getContentPane().setLayout((LayoutManager)null);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuFileExit = new JMenuItem("Exit");
        menuBar.add(menuFile);
        menuFile.add(menuFileExit);
        this.setJMenuBar(menuBar);
        this.setTitle("Pong");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        menuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                char pressed = Character.toUpperCase(e.getKeyChar());
                switch (pressed) {
                    case 'M':
                        Game.this.p2Right = true;
                        break;
                    case 'N':
                        Game.this.p2Left = true;
                        break;
                    case 'X':
                        Game.this.p1Right = true;
                        break;
                    case 'Z':
                        Game.this.p1Left = true;
                }

            }
            public void keyReleased(KeyEvent e) {
                char released = Character.toUpperCase(e.getKeyChar());
                switch (released) {
                    case 'M':
                        Game.this.p2Right = false;
                        break;
                    case 'N':
                        Game.this.p2Left = false;
                        break;
                    case 'X':
                        Game.this.p1Right = false;
                        break;
                    case 'Z':
                        Game.this.p1Left = false;
                }

            }
        });
    }
    public void startGame() {
        running = true;
        new Thread(() -> gameLoop()).start();
    }
    public void stopGame() {
        running = false;
    }
    private void gameLoop() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                act();
                for (Object o : _ObjectList) {
                    ((GameObject) o).act();
                }
                repaint();
                delta--;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void p1Wins() {
        _WinDialog d = new _WinDialog(this, "Player 1 Wins!");
        d.setVisible(true);
    }
    public void p2Wins() {
        _WinDialog d = new _WinDialog(this, "Player 2 Wins!");
        d.setVisible(true);
    }
    public int getFieldWidth() {
        return this.getContentPane().getBounds().width;
    }
    public int getFieldHeight() {
        return this.getContentPane().getBounds().height;
    }
    class _WinDialog extends JDialog {
        JButton ok = new JButton("OK");
        _WinDialog(JFrame owner, String title) {
            super(owner, title);
            Rectangle r = owner.getBounds();
            this.setSize(200, 100);
            this.setLocation(r.x + r.width / 2 - 100, r.y + r.height / 2 - 50);
            this.getContentPane().add(this.ok);
            this.ok.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    _WinDialog.this.setVisible(false);
                }
            });
        }
    }
}
