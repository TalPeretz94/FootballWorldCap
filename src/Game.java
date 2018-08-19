import java.util.Arrays;

public class Game {
	private char[] homeTeam;
	private char[] hostTeam;
	private int homeScore;
	private int guestScore;

	public Game(char[] homeTeam, char[] hostTeam, int homeScore, int hostScore) {
		super();
		this.homeTeam = homeTeam;
		this.hostTeam = hostTeam;
		this.homeScore = homeScore;
		this.guestScore = hostScore;

	}

	public char[] getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(char[] homeTeam) {
		this.homeTeam = homeTeam;
	}

	public char[] getGuestTeam() {
		return hostTeam;
	}

	public void setGuestTeam(char[] hostTeam) {
		this.hostTeam = hostTeam;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getGuestScore() {
		return guestScore;
	}

	public void setGuestScore(int hostScore) {
		this.guestScore = hostScore;
	}

	@Override
	public String toString() {
		return new String(getHomeTeam()).toUpperCase() + " " + getHomeScore() + " - " + getGuestScore() + " "
				+ new String(getGuestTeam()).toUpperCase();
	}

}
