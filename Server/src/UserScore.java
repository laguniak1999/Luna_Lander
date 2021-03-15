/**
 * Klasa przechowujaca imie oraz wynik gracza
 *
 */
public class UserScore {

	private String name;
	private int score;
	
	public UserScore(String name,int score) {
		this.name=name;
		this.score=score;
	}

	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}
	
}
