import java.io.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import javax.swing.JOptionPane;
/**
 * G³ówna klasa Main, laczy klienta z serverem, pobiera odpowiednie ustawienia, oraz odpala gre
 *
 */
public class Main {
	private static String ipAddress;
	private static int port;
	private static Socket socket;	
	private static boolean online=true;
	/**
	 * Metoda main
	 * Odpowiada za utworzenie gry albo menu, w zaleznosci od statycznego parametru znajdujacego sie w klasie Menu
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		if(Menu.isMenu()==true) {
			connectToServer();
			if(online==true)
			getConfig();
			new Menu();
		}
		if(Menu.isMenu()==false) {
		run();
		}
	    }
	/**
	 * Metoda tworzaca socket, ktory jest polaczeniem z serwerem 
	 */
	public static void connectToServer() {
		 try {
			    getServerInfo();
	            socket = new Socket(ipAddress, port);
	        }
	        catch (Exception e) {
	        	online=false;
	            System.out.println("Connection could not be opened..");
	            System.out.println("error: "+e);
	        }

	}
	/**
	 * Metoda odpowiedzialna za rozpoczecie gry, w zaleznosci od tego czy chcemy gre online czy offline
	 * @throws IOException
	 */
	public static void run() throws IOException {
		if(online==true) { 
			if (socket!=null) {
			new Luna_Lander(socket);
		    }
		    else {
			    Object[] options= {
					"PLAY","EXIT"
			    };
			    switch(JOptionPane.showOptionDialog(null, "Not connected to server. Do you want to play offline?", "Access denied", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0])) {
			    case JOptionPane.YES_OPTION:
				   new Luna_Lander(null);
				    break;
				
			    case JOptionPane.WARNING_MESSAGE:
				    System.exit(0);
				    break;
			    default:
				    break;
			    }
		   }
		}
		else {
			new Luna_Lander(null);
		}
		
	}
	/**
	 * Metoda odpowiedzialna za pobranie ustawien z serwera
	 */
	public static void getConfig(){
		 try{
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os, true);
		pw.println("GET_CONFIG");
		InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		PrintWriter out = new PrintWriter("data/dataConfig.properties");		
		String settings = br.readLine();
		String[] setting = settings.split("@");
		for(int i=0;i<setting.length;i++) {
			out.println(setting[i]);
		}
		out.close();
	        }catch (IOException e){
	            System.out.println("Access denied");
	            System.out.println(e);
	        }

	}
	/**
	 * Metoda odpowiedzialna za pobranie listy najlepszych wynikow z serwera
	 */
	public static void getRecords(){
		try {
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os,true);
		pw.println("GET_RECORDS");
		InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String settings = br.readLine();
		String[] setting = settings.split(" ");
		PrintWriter out = new PrintWriter("data/Records.properties");
		for(int i=0;i<setting.length;i++) {
			out.println(setting[i]);
		}
		out.close();
    }catch (IOException e){
        System.out.println("Access denied");
        System.out.println(e);
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
		ipAddress=p.getProperty("ipAddress");
	}
	/**
	 * Getter sprawdzajacy czy chcemy grac online
	 * @return true albo false
	 */
    public static boolean isOnline() {
	    return online;
    }
    /**
	 * Setter ustawiajacy serwer na online
	 *
	 */
    public static void setOnline(boolean online) {
	    Main.online = online;
    }
    public static Socket getSocket() {
    	return socket;
    }
}

