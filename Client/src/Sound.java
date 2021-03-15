import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Klasa odpowiedzialna za dzwiek w grze
 *
 */
public class Sound {

	/**
	 * Metoda tworzaca watek, ktory odtwarza dzwiek
	 * @param url sciezka do pliku z dzwiekiem
	 */
    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(url));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();
    }
}
