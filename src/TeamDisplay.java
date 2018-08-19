import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TeamDisplay extends BorderPane {
	private FifaWorldCupFileManager fifa;

	private Color tempPrmColor;
	private Color tempSecColor;
	private Team theTeam;

	private Button leftButton;
	private Button rightButton;
	private ImageView countryImage;
	private Label labelName;
	private Circle firstColorCircle;
	private Circle secondColorCircle;
	private Label labelPrmColor;
	private Label labelSecColor;
	private String name;
	private GridPane colorPane;

	private BorderPane tempPane;
	// private StackPane labelAndCircle;

	public TeamDisplay() {
		try {
			fifa = new FifaWorldCupFileManager(".\\teams.bin", ".\\games.bin");
			theTeam = fifa.nextTeam();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// name = new String(theTeam.getCountryName());
		buildPane();

	}

	private void buildPane() {

		name = new String(theTeam.getCountryName());

		leftButton = new Button("<");
		previouseButtonAction();
		rightButton = new Button(">");
		nextButtonAction();
		countryImage = new ImageView("file:.\\photos\\" + name + ".png");
		labelName = new Label((name.toUpperCase()));
		labelPrmColor = new Label("First Color");
		labelSecColor = new Label("Second Color");
		tempPrmColor = Color.color(theTeam.getPrimaryColor().getRed(), theTeam.getPrimaryColor().getGreen(),
				theTeam.getPrimaryColor().getBlue());
		tempSecColor = Color.color(theTeam.getSecendryColor().getRed(), theTeam.getSecendryColor().getGreen(),
				theTeam.getSecendryColor().getBlue());
		firstColorCircle = new Circle(8, 8, 8);
		secondColorCircle = new Circle(8, 8, 8);

		firstColorCircle.setFill(tempPrmColor);
		secondColorCircle.setFill(tempSecColor);

		// thePane.setStyle("-fx-background-color: blue");

		colorPane = new GridPane();
		colorPane.add(labelPrmColor, 0, 0);
		colorPane.add(labelSecColor, 0, 1);
		colorPane.add(firstColorCircle, 1, 0);
		colorPane.add(secondColorCircle, 1, 1);
		// labelAndCircle = new StackPane();
		// labelAndCircle.getChildren().add(colorPane);
		tempPane = new BorderPane();
		tempPane.setCenter(colorPane);

		setStyle("-fx-background-color: blue");
		setAlignment(leftButton, Pos.CENTER_LEFT);
		setAlignment(rightButton, Pos.CENTER_RIGHT);
		setAlignment(labelName, Pos.TOP_CENTER);
		// setAlignment(tempPane, Pos.CENTER);

		setCenter(countryImage);
		setTop(labelName);
		setLeft(leftButton);
		setRight(rightButton);
		setBottom(tempPane);

		// leftButton.setPadding(new Insets(15));
		// rightButton.setPadding(new Insets(15));
		setPadding(new Insets(15));

	}

	private void nextButtonAction() {

		rightButton.setOnAction(e -> {
			try {
				theTeam = fifa.nextTeam();
				name = new String(theTeam.getCountryName());
				labelName.setText(name.toUpperCase());
				countryImage = new ImageView("file:.\\photos\\" + name + ".png");
				setCenter(countryImage);
				tempPrmColor = Color.color(theTeam.getPrimaryColor().getRed(), theTeam.getPrimaryColor().getGreen(),
						theTeam.getPrimaryColor().getBlue());
				tempSecColor = Color.color(theTeam.getSecendryColor().getRed(), theTeam.getSecendryColor().getGreen(),
						theTeam.getSecendryColor().getBlue());
				firstColorCircle.setFill(tempPrmColor);
				secondColorCircle.setFill(tempSecColor);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

	}

	private void previouseButtonAction() {

		leftButton.setOnAction(e -> {
			try {
				theTeam = fifa.previousTeam();
				name = new String(theTeam.getCountryName());
				labelName.setText(name.toUpperCase());
				countryImage = new ImageView("file:.\\photos\\" + name + ".png");
				setCenter(countryImage);
				tempPrmColor = Color.color(theTeam.getPrimaryColor().getRed(), theTeam.getPrimaryColor().getGreen(),
						theTeam.getPrimaryColor().getBlue());
				tempSecColor = Color.color(theTeam.getSecendryColor().getRed(), theTeam.getSecendryColor().getGreen(),
						theTeam.getSecendryColor().getBlue());
				firstColorCircle.setFill(tempPrmColor);
				secondColorCircle.setFill(tempSecColor);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

	}

	public String getName() {
		return theTeam.toString();
	}

}
