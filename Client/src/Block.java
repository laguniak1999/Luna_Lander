import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
/**
 * Klasa dziedziczy po klasie GameObject 
 * Przez co jest obiektem gry, w tym przypadku jest to teren, o ktory moze rozbic sie nasza rakieta
 *
 */
public class Block extends GameObject {
	
	private int[] block_cords_x;	
	private int[] block_cords_y;		
	private int n;
	/**
	 * Konstruktor przypisujacy wartosci: poczatkowe polozenia ladowiska oraz ksztaltu pobrane z pliku konfiguracyjnego
	 * @param id poprzez ten parametr okreslamy, ze jest to teren,a nie inny obiekt
	 */
	public Block(int[] a,int [] b, int n,int x, int y, ID id) {
		super(x, y, id);
		block_cords_x=a;
		block_cords_y=b;
		this.n=n;
	}

	public void update() {

		
	}

	
	 
    /**
	 * Odpowiada za utworzenie terenu 
	 */
	public void render(Graphics g) {
	Graphics2D g2d;
    g2d=Scaling(g);
	g2d.setColor(new Color(18,17,17 ,209));
	g2d.drawPolygon(block_cords_x, block_cords_y, n);
    g2d.fillPolygon(block_cords_x, block_cords_y, n);
    
	}

	/**
	 * Metoda zwracajaca ksztalt oraz wymiary ladowiska(potrzebna do kolizji)
	 */
	public Shape getBounds() {
		Shape pol=new Polygon(block_cords_x,block_cords_y,n);
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
