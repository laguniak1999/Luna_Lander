import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
/**
 * Klasa server
 *
 */
public class Server {
	private static int port;
	
	/**
	 * metoda runServer tworzy serversocket oraz ropoczyna nowy w¹tek od danego socketu
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Server runServer() throws IOException, InterruptedException {
		getServerInfo();
		ServerSocket serverSocket = new ServerSocket(port);
		while(true) {
			Socket socket = serverSocket.accept();
			new Thread(new ServerThread(socket)).start();
		}	
	}
	/**
	 * Metoda pobierajaca informacje na temat serwera
	 * @throws IOException
	 */
	public static void getServerInfo() throws IOException {
		Properties p = new Properties();
		InputStream is = new FileInputStream("port.properties");
		p.load(is);	
		port=Integer.parseInt(p.getProperty("port"));
	}
	
  
}