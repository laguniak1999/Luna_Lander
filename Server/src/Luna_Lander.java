import java.io.IOException;
/**
 * G³ówna klasa serwera, posiadaj¹ca metode main
 *
 */
public class Luna_Lander {
	/**
	 * Metoda main odpowiedzialna za utworzenie serwera
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		Server server = new Server().runServer();
	}
	
	
}