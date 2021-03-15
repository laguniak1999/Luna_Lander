	import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.Rectangle;
	import java.awt.Shape;
	import java.awt.geom.Rectangle2D;
	import java.awt.image.BufferedImage;
	/**
	 * Klasa dziedziczy po klasie GameObject 
	 * Przez co jest obiektem gry, w tym przypadku jest to klepsydra
	 *
	 */
	public class TimeSupply extends GameObject{
		
		Handler handler;
		private float gravitation;
		private BufferedImage hourglass_image;

		public TimeSupply (int x, int y, ID id) {
			super(x, y, id);

		}
		/**
		 * Konstruktor przypisujacy wartosci: poczatkowe polozenie klepsydry
		 * @param id poprzez ten parametr okreslamy, ze jest to klepsydra,a nie inny obiekt
		 * @param handler lapie nasz obiekt, przez co mozemy nim sterowac itp..
		 * @param BufferedImage parametr przechowujacy obrazek klepsydry
		 * @param gravitation grawitacja na planecie
		 */
		public TimeSupply (int x, int y, ID id, Handler handler,BufferedImage hourglass_image,float gravitation) {
			super(x, y, id);
			this.handler = handler;
			this.hourglass_image=hourglass_image;
			this.gravitation=gravitation+1;
		}


		/**
		 * Metoda update polaczona z gra odpala poruszanie oraz kolizje
		 * 
		 */
		public void update() {
		colision();
	    y+=gravitation;
			
		}
		/**
		 * Odpowiada za utworzenie klepsydry
		 */
		public void render(Graphics g) {
			Graphics2D g2d;
			g2d=Scaling(g);
			g2d.setColor(Color.blue);		
		    g2d.drawImage(hourglass_image,x,y,null);
			
		}
		/**
		 * Metoda odpowiedzialna za reakcje podczas kolizji
		 */
		private void colision() {
			for(int i=0; i<handler.object.size();i++) {
			
				GameObject tempObject = handler.object.get(i);
				
				if(tempObject.getId() == ID.Block ||tempObject.getId() == ID.Airstrip) {
					
					if(tempObject.getBounds().intersects(getBoundsRect())) {
						handler.removeObject(this);

				    }
				}		
			}
		}

		public Shape getBounds() {
			// TODO Auto-generated method stub
			return null;
		}

		/**
		 * Metoda zwracajaca ksztalt oraz wymiary klepsydry(potrzebna do kolizji)
		 */
		public Rectangle2D getBoundsRect() {
			Rectangle2D rect=new Rectangle(x,y,25,35);
			return rect;
		}


		@Override
		public float getFuel() {
			// TODO Auto-generated method stub
			return 0;
		}


	}


