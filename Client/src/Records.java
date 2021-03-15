import java.util.Properties;
import java.io.*;
import java.util.*;
/**Klasa przechowujaca liste najlepszych wynikow
*/
public class Records {
    
	private static String [] nick=new String[10];
	private static int [] scores = new int[10];
	
	/**
	 * Metoda wczytujaca liste najlepszych wynikow, ktore umieszcza w dwoch tablicach
	 * @throws IOException
	 */
	public static void loadRecords() throws IOException {
		FileReader reader= new FileReader("data/Records.properties");
		Properties p= new Properties();
		p.load(reader);
		String nickScore;
		for (int i=1;i<=10;i++) {
			nickScore=p.getProperty("place"+i);
			
			String[] pair = nickScore.split(",");
			for(int j=0; j<pair.length;j++) {
				nick[i-1]=pair[0];
				scores[i-1]= Integer.parseInt(pair[1]);
			}
		}
	}
	/**
	 * Getter pobierajacy wyniki
	 * @param i wynik z ktorego miejsca tablicy
	 * @return zwraca nick oraz wynik
	 */
	public static String getScores(int i) {
		return nick[i]+" "+scores[i];
	}
    
}