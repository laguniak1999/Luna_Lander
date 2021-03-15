import java.awt.Color;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.Rectangle;
	import java.awt.Shape;
	import java.awt.geom.Rectangle2D;
	import java.awt.image.BufferedImage;
	/**
	 * Klasa dziedziczy po klasie GameObject 
	 * Przez co jest obiektem gry, w tym przypadku jest to zrzut jedzenia
	 *
	 */
	public class MealSupply extends GameObject{
		
		Handler handler;
		private float gravitation;
		private BufferedImage meal_image;

		public MealSupply (int x, int y, ID id) {
			super(x, y, id);

		}
		/**
		 * Konstruktor przypisujacy wartosci: poczatkowe wartosci zaopatrzenia
		 * @param id poprzez ten parametr okreslamy, ze jest to zrzut jedzenia,a nie inny obiekt
		 * @param handler lapie nasz obiekt, przez co mozemy nim sterowac itp..
		 * @param BufferedImage parametr przechowujacy obrazek burgera
		 * @param gravitation grawitacja na planecie
		 */
		public MealSupply (int x, int y, ID id, Handler handler,BufferedImage meal_image,float gravitation) {
			super(x, y, id);
			this.handler = handler;
			this.meal_image=meal_image;
			this.gravitation=gravitation+1;
		}
		/**
		 * Metoda update polaczona z gra odpala poruszanie oraz kolizje
		 * 
		 */

		public void update() {
		colision();
	   // y+=gravitation;
			
		}
		/**
		 * Odpowiada za utworzenie ladowiska 
		 */
		public void render(Graphics g) {
			Graphics2D g2d;
			g2d=Scaling(g);
			g2d.setColor(Color.blue);		
		    g2d.drawImage(meal_image,x,y,null);
			
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
		 * Metoda zwracajaca ksztalt oraz wymiary burgera(potrzebna do kolizji)
		 */
		public Rectangle2D getBoundsRect() {
			Rectangle2D rect=new Rectangle(x,y,21,24);
			return rect;
		}


		@Override
		public float getFuel() {
			// TODO Auto-generated method stub
			return 0;
		}


	}



