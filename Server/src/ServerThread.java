import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Klasa, która obs³uguje w¹tek
 * Odpowiada za relacje serwer-client
 *
 */
public class ServerThread implements Runnable {
	private Socket socket;
	public ServerThread(Socket socket) {
		this.socket=socket;
	}

	/**
	 * Metoda run, otrzymuje ¿¹danie od klienta nastêpnie jest rozpatruje w klasie serverCommands a potem odsyla wiadomosc zwrotna clientowi
	 */
	public void run() {	
			try {
				while(true) {
				    InputStream inputStream = socket.getInputStream();
				    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	                OutputStream outputStream = socket.getOutputStream();
	                PrintWriter printWriter = new PrintWriter(outputStream, true);
	                String fromClient = bufferedReader.readLine();
	               
	                if(fromClient !=null) {
	                	System.out.println("From client: " + fromClient);
	                	String serverRespond = ServerCommands.serverAction(fromClient);
	                	printWriter.println(serverRespond+"\n");
	                    printWriter.flush();
	                    System.out.println("Server respond: " + serverRespond);
				    }	
			    } 
			}
			catch (IOException e) {
				System.out.println("Connection lost");
			}
			
		
	}

}
