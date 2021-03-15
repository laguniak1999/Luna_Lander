import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
/**
 * Klasa dziedziczy po klasie GameObject 
 * Przez co jest obiektem gry, w tym przypadku jest to rakieta
 *
 */
public class Rocket extends GameObject{

	Handler handler;
	private float fuel = 100;
	private float gravitation;
	private float vel = 0;
	private BufferedImage rocket_image, left_image, right_image, rocket2_image;

	public  float getFuel() {
		return this.fuel;
	}
	public void setFuel(int fuel) {
		this.fuel = fuel;
	}


	/**
	 * Konstruktor przypisujacy wartosci: poczatkowe polozenie rakiety
	 *
	 * @param id            poprzez ten parametr okreslamy, ze jest to rakieta,a nie inny obiekt
	 * @param handler       lapie nasz obiekt, przez co mozemy nim sterowac itp..
	 * //@param BufferedImage parametr przechowujacy obrazek rakiety
	 * @param gravitation   grawitacja na planecie
	 */
	public Rocket(int x, int y, ID id, Handler handler, BufferedImage rocket_image,BufferedImage rocket2_image,BufferedImage left_image,BufferedImage right_image, Float gravitation) {
		super(x, y, id);
		this.handler = handler;
		this.rocket_image = rocket_image;
		this.gravitation = gravitation;
		this.isCrashed=false;
		this.isLanded=false;
		this.rocket2_image = rocket2_image;
		this.left_image = left_image;
		this.right_image = right_image;
		handler.reset();
	}

	/**
	 * Metoda okreslajaca w jaki sposob i z jaka predkoscia ma poruszaa sie nasz obiekt
	 * Uaktywnia takze kolizje
	 */
	public void update() {


		colision();
		Luna_Lander.FuelLevel(fuel);
		//poruszanie
		if (handler.isUp()) {
			Sound.playSound("/engine.wav");
			velY = (float) -0.3;
			fuel = (float) (fuel - 0.5);
		} else if (!handler.isDown()) velY = 0;

		if (handler.isRight()) {
			Sound.playSound("/engine.wav");
			velX = -2;
			fuel = (float) (fuel - 0.1);
		} else if (!handler.isLeft()) velX = 0;

		if (handler.isLeft()) {
			Sound.playSound("/engine.wav");
			velX = 2;
			fuel = (float) (fuel - 0.1);
		} else if (!handler.isRight()) velX = 0;
		x += velX;
		vel = vel + gravitation - (-velY);
		y += vel;
//		System.out.println(vel);
		if((int)fuel<0){
			setIsCrashed(true);
		}

		x = Luna_Lander.clamp(x, 0, PropertiesLoader.getNum("game_width") - 60);
		y = Luna_Lander.clamp(y, -12, PropertiesLoader.getNum("game_height"));

	}

	/**
	 * Metoda odpowiedzialna za kolizje
	 * Jezli nasza rakieta dotknie inny obiekt natychmiast wywolywane jest jakies zdarzenie
	 */
	private void colision() {
		for (int i = 0; i < handler.object.size(); i++) {

			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Block) {

				if (tempObject.getBounds().intersects(getBoundsRect())) {
					setIsCrashed(true);
					Sound.playSound("/crash.wav");

				}
			}
			if (tempObject.getId() == ID.Airstrip) {
				if (tempObject.getBounds().intersects(getBoundsRect())) {
					if (vel < 2.5) {
						setIsLanded(true);
						Sound.playSound("/landing.wav");
					} else {
						setIsCrashed(true);
						Sound.playSound("/crash.wav");
					}
				}
			}
			if (tempObject.getId() == ID.SupplyCrate) {

				if (tempObject.getBoundsRect().intersects(getBoundsRect())) {
					Sound.playSound("/drink.wav");
					fuel = fuel + 30;
					handler.removeObject(tempObject);
				}
			}
			if (tempObject.getId() == ID.TimeSupply) {

				if (tempObject.getBoundsRect().intersects(getBoundsRect())) {
					Sound.playSound("/clock.wav");
					Luna_Lander.stopTime();
					handler.removeObject(tempObject);
				}
			}
			if (tempObject.getId() == ID.MealSupply) {

				if (tempObject.getBoundsRect().intersects(getBoundsRect())) {
					Sound.playSound("/eat.wav");
					Luna_Lander.setScore(20);
					handler.removeObject(tempObject);
				}
			}
		}
	}


	/**
	 * Odpowiada za zrenderowanie rakiety
	 */
	public void render(Graphics g) {
		Graphics2D g2d;
		g2d=Scaling(g);
		g2d.setColor(Color.blue);
		if(handler.isUp())
			g2d.drawImage(rocket2_image, x, y, null);
		else if (handler.isLeft())
			g2d.drawImage(left_image, x, y, null);
		else if (handler.isRight())
			g2d.drawImage(right_image, x, y, null);
		else		
			g2d.drawImage(rocket_image, x, y, null);
	}

	/**
	 * Metoda zwracajaca ksztalt oraz wymiary rakiety(potrzebna do kolizji)
	 */
	public Rectangle2D getBoundsRect() {
		Rectangle2D rect = new Rectangle(x, y, 60, 80);
		return rect;
	}

	@Override
	public Shape getBounds() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
