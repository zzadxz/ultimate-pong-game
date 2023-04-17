import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
public abstract class GameObject extends JComponent {
    Color c;
    private GameObject ball;
    private GameObject paddle;
    public GameObject() {
        this.c = Color.white;
    }
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }
    public int getX() {
        return this.getLocation().x;
    }
    public int getY() {
        return this.getLocation().y;
    }
    public void setX(int x) {
        super.setLocation(x, this.getLocation().y);
    }
    public void setY(int y) {
        super.setLocation(this.getLocation().x, y);
    }
    public void setColor(Color c) {
        this.c = c;
    }


    public boolean collides(GameObject o) {
        return this.getBounds().intersects(o.getBounds());
    }

    public abstract void act();

}
