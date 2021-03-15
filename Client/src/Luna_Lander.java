import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;

import javax.swing.*;

/**
 * Glowna klasa Main odpowiada za aktywacje Menu jak i gry,a takze kontroluje framerate
 *
 */
public class Luna_Lander extends Canvas implements Runnable, ActionListener {
	private final int WIDTH, HEIGHT;
	private final int FRAMERATE=PropertiesLoader.getNum("framerate");
	private static int w,h;
	private static Socket socket;

	private int level;
	private int health;
	private float gravitation;
	private static float fuel;
	private static int time=0,score=0;
	private JFrame frame;
	private boolean RUNNING = false;
	private Handler handler;
	private BufferedImage rocket_image = null;
	private BufferedImage rocket2_image = null;
	private BufferedImage crate_image = null;
	private BufferedImage left_image = null;
	private BufferedImage right_image = null;
	private BufferedImage hourglass_image = null;
	private BufferedImage meal_image = null;
	public static boolean paused = true,timepaused=false;
	
	public static int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public static int getH() {
		return h;
	}

	public void Luna_Lander(int h) {
		this.h = h;
	}

	BufferedImageLoader loader;
	
	JPanel north,south,north3;
	static JProgressBar bBar;
	JLabel lFuel, lLevel, lTime,lScore;
	JButton bPause;
	/**
	 * Glowny konstruktor
	 * tworzy okno gry, jak i rowniez panel znajdujacy sie w grze
	 * odpowiada za aktywacje klawiatury oraz zaladowanie odpowiedniego poziomu
	 * @param socket jest to serwerowy socket umo¿liwiaj¹cy komunikacje client-server
	 * @throws IOException 
	 */
	public Luna_Lander(Socket socket) throws IOException {
		this.socket=socket;
		WIDTH = PropertiesLoader.getNum("game_width");
	    HEIGHT = PropertiesLoader.getNum("game_height");
		frame = new JFrame();
		
		frame.setTitle(PropertiesLoader.getInfo("title"));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		// ustawiamy szerokosc oraz wysokosc naszego canvas
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// zamkniecie okna powoduje zatrzymanie programu
		frame.add(this, new BorderLayout().CENTER);
		// ustawiamy naszego canvasa
		frame.pack();
		// inicjalizacja powyzszych ustawien
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		// ustawienie okna na srodku ekranu
	    
	    north= new JPanel(new BorderLayout());
	    south= new JPanel(new BorderLayout());
		bBar = new JProgressBar();
		lFuel = new JLabel();
		lLevel = new JLabel();
        lTime = new JLabel();
        lScore = new JLabel();
		
		bBar.setMaximum(100);		
		bBar.setStringPainted(true);
		
		lTime.setText("Time: ");
		 
		lLevel.setText("Level: " + level);
		
		lScore.setText("Score:");
		 
		lFuel.setText("                    Fuel");
		
		
		frame.add(north,BorderLayout.NORTH);
		north.add(south,BorderLayout.SOUTH);
		north.add(bBar, BorderLayout.WEST);
		lTime.setHorizontalAlignment(JLabel.CENTER);
		south.add(lTime, BorderLayout.CENTER);
		south.add(lScore, BorderLayout.EAST);
		north.add(lLevel,BorderLayout.LINE_END);
		south.add(lFuel,BorderLayout.LINE_START);

		bPause = new JButton("Pause");
		north.add(bPause,BorderLayout.CENTER);
		bPause.addActionListener(this);

		frame.pack();
		frame.setVisible(true);
				
		handler = new Handler();
		this.addKeyListener(new Keyboard(handler));
		loader = new BufferedImageLoader();
		rocket_image = loader.loadImage("/Rakieta.png");
		rocket2_image = loader.loadImage("/Rakieta2.png");
		left_image = loader.loadImage("/left.png");
		right_image = loader.loadImage("/right.png");
		crate_image = loader.loadImage("/karnister.png");
		hourglass_image = loader.loadImage("/klepsydra.png");
		meal_image = loader.loadImage("/burger.png");
		
		health=3;
		level=1;
        loadLevel(level);
        
        w=frame.getWidth();
        h=frame.getHeight();	
        start();
	}
	
	/**
	 * Odpowiada za rozpoczecie gry, poprzez zmiane parametru RUNNING
	 * Tworzy nowy watek
	 * oraz wywoluje metode run
	 */
	private void start() {
		if (RUNNING)
			return;
		else
			RUNNING = true;

		new Thread(this, "Game " + PropertiesLoader.getInfo("title")).start();
		// wywoluje funkcje run
	}
	/**
	 * stopuje program poprzez zmiane parametru RUNNING
	 */
	private void stop() {
		if (!RUNNING)
			return;
		else
			RUNNING = false;
		frame.dispose();
		//System.exit(0);
	}
//wylacza program

	private double timerek = System.currentTimeMillis();
	private int FPS = 0;
	private int UPS = 0;
	private double delta;
	private double frametime = 1000000000.0 / FRAMERATE;
	private long timeNOW = System.nanoTime();
	private long timeLAST = System.nanoTime();
	private int temptime=0;
	/** 
	 * Glowna metoda obslugujaca gre
	 * Ustawia odpowiedni framerate(ograniczenie klatek na sekunde)
	 * Mierzy fps'y
	 * Oraz aktywuje metody render i update
	 */
	public void run() {
	this.requestFocus();
		while (RUNNING) {
			
			timeNOW = System.nanoTime();
			delta += (timeNOW - timeLAST) / frametime;
			timeLAST = System.nanoTime();

			while (delta >= 1) {
				if(!paused&&!timepaused) {
					time++;
					temptime=0;
				}
				if(!paused&&timepaused==true) {
					System.out.println("cos");
				    temptime+=1;
			    }
				if(temptime==120)
				timepaused=false;
				lLevel.setText("Level:"+level);
			    lTime.setText("Time:" +time/60 );
				lScore.setText("Score:"+ score);
				update();
				delta -= 1;
				UPS++;
			}
			// ograniczenie klatek na skunde
			render();
			FPS++;

			if (System.currentTimeMillis() - timerek >= 1000) {
				timerek = System.currentTimeMillis();
				//System.out.println(PropertiesLoader.getInfo("title") + "   FPS: " + FPS + "   UPS: "+UPS);
				FPS = 0;
				UPS = 0;
			}
		}

		stop();
	}
	/**
	 * Odpowiada za wszystkie kolizje oraz sterowanie
	 */
	private void update() {
		if(!paused) {
			handler.update();
			for(int i=0; i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.id==ID.Rocket) {
					if(tempObject.getIsLanded()==true) {
						paused = true;
						if (level!=3)
						    LANDING();
						else
							GAME_OVER();
					}	
					if(tempObject.getIsCrashed()==true) {
						paused = true;					
						health--;
						
						if (health == 0)
					    GAME_OVER();
						else {
						CRASH();}
					}
			}
			
		}
		}
		
		
		
	}
	/**
	 * Tworzy 3 bufory (usprawnia plynnosc gry, podczas gdy jeden bufor jest updatowany, wczesniejszy jest wyswietlany)
	 * Graphics pobiera odpowiedni bufor po czym jest on updatowany 
	 * Metoda renderuje obrazki oraz obrazy
	 */
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		w=frame.getWidth();
        h=frame.getHeight();
        Graphics2D g2d;
        g2d=Scaling(g);
	    g2d.setColor(new Color(40, 115, 115));
	    int x=1024;
	    int y=576;
	    g2d.fillRect(0, 0, x, y);
	    handler.render(g);
		g.dispose();
		bs.show();
	}
	public Graphics2D Scaling(Graphics g) {
		int Width = Luna_Lander.getW();
		int Height = Luna_Lander.getH();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveTransform = g2d.getTransform();
	    AffineTransform scaleMatrix = new AffineTransform();
	    float sx =(1f+(Width-1040)/(float)1040);
	    float sy =(1f+(Height-647)/(float)647);
	    scaleMatrix.scale(sx, sy);
	    g2d.setTransform(scaleMatrix);
		return g2d;
	}
	/**
	 * Metoda ograniczajaca pole poruzania sie rakiety
	 */
	public static int clamp (int var, int min, int max){
		if (var >= max) return var = max;
		else if (var <= min) return var = min;
		else return var;
	}
	/**
	 * Metoda wczytujlca poziom
	 * Dodaje obiekty do gry
	 * @param level liczba okreslajaca level
	 */
	private void loadLevel(int level) {
		gravitation= PropertiesLoader.getNumFloat("gravitation"+level);
		int s_coordinate_x=PropertiesLoader.getNum("s_coordinate_x"+level);
		int s_coordinate_y=PropertiesLoader.getNum("s_coordinate_y"+level);
		int s_coordinate_xx=PropertiesLoader.getNum("s_coordinate_x"+level+level);
		int s_coordinate_yy=PropertiesLoader.getNum("s_coordinate_y"+level+level);
		int [] block_cords_x= PropertiesLoader.getTab("x"+level);
		int [] block_cords_y= PropertiesLoader.getTab("y"+level);
		int n= PropertiesLoader.getNum("n"+level);
		int [] strip_cords_x= PropertiesLoader.getTab("airstrip_x"+level);
		int [] strip_cords_y= PropertiesLoader.getTab("airstrip_y"+level);
		int m= PropertiesLoader.getNum("m"+level);	
		int crate_coordinate_x=PropertiesLoader.getNum("crate_coordinate_x"+level);
		int crate_coordinate_y=PropertiesLoader.getNum("crate_coordinate_y"+level);
		int hourglass_coordinate_x=PropertiesLoader.getNum("hourglass_coordinate_x"+level);
		int hourglass_coordinate_y=PropertiesLoader.getNum("hourglass_coordinate_y"+level);
		int meal_coordinate_x=PropertiesLoader.getNum("meal_coordinate_x"+level);
		int meal_coordinate_y=PropertiesLoader.getNum("meal_coordinate_y"+level);
        handler.addObject(new Block(block_cords_x,block_cords_y,n,s_coordinate_x,s_coordinate_y, ID.Block));
		handler.addObject(new Airstrip(strip_cords_x,strip_cords_y,m,s_coordinate_x,s_coordinate_y, ID.Airstrip));
		handler.addObject(new TimeSupply(hourglass_coordinate_x,hourglass_coordinate_y, ID.TimeSupply, handler, hourglass_image,gravitation));
		handler.addObject(new SupplyCrate(crate_coordinate_x,crate_coordinate_y, ID.SupplyCrate, handler, crate_image,gravitation));
		handler.addObject(new MealSupply(meal_coordinate_x,meal_coordinate_y, ID.MealSupply, handler, meal_image,gravitation));
		handler.addObject(new Rocket(s_coordinate_xx,s_coordinate_yy, ID.Rocket, handler, rocket_image,rocket2_image,left_image, right_image, gravitation));
		
	}
	/**
	 * Metoda pobiera ilosc paliwa z rakiety oraz ustawia wartosc na pasku
	 * @param fuel ilosc paliwa w statku
	 */
	public static void FuelLevel(float fuel) {
		Luna_Lander.fuel=fuel;
		bBar.setValue((int) fuel);
	}
	/**
	 * Metoda odpowiedzialna za zliczanie punktów
	 * @param health pozostale zycia
	 * @param time czas lotu
	 * @param gravitation wspolczynnik grawitacji pobierany z danej planety
	 * @param fuel pozostale paliwo
	 * @return metoda zwraca sumaryczna ilosc punktow
	 */
	public int Totalpoints(int health, int time, float gravitation, float fuel){
		return (health*10)-time+150*(int)gravitation+2*(int)fuel;
	}

	/**
	 * Metoda odpowiadajaca za reakcje, gdy zostanie wcisniety przycisk
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object== bPause) {
			if(Luna_Lander.paused) Luna_Lander.paused = false;
			else Luna_Lander.paused = true;
			this.requestFocus();

		}
		if(object == btryagain){
			Menu.setMenu(false);
			handler.object.clear();
			loadLevel(level);
			time=0;
			Window.dispose();
			Window.getContentPane().removeAll();
			}
		
		if(object == bexit){
			stop();
			Window.dispose();
			Window.getContentPane().removeAll();
			Menu.setMenu(true);
			try {
				Main.main(null);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if(object == bnextlevel){
			System.out.println("COS");
			level++;
			time=0;
			Window.dispose();
			handler.object.clear();
			Window.getContentPane().removeAll();
			loadLevel(level);
			bnextlevel.removeActionListener(this);
		}
	}
	JFrame Window = new JFrame();
	JLabel Jinfo = new JLabel();
	JLabel Jscoreinfo = new JLabel();
	JButton btryagain = new JButton();
	JButton bnextlevel = new JButton();
	JButton bexit = new JButton();
	/**
	 * Metoda odpowiedzialna za utworzenie okna, gdy nasz statek wybuchnie
	 */
	public void CRASH() {
		Window.setSize(300, 400);
		Window.setTitle(PropertiesLoader.getInfo("Lunar Lander"));

		Jinfo = new JLabel("Landing failed!!");
		Jinfo.setForeground(Color.RED);
		Jinfo.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 20));
		Jinfo.setBounds(10, 10, 400, 30);

		Jscoreinfo = new JLabel("Your score is: " + score);
		Jscoreinfo.setForeground(Color.GREEN);
		Jscoreinfo.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 25));
		Jscoreinfo.setBounds(10, 50, 400, 30);

		btryagain.setBounds(30, 300, 220, 50);
		btryagain.setText("Try again");
		btryagain.addActionListener(this);

		Window.add(Jinfo);
		Window.add(Jscoreinfo);
		Window.add(btryagain);
		Window.setBackground(Color.BLACK);
		Window.setLayout(null);
		Window.setResizable(false);
		Window.setLocationRelativeTo(null);
		Window.setDefaultCloseOperation(3);
		Window.setVisible(true);

	}
	/**
	 * Metoda odpowiedzialna za utworzenie okna, gdy nasz statek wyladuje
	 */
	public void LANDING() {
		score=score+Totalpoints(0,time/60 ,gravitation ,fuel);
		Window.setSize(300, 400);
		Window.setTitle(PropertiesLoader.getInfo("Lunar Lander"));

		Jinfo =new JLabel("Landing successful!!");
		Jinfo.setForeground(Color.GREEN);
		Jinfo.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 23));
		Jinfo.setBounds(10, 10, 400, 30);

		Jscoreinfo = new JLabel("Your score is: "+score);
		Jscoreinfo.setForeground(Color.GREEN);
		Jscoreinfo.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 25));
		Jscoreinfo.setBounds(10, 50, 400, 30);

		bnextlevel.setBounds(30, 300, 220, 50);
		bnextlevel.setText("Next Level!");
		bnextlevel.addActionListener(this);

		Window.add(Jinfo);
		Window.add(Jscoreinfo);
		Window.add(bnextlevel);
		Window.setLayout(null);
		Window.setResizable(false);
		Window.setLocationRelativeTo(null);
		Window.setDefaultCloseOperation(3);
		Window.setVisible(true);
	}
	/**
	 * Metoda odpowiedzialna za utworzenie okna, gdy gra sie skonczy
	 */
	public void GAME_OVER() {
		if(health!=0)
		score=score+Totalpoints(health,time/60 ,gravitation ,fuel);
		Window.setSize(300, 400);
		Window.setTitle(PropertiesLoader.getInfo("Lunar Lander"));

		Jinfo = new JLabel("GAME OVER!!");
		Jinfo.setForeground(Color.BLUE);
		Jinfo.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 23));
		Jinfo.setBounds(10, 10, 400, 30);

		Jscoreinfo = new JLabel("Your score is: "+score);
		Jscoreinfo.setForeground(Color.GREEN);
		Jscoreinfo.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 25));
		Jscoreinfo.setBounds(10, 50, 400, 30);

		bexit.setBounds(30, 300, 220, 50);
		bexit.setText("EXIT");
		bexit.addActionListener(this);

		Window.add(Jinfo);
		Window.add(Jscoreinfo);
		Window.add(bexit);
		Window.setLayout(null);
		Window.setResizable(false);
		Window.setLocationRelativeTo(null);
		Window.setDefaultCloseOperation(3);
		Window.setVisible(true);
		if(socket!=null) {
		try {
			Send_scores();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}

	}
	/**
	 * Metoda wysylajaca zadanie do serwera, aby ten zapisal wynik
	 * @throws IOException
	 */
	private void Send_scores() throws IOException {
		OutputStream os = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(os,true);
		pw.println("SEND_SCORE-"+Menu.getPlayerName()+"-"+score);
		InputStream is = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String settings = br.readLine();
	}

	/**
	 * Getter wyniku
	 * @return wynik
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * Setter wykorzystywany do zmiany bierzacego wyniku
	 * @return wynik
	 */
	public static void setScore(int score) {
		Luna_Lander.score +=score;
	}
	/**
	 * Metoda odpowiedzialna za zatrzymanie czasu rozgrywki
	 */
	public static void stopTime() {
		timepaused=true;
	}

	

}
