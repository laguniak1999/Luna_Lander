import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *Klasa implementujaca obsluge klawiatury
 */
public class Keyboard implements KeyListener {

	Handler handler;

	
	public Keyboard(Handler handler) {
		this.handler = handler;
	}

	public void keyTyped(KeyEvent e) {

	}
	/**
     * Metoda reaguje, gdy przycisk zostanie wcisniety
     * Ustawia odpowiednie wartosci w klasie handler przez co wiadomo, jaki przycisk zostac wcisniety
     */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i=0; i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
					
		    if(tempObject.getId() == ID.Rocket) {
		    	if(key == KeyEvent.VK_W) handler.setUp(true);
		    	if(key == KeyEvent.VK_S) handler.setDown(true);
		    	if(key == KeyEvent.VK_D) handler.setRight(true);
		    	if(key == KeyEvent.VK_A) handler.setLeft(true);
				//Trzeba dać tu jakieś clip stop czy coś bo daje nieprzyjemny dźwięk i się utrzymuje nawet po puszczeniu klawisza
		    }
		    if(key== KeyEvent.VK_ESCAPE)
			{ 	System.out.println("Warunkowe zamknięcie okna gry");
				System.exit(1);}
			}
			if(key == KeyEvent.VK_SPACE)
			{
				if(Luna_Lander.paused) Luna_Lander.paused= false;
				else Luna_Lander.paused = true;
			}

	}
	/**
     * Metoda reaguje, gdy przycisk zostanie wycisniety
     * Ustawia odpowiednie wartosci w klasie handler przez co wiadomo, jaki przycisk zostac wycisniety
     */

	public void keyReleased(KeyEvent e) {
int key = e.getKeyCode();
		
		for(int i=0; i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
					
		    if(tempObject.getId() == ID.Rocket) {
		    	if(key == KeyEvent.VK_W) handler.setUp(false);
		    	if(key == KeyEvent.VK_S) handler.setDown(false);
		    	if(key == KeyEvent.VK_D) handler.setRight(false);
		    	if(key == KeyEvent.VK_A) handler.setLeft(false);
		    }
	    }
	}


}
