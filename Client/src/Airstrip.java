import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
/**
 * Klasa dziedziczy po klasie GameObject 
 * Przez co jest obiektem gry, w tym przypadku jest to l�dowisko
 *
 */
public class Airstrip extends GameObject{
	
	private int[] strip_cords_x;
	private int[] strip_cords_y;
	private int m;
	/**
	 * Konstruktor przypisujacy wartosci poczatkowe polozenia ladowiska oraz ksztaltu pobrane z pliku konfiguracyjnego
	 * @param id poprzez ten parametr okreslamy, ze jest to ladowisko,a nie inny obiekt
	 */
	public Airstrip(int []a,int[]b,int m,int x, int y, ID id) {
		super(x, y, id);
		strip_cords_x = a;	
		strip_cords_y = b;
		this.m = m;
	}

	public void update() {

		
	}
	/**
	 * Odpowiada za utworzenie ladowiska 
	 */

	public void render(Graphics g) {	
	Graphics2D g2d;
	g2d=Scaling(g);
    g2d.setColor(new Color(36, 147, 18));
	g2d.drawPolygon(strip_cords_x, strip_cords_y, m);
	g2d.fillPolygon(strip_cords_x, strip_cords_y, m);
	}

	/**
	 * Metoda zwracajaca ksztalt oraz wymiary ladowiska(potrzebna do kolizji)
	 */
	public Shape getBounds() {
		Shape pol=new Polygon(strip_cords_x,strip_cords_y,m);
		return pol;
	}

	@Override
	public Rectangle2D getBoundsRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getFuel() {
		// TODO Auto-generated method stub
		return 0;
	}


}
