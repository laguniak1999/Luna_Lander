import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * Klasa odpowiedzialna za wczytanie obrazka
 *
 */
public class BufferedImageLoader {

	private BufferedImage image;
	/**
	 * Konstruktor wczytujacy obrazek
	 * @param path sciezka do obrazka 
	 * @return zwraca wczytany obrazek 
	 */
	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
