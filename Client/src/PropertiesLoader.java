import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
/**
 *Klasa odpowiedzialna za wczytanie pliku konfiguracyjnego oraz sparsowanie danych
 */
public class PropertiesLoader {
 	
	private static String info,i;
	private static int num;
	private static float numFloat;
	private static int[] intValues;
    static Properties p= new Properties(); 
	
    /**
     * Glowny konstruktor wczytujacy plik konfiguracyjny
     */
	public PropertiesLoader() throws Exception{
	InputStream is = new FileInputStream("data/dataConfig.properties");
	p.load(is);	
	}
	/**
	 * Getter zwracajacy tekst
	 * @param data Klucz, ktory pozwala nam odczytac potrzebna wartosc
	 * @return zwracana jest wartosc 
	 */
public static String getInfo(String data) {
	info = p.getProperty(data);
	return info;
}
/**
 * Getter zwracajacy liczbe typu int, ktora uprzednio zostaje skonwertowana z tekstu
 * @param data Klucz, ktory pozwala nam odczytac potrzebna wartosc
 * @return zwracana jest wartosc
 */
public static int getNum(String data) {
	i = p.getProperty(data);
	num = Integer.parseInt(i);
	return num;
}
/**
 * Getter zwracajacy liczbe typu float, ktora uprzednio zostaje skonwertowana z tekstu
 * @param data Klucz, ktory pozwala nam odczytac potrzebna wartosc
 * @return zwracana jest wartosc
 */
public static float getNumFloat(String data) {
	i = p.getProperty(data);
	numFloat = Float.parseFloat(i);
	return numFloat;
}
/**
 * Getter zwracajacy tablice zawierajaca liczby, ktore uprzednio zostaja skonwertowane z tekstu
 * @param data Klucz, ktory pozwala nam odczytac potrzebna wartosc
 * @return zwracana jest wartosc 
 */
public static int[] getTab(String data) {

	String[] strValues=p.getProperty(data).split(",");
	intValues = new int[strValues.length];
	for(int i=0;i<strValues.length;i++) {
	    intValues[i] = Integer.parseInt(strValues[i]);
	}
	
	return intValues;
}

}
