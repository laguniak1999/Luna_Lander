import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
/**
 * Klasa rozpatruj¹ca zadanie, oraz implementuje funkcje tablicy wynikow
 *
 */
public class ServerCommands {
	/**
	 * Metoda odpowiedzialna za odeslanie clientowi tego co zazada
	 * @param command zadanie, ktore przeslal client
	 * @return odpowiedz serwera
	 * @throws IOException
	 */
	public static String serverAction(String command) throws IOException{
		String serverMessage;
		String command2=command;
		String[] commands = command.split("-");
		command = commands[0];
		switch (command) {
		case "GET_CONFIG":
            serverMessage = getConfig();
            break;
        case "GET_RECORDS":
        	serverMessage = getRecords();
        	break;
        case "SEND_SCORE":
        	serverMessage = Send_Score(command2.replace("SEND_SCORE-",""));
        	break;
        default: 
        	serverMessage="INVALID_COMMAND";
        }
    return serverMessage;
    }
	/**
	 * Metoda pobierajaca ustawienia z serwera 
	 * @return linie tekstu, z ustawieniami gry
	 * @throws IOException
	 */
	public static String getConfig() throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader("data/dataConfig.properties"));
		String currentLine;
		while ((currentLine = br.readLine()) != null) {
			sb.append(currentLine);
			sb.append("@");
		}
	return sb.toString();	
	}
	/**
	 * Metoda wysylajaca liste rekordow clientowi
	 * @return linie tekstu, z rekordami
	 * @throws IOException
	 */
	public static String getRecords() throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader("data/Records.properties"));
		String currentLine;
		while ((currentLine = br.readLine()) != null) {
			sb.append(currentLine);
			sb.append(" ");
		}
	return sb.toString();	
	}
	
	/**
	 * Metoda zapisujaca nowy wynik na serwerze
	 * @param path informacja o nazwie gracza oraz wyniku
	 * @return
	 * @throws IOException
	 */
	private static String Send_Score(String path) throws IOException {
		ArrayList<UserScore> scorelist;
		scorelist = new ArrayList<>();
		scorelist = loadRecords(scorelist);
		String []fromclient=path.split("-");
		scorelist.add(new UserScore(fromclient[0],Integer.parseInt(fromclient[1])));
		sortRecords(scorelist);
		saveRecords(scorelist);
		
		return "Your score is save";
	}
	/**
	 * Metoda wczytujaca liste wynikow z pliku oraz zapisujaca je w liscie
	 * @param scorelist lista wynikow
	 * @return liste wynikow
	 * @throws IOException
	 */
	private static ArrayList<UserScore> loadRecords(ArrayList<UserScore> scorelist) throws IOException{
		FileReader reader = new FileReader("data/Records.properties");
		Properties p = new Properties();
		p.load(reader);
		String tempUserScore;
		for (int i = 1; i<=10; i++) {
			tempUserScore = p.getProperty("place"+i);
			String[] pair = tempUserScore.split(",");
			scorelist.add(new UserScore(pair[0],Integer.parseInt(pair[1])));
			System.out.println(pair[0]+" "+pair[1]);
		}
	    return scorelist;
	}
	/**
	 * Metoda odpowiedzialna za posortowanie wynikow
	 * @param scorelist lista wynikow
	 */
	private static void sortRecords(ArrayList<UserScore> scorelist) {
		Collections.sort(scorelist,  (user1, user2) -> {
			if(user1.getScore() < user2.getScore()){
			return 1;
		    }
			if (user1.getScore() > user2.getScore()) {
			return -1;
			}
			else return 0;
		});
	}
	/**
	 * Metoda zapisujaca wyniki na serwerze
	 * @param scorelist lista wynikow
	 * @throws FileNotFoundException
	 */
	private static void saveRecords(ArrayList<UserScore> scorelist) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("data/Records.properties");
		for(int i=0;i<10;i++) {
			int j=i+1;
			out.println("place"+j+"="+scorelist.get(i).getName()+","+scorelist.get(i).getScore());
		}
		out.close();
	}

}
