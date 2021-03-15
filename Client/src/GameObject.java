import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
/**
 * Abstrakcyjna klasa, pozwalajaca tworzyc rozne obiekty w grze
 * W taki sposob, ze kazdy moze miec inne wlasciwosci
 *
 */
public abstract class GameObject {
	protected int x,y,a,b;
	protected Boolean isCrashed;
	protected Boolean isLanded;
	protected float velX = 0, velY = 0;
	protected ID id;
	
	public GameObject(int x, int y, ID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
    public abstract Shape getBounds();
    public abstract Rectangle2D getBoundsRect();
	public abstract float getFuel();
	public Graphics2D Scaling(Graphics g) {
			int Width = Luna_Lander.getW();
			int Height = Luna_Lander.getH();
			Graphics2D g2d = (Graphics2D) g;
			AffineTransform saveTransform = g2d.getTransform();
		    AffineTransform scaleMatrix = new AffineTransform();
		    float sx =(1f+(Width-1040)/(float)1040);
		    float sy =(1f+(Height-647)/(float)647);
		    scaleMatrix.scale(sx, sy);
		    g2d.setTransform(scaleMatrix);
			return g2d;
		}
    public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getVelX() {
		return velX;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public float getVelY() {
		return velY;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Boolean getIsCrashed() {
		return isCrashed;
	}

	public void setIsCrashed(Boolean isCrashed) {
		this.isCrashed = isCrashed;
	}

	public Boolean getIsLanded() {
		return isLanded;
	}

	public void setIsLanded(Boolean isLanded) {
		this.isLanded = isLanded;
	}


}
