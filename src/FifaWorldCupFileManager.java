
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.scene.paint.Color;

public class FifaWorldCupFileManager implements FifaWorldCupData {

	final int TEAMSIZE = 3 * Character.BYTES + (Double.BYTES * 6);
	final int GAMESIZE = 6 * Character.BYTES + (Integer.BYTES * 2);
	final int NAMESIZE = 3;

	private RandomAccessFile teamAccessFile;
	private RandomAccessFile gameAccessFile;

	public FifaWorldCupFileManager(String fileTeam, String fileGame) throws IOException {
		super();
		this.teamAccessFile = new RandomAccessFile(fileTeam, "rw");
		this.gameAccessFile = new RandomAccessFile(fileGame, "rw");
		this.teamAccessFile.seek(Integer.BYTES);
		this.gameAccessFile.seek(Integer.BYTES);
	}

	public char[] readTeamName() throws IOException {
		char[] tempName = new char[NAMESIZE];
		for (int i = 0; i < NAMESIZE; i++) {
			tempName[i] = teamAccessFile.readChar();
		}
		return tempName;

	}

	@Override
	public void saveTeam(Team team) throws IOException {
		long fileSize = teamAccessFile.length();
		teamAccessFile.seek(fileSize);
		saveOneTeam(team);
		long tempPos = teamAccessFile.getFilePointer();
		// teamAccessFile.seek(0);
		// teamAccessFile.writeInt(getNumOfTeams()+1);
		// teamAccessFile.seek(tempPos);

	}

	@Override
	public void saveTeam(Team team, int index) throws IOException {

		teamAccessFile.seek(Integer.BYTES + (TEAMSIZE * (index - 1)));
		Team tempTeam = new Team(readTeamName(), readColor(), readColor());
		teamAccessFile.seek(Integer.BYTES + (TEAMSIZE * (index - 1)));
		saveOneTeam(team);
		saveTeam(tempTeam);

	}

	public void saveOneTeam(Team team) throws IOException {
		for (int i = 0; i < team.getCountryName().length; i++) {
			teamAccessFile.writeChar(team.getCountryName()[i]);
		}
		writeColor(team.getPrimaryColor());
		writeColor(team.getSecendryColor());
	}

	@Override
	public Team nextTeam() throws IOException {

		long fileSize = teamAccessFile.length();

		if (teamAccessFile.getFilePointer() >= fileSize) {
			teamAccessFile.seek(Integer.BYTES);

		}

		return new Team(readTeamName(), readColor(), readColor());

	}

	@Override
	public Team previousTeam() throws IOException {

		long fileSize = teamAccessFile.length();
		if (teamAccessFile.getFilePointer() <= TEAMSIZE + Integer.BYTES) {
			teamAccessFile.seek(fileSize - TEAMSIZE);
		} else {
			teamAccessFile.seek(teamAccessFile.getFilePointer() - TEAMSIZE * 2);
		}

		return new Team(readTeamName(), readColor(), readColor());
	}

	public char[] readGameName() throws IOException {
		char[] tempName = new char[NAMESIZE];
		for (int i = 0; i < NAMESIZE; i++) {
			tempName[i] = gameAccessFile.readChar();
		}
		return tempName;

	}

	@Override
	public void saveGame(Game game) throws IOException {

		long fileSize = gameAccessFile.length();
		gameAccessFile.seek(fileSize);
		saveOneGame(game);

	}

	@Override
	public void saveGame(Game game, int index) throws IOException {

		gameAccessFile.seek(Integer.BYTES + (GAMESIZE * (index - 1)));
		Game tempGame = new Game(readGameName(), readGameName(), gameAccessFile.readInt(), gameAccessFile.readInt());
		gameAccessFile.seek(Integer.BYTES + (GAMESIZE * (index - 1)));
		saveOneGame(game);
		saveGame(tempGame);

	}

	public void saveOneGame(Game game) throws IOException {
		for (int i = 0; i < NAMESIZE; i++) {

			gameAccessFile.writeChar(game.getHomeTeam()[i]);
		}
		for (int i = 0; i < NAMESIZE; i++) {

			gameAccessFile.writeChar(game.getGuestTeam()[i]);
		}

		gameAccessFile.writeInt(game.getHomeScore());
		gameAccessFile.writeInt(game.getGuestScore());
		long tempPos = gameAccessFile.getFilePointer();
		int num = getNumOfGames();
		gameAccessFile.seek(0);
		gameAccessFile.writeInt(num + 1);
		gameAccessFile.seek(tempPos);
		num = getNumOfGames();
	}

	@Override
	public Game getGameAt(int index) throws IOException {
		gameAccessFile.seek(Integer.BYTES + (GAMESIZE * (index)));
		return new Game(readGameName(), readGameName(), gameAccessFile.readInt(), gameAccessFile.readInt());

	}

	@Override
	public int getNumOfTeams() {
		long tempPos;
		int numOfTeam = 0;
		try {
			tempPos = teamAccessFile.getFilePointer();
			teamAccessFile.seek(0);
			numOfTeam = teamAccessFile.readInt();
			teamAccessFile.seek(tempPos);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return numOfTeam;
	}

	@Override
	public int getNumOfGames() {
		long tempPos;
		int numOfGames = 0;
		try {
			tempPos = gameAccessFile.getFilePointer();
			gameAccessFile.seek(0);
			numOfGames = gameAccessFile.readInt();
			gameAccessFile.seek(tempPos);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return numOfGames;
	}

	public Color readColor() throws IOException {

		return new Color(teamAccessFile.readDouble(), teamAccessFile.readDouble(), teamAccessFile.readDouble(), 1);

	}

	public void writeColor(Color color) throws IOException {

		teamAccessFile.writeDouble(color.getRed());
		teamAccessFile.writeDouble(color.getGreen());
		teamAccessFile.writeDouble(color.getBlue());

	}

}
