import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

/**
 * Klasa tworzaca menu oraz implementujaca jego obsluge
 *
 */
public class Menu extends JFrame implements ActionListener{

	
	private int WIDTH;
	private int HEIGHT;
	private static boolean Menu = true;

	JFrame MenuWindow = new JFrame();
	JFrame OnOFFlineWindow = new JFrame();
	JButton bnewgame, bhowtoplay, bscoreboard, bauthors, bexit,bBack, bnext, bOFFline, bOnline, bBacktoMenu;
	JTextArea text;
	JLabel lMariusz,lDamian, ltekst,lnicknamerules ;
	public static JTextField nickField;


	/**
	 * Glowny konstruktor tworzacy okno oraz przyciski
	 */
	public Menu()  {

		try {
			new PropertiesLoader();
		} catch (Exception e) {
			e.printStackTrace();
		}
		WIDTH = PropertiesLoader.getNum("menu_width");
		HEIGHT = PropertiesLoader.getNum("menu_height");
///////////////JPANELE///////////////////////////////////////
		setTitle(PropertiesLoader.getInfo("title"));
		JPanel bottom1 = new JPanel();
		setLayout(new GridLayout(5,1));
		bottom1.setPreferredSize(new Dimension(WIDTH,HEIGHT/5));
		bottom1.setLayout(new GridLayout(1,1));

		JPanel bottom2 = new JPanel();
		bottom2.setLayout(new GridLayout(1,1));

		JPanel bottom3 = new JPanel();
		bottom3.setLayout(new GridLayout(1,1));

		JPanel bottom4 = new JPanel();
		bottom4.setLayout(new GridLayout(1,1));

		JPanel bottom5 = new JPanel();
		bottom5.setLayout(new GridLayout(1,1));

		// ///////////PRZYPISANIE NAZW GUZIKï¿½W///////////////////////////////
		bnewgame = new JButton(PropertiesLoader.getInfo("new_game"));
		bhowtoplay = new JButton(PropertiesLoader.getInfo("how_to_play"));
		bscoreboard = new JButton(PropertiesLoader.getInfo("scoreboard"));
		bauthors = new JButton(PropertiesLoader.getInfo("authors"));
		bexit = new JButton(PropertiesLoader.getInfo("exit"));


		////////////////////////Przypisywanie guzika do panelu//////////
		bottom1.add(bnewgame);
		bottom2.add(bhowtoplay);
		bottom3.add(bscoreboard);
		bottom4.add(bauthors);
		bottom5.add(bexit);

		////////////////DODAWANIE PANELU DO OKNA//////////////////
		add(bottom1);
		add(bottom2);
		add(bottom3);
		add(bottom4);
		add(bottom5);

		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		bnewgame.addActionListener(this);
		bhowtoplay.addActionListener(this);
		bscoreboard.addActionListener(this);
		bauthors.addActionListener(this);
		bexit.addActionListener(this);

	}
	/**
	 * Metoda odpowiadajaca za reakcje, gdy zostanie wcisniety jakis przycisk
	 */
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();

		if(object == bnewgame){
			dispose();
			OnOffline();
		}

		if(object == bOnline){
			OnOFFlineWindow.dispose();
			NewGame();
			Main.setOnline(true);
		}
		if (object== bOFFline){
			OnOFFlineWindow.dispose();
			Menu=false;
			Main.setOnline(false);
			try {
				Main.main(null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}


		else if(object == bnext){
			String playerName = nickField.getText();
			if(playerName.isEmpty() || nickField.getText().length()>10){
				lnicknamerules.setText("*nickname length: 0-10 characters");
				Sound.playSound("/err.wav");
			}
			else {
				MenuWindow.dispose();
				Menu=false;
				try {
					Main.main(null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		else if (object== bhowtoplay){
			try {
				dispose();
				HowToPlay();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (object== bscoreboard){
			dispose();
			try {
				Scoreboard();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (object== bauthors){
			dispose();
			AuthorsWindow();
		}
		else if (object== bexit){
			dispose();
		}
		else if (object == bBack) {
			MenuWindow.dispose();
			new Menu();
		}
		else if (object == bBacktoMenu){
			OnOFFlineWindow.dispose();
			new Menu();
		}
	}
	/**
	 * Metoda tworzaca nowe okno po tym jak wcisniemy nowa gra
	 */
	public void NewGame(){
		MenuWindow.setSize(300, 400);
		MenuWindow.setTitle(PropertiesLoader.getInfo("Lunar Lander"));
		bnext = new JButton("Next");
		bBack = new JButton("Back");
		
		ltekst = new JLabel("Enter the nickname");
		ltekst.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 25));
		ltekst.setBounds(5, 10, 400, 30);

		nickField = new JTextField("");
		nickField.setBounds(10,50,260,30);

		lnicknamerules = new JLabel();
		lnicknamerules.setBounds(10,40,200,100);
		lnicknamerules.setForeground(Color.RED);
		lnicknamerules.setFont(new Font("sanserif", Font.BOLD,10));

		bnext.setBounds(30, 250, 220, 50);
		bBack.setBounds(30,300,220,50);

		MenuWindow.add(ltekst);
		MenuWindow.add(bnext);
		MenuWindow.add(bBack);
		MenuWindow.add(nickField);
		MenuWindow.add(lnicknamerules);
		MenuWindow.setLayout(null);
		MenuWindow.setLocationRelativeTo(null);
		MenuWindow.setResizable(false);
		MenuWindow.setDefaultCloseOperation(3);
		MenuWindow.setVisible(true);

		bnext.addActionListener(this);
		bBack.addActionListener(this);
		nickField.addActionListener(this);
	}
	/**
	 * Metoda tworzaca okno z wyborem trybu gry
	 */
	public void OnOffline(){
		OnOFFlineWindow.setSize(300, 400);
		OnOFFlineWindow.setTitle("Lunar Lander");
		bOnline = new JButton("Play Online");
		bOFFline = new JButton("Play Offline");
		bBacktoMenu = new JButton("Back");

		bOnline.setBounds(30, 50, 220, 50);
		bOFFline.setBounds(30,120,220,50);
		bBacktoMenu.setBounds(30,190,220,50);

		OnOFFlineWindow.add(bOnline);
		OnOFFlineWindow.add(bOFFline);
		OnOFFlineWindow.add(bBacktoMenu);
		OnOFFlineWindow.setLayout(null);
		OnOFFlineWindow.setLocationRelativeTo(null);
		OnOFFlineWindow.setResizable(false);
		OnOFFlineWindow.setDefaultCloseOperation(3);
		OnOFFlineWindow.setVisible(true);

		bOnline.addActionListener(this);
		bOFFline.addActionListener(this);
		bBacktoMenu.addActionListener(this);
	}
	/**
	 * Metoda tworzaca okno z instrukcja
	 */
	public void HowToPlay() throws IOException {

		MenuWindow.setTitle(PropertiesLoader.getInfo("how_to_play"));
		MenuWindow.setVisible(true);
		MenuWindow.setLayout(null);
		MenuWindow.setDefaultCloseOperation(3);
		MenuWindow.setSize(315,400);
		MenuWindow.setResizable(false);
		MenuWindow.setLocationRelativeTo(null);


		bBack = new JButton(PropertiesLoader.getInfo("back"));
		bBack.setBounds(0, 300, 300, 60);
		MenuWindow.add(bBack);
		bBack.addActionListener(this);
		text=new JTextArea();
		text.setSize(300,300);
		text.setLineWrap(true); //To robi ï¿½e tekst nie wychodzi poza ramkï¿½
		text.setWrapStyleWord(true); // to robi ï¿½e nie ucina sï¿½ï¿½w
		text.setFont(new Font("nwm", Font.BOLD, 16));
		text.append(PropertiesLoader.getInfo("instruction"));
		text.setEditable(false);
		MenuWindow.add(text);

	}
	/**
	 * Metoda tworzaca okno z autorami projektu
	 */
	public void AuthorsWindow() {
		MenuWindow.setSize(300, 400);
		MenuWindow.setTitle(PropertiesLoader.getInfo("authors"));
		MenuWindow.setLayout(null);
		MenuWindow.setResizable(false);
		MenuWindow.setLocationRelativeTo(null);


		bBack = new JButton(PropertiesLoader.getInfo("back"));
		bBack.setBounds(170, 300, 80, 40);
		MenuWindow.add(bBack);
		bBack.addActionListener(this);

		lDamian = new JLabel(PropertiesLoader.getInfo("author1"));
		lDamian.setBounds(30, 50, 250, 100);
		lDamian.setForeground(Color.BLACK);                            //Ustawia kolor etykiety
		lDamian.setFont(new Font("sanserif", Font.BOLD, 30));
		MenuWindow.add(lDamian);

		lMariusz = new JLabel(PropertiesLoader.getInfo("author2"));
		lMariusz.setBounds(15, 100, 260, 100);
		lMariusz.setForeground(Color.BLACK);                            //Ustawia kolor etykiety
		lMariusz.setFont(new Font("sanserif", Font.BOLD, 30));
		MenuWindow.add(lMariusz);

		MenuWindow.setDefaultCloseOperation(3);
		MenuWindow.setVisible(true);

	}
	/**
	 * Metoda tworzy oraz wyswietla okno z najlepszymi wynikami
	 * @throws IOException
	 */
	public void Scoreboard() throws IOException{
		if(Main.getSocket()!=null) {
		MenuWindow.setTitle("Best results");
		MenuWindow.setVisible(true);
		MenuWindow.setLayout(null);
		MenuWindow.setDefaultCloseOperation(3);
		MenuWindow.setSize(315,400);
		MenuWindow.setResizable(false);
		MenuWindow.setLocationRelativeTo(null);


		bBack = new JButton(PropertiesLoader.getInfo("back"));
		bBack.setBounds(0, 300, 300, 60);
		MenuWindow.add(bBack);
		bBack.addActionListener(this);
		text=new JTextArea();
		text.setSize(300,300);
		text.setLineWrap(true); //To robi ï¿½e tekst nie wychodzi poza ramkï¿½
		text.setWrapStyleWord(true); // to robi ï¿½e nie ucina sï¿½ï¿½w
		text.setFont(new Font("oki", Font.BOLD, 16));
		Main.getRecords();
		Records.loadRecords();
		for(int i=0;i<10;i++)
		text.append(Records.getScores(i)+ "\n");
		text.setEditable(false);
		
		MenuWindow.add(text);}
		else {
			 JOptionPane.showMessageDialog(null, "Server is offline");
			   }
		

	}


	/**
	 * Getter odpowiedzialny za przekazanie informacji czy menu jest wlaczone czy wylaczone
	 */
	public static boolean isMenu() {
		return Menu;
	}
	/**
	 * Setter odpowiedzialny za ustawienie informacji czy menu jest wlaczone czy wylaczone
	 */
	public static void setMenu(boolean menu) {
		Menu = menu;
	}
	
    /**
     * Metoda pobieraj¹ca nick gracza, potrzebna do zapisania wyniku
     * @return nick
     */
	public static String getPlayerName(){
		String name=null;
		name = nickField.getText();
		return name;
	}


}