

import java.awt.Graphics;
import java.util.LinkedList;
/**
 * Klasa, ktora jest uchwytem do naszych obiektow 
 * Posiada LinkedList z naszymi obiektami
 */
public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	private boolean up = false, down = false, right = false, left = false;
	
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	/**
	 * Metoda updatujaca obiekty, ktore sa uchwycone przez uchwyt
	 */
	public void update()
	{
		for(int i=0; i<object.size();i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.update();
		}
	}
	/**
	 * Metoda renderujaca obiekty, ktore sa uchwycone przez uchwyt
	 */
	public void render(Graphics g) {
		for(int i=0; i<object.size();i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
	    }

    }
	/**
	 * Metoda Dodajaca obiekt do listy
	 */
	public void addObject(GameObject tempObject) {
		object.add(tempObject);
		
	}
	/**
	 * Metoda usuwajaca obiekt z listy
	 */
	public void removeObject(GameObject tempObject) {
		object.remove(tempObject);
	}
	
	public void reset() {
		up = false;
	    down = false; 
	    right = false; 
	    left = false;
	}
}
