import java.util.Arrays;

import javafx.scene.paint.Color;

public class Team {
	private char[] countryName;
	private Color primaryColor;
	private Color secendryColor;

	public Team(char[] countryName, Color primaryColor, Color secendryColor) {
		super();
		this.countryName = countryName;
		this.primaryColor = primaryColor;
		this.secendryColor = secendryColor;
	}

	public char[] getCountryName() {
		return countryName;
	}

	@Override
	public String toString() {
		String temp = new String(countryName);
		return temp;
	}

	public void setCountryName(char[] countryName) {
		this.countryName = countryName;
	}

	public Color getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(Color primaryColor) {
		this.primaryColor = primaryColor;
	}

	public Color getSecendryColor() {
		return secendryColor;
	}

	public void setSecendryColor(Color secendryColor) {
		this.secendryColor = secendryColor;
	}

}
