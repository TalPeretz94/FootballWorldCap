
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
// Tal Peretz 312468929
public class Main extends Application {

	private FifaWorldCupFileManager fifa;

	private TeamDisplay leftPane;
	private TeamDisplay rightPane;
	private BorderPane borderPane;
	private BorderPane topMenu;
	private HBox downMenu;
	private Button btNewMatch;
	private Button btMatches;
	private HBox centerMenu;
	private Label HomeLabel;
	private TextField homeName;
	private TextField homeScoreInput;
	private Label guestLabel;
	private TextField guestName;
	private TextField guestScoreInput;
	private Button btSave;
	private Game setNewGame;
	private ComboBox<Integer> comboBox;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage window = primaryStage;
		fifa = new FifaWorldCupFileManager(".\\teams.bin", ".\\games.bin");

		// topmenu fifa logo
		topMenu = new BorderPane();
		ImageView logoImage = new ImageView("file:.\\logo.png");
		topMenu.setStyle("-fx-background-color: #8a000b");
		topMenu.setCenter(logoImage);

		// centerPane left and right flags display
		leftPane = new TeamDisplay();
		rightPane = new TeamDisplay();
		centerMenu = new HBox();
		centerMenu.getChildren().addAll(leftPane, rightPane);

		// bottom pane the buttons New-Match and Matches
		downMenu = new HBox();
		btNewMatch = new Button("New-Match");
		btMatches = new Button("Matches");
		downMenu.getChildren().addAll(btNewMatch, btMatches);

		btNewMatch.setOnAction(e -> {
			newMatchWindowNew();
			// window.close();
		});

		btMatches.setOnAction(e -> {

			MatchWindow();

		});

		// set all the panes to the main pane
		borderPane = new BorderPane();
		borderPane.setTop(topMenu);
		borderPane.setCenter(centerMenu);
		borderPane.setAlignment(downMenu, Pos.BOTTOM_CENTER);
		borderPane.setBottom(downMenu);

		Scene scene = new Scene(borderPane);
		window.setScene(scene);
		window.show();

	}

	public void newMatchWindowNew() {
		Stage newMatchWindow = new Stage();
		GridPane pane = new GridPane();
		pane.setStyle("-fx-background-color: #8a000b");
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setVgap(20);
		pane.setHgap(10);

		HomeLabel = new Label("Home");
		HomeLabel.setStyle("-fx-text-fill: WHITE");
		GridPane.setConstraints(HomeLabel, 0, 0);

		homeName = new TextField(leftPane.getName());
		GridPane.setConstraints(homeName, 0, 1);

		homeScoreInput = new TextField();
		homeScoreInput.setPromptText("Enter Score Here");
		GridPane.setConstraints(homeScoreInput, 0, 2);

		guestLabel = new Label("Guest");
		guestLabel.setStyle("-fx-text-fill: WHITE");
		GridPane.setConstraints(guestLabel, 1, 0);

		guestName = new TextField(rightPane.getName());
		GridPane.setConstraints(guestName, 1, 1);

		guestScoreInput = new TextField();
		guestScoreInput.setPromptText("Enter Score Here");
		GridPane.setConstraints(guestScoreInput, 1, 2);

		btSave = new Button("Save");
		btSave.setOnAction(e -> {
			try {
				btSaveAction();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		GridPane.setConstraints(btSave, 0, 3);
		GridPane.setColumnSpan(btSave, 2);

		GridPane.setHalignment(btSave, HPos.CENTER);
		homeName.setEditable(false);
		guestName.setEditable(false);

		pane.getChildren().addAll(HomeLabel, homeName, homeScoreInput, guestLabel, guestName, guestScoreInput, btSave);

		Scene newMatchScene = new Scene(pane);
		newMatchWindow.setScene(newMatchScene);
		newMatchWindow.show();

	}

	public void btSaveAction() throws IOException {
		String homeTextInput = homeScoreInput.getText();
		String guestTextInput = guestScoreInput.getText();
		String errorMassage = "Unable to save the game: ";
		String negativeMassege = " score must be a non-negative Integer";
		String stringMassage = "For input String: ";
		String allErrorMassege = "";
		int HomeScoreNumber = 0;
		int guestScoreNumber = 0;
		boolean isHomeNumber = true, isGuestNumber = true;
		try {
			HomeScoreNumber = Integer.parseInt(homeTextInput);

		} catch (NumberFormatException nfe) {
			isHomeNumber = false;

		}
		try {
			guestScoreNumber = Integer.parseInt(guestTextInput);

		} catch (NumberFormatException nfe) {
			isGuestNumber = false;

		}

		if (HomeScoreNumber < 0 || guestScoreNumber < 0 || !isHomeNumber || !isGuestNumber) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			if (HomeScoreNumber < 0) {

				allErrorMassege = errorMassage + "Home" + negativeMassege;
			} else if (guestScoreNumber < 0) {

				allErrorMassege = errorMassage + "Guest" + negativeMassege;

			} else if (!isHomeNumber) {
				allErrorMassege = errorMassage + stringMassage + homeTextInput;

			} else if (!isGuestNumber) {
				allErrorMassege = errorMassage + stringMassage + guestTextInput;

			}
			alert.setContentText(allErrorMassege);

			alert.showAndWait();

		} else {
			setNewGame = new Game(homeName.getText().toCharArray(), guestName.getText().toCharArray(), HomeScoreNumber,
					guestScoreNumber);
			fifa.saveGame(setNewGame);

		}
	}

	public void MatchWindow() {
		int numOfGames = fifa.getNumOfGames();
		Label gameDisplayLabel = new Label();
		gameDisplayLabel.setStyle("-fx-text-fill: WHITE");
		Stage matchDisplay = new Stage();
		BorderPane matchPane = new BorderPane();
		matchPane.setStyle("-fx-background-color: #8a000b");
		matchPane.setPadding(new Insets(10, 10, 10, 10));
		comboBox = new ComboBox<>();
		comboBox.setPromptText("Choose game here");
		for (int i = 0; i < numOfGames; i++) {
			comboBox.getItems().add(i + 1);
		}
		matchPane.setAlignment(comboBox, Pos.TOP_CENTER);
		matchPane.setTop(comboBox);

		comboBox.setOnAction(e -> {
			int index = comboBox.getValue();
			try {
				gameDisplayLabel.setText(fifa.getGameAt(index - 1).toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			matchPane.setAlignment(gameDisplayLabel, Pos.CENTER);
			matchPane.setCenter(gameDisplayLabel);
		});

		Scene newMatchScene = new Scene(matchPane, 350, 350);
		matchDisplay.setScene(newMatchScene);
		matchDisplay.show();

	}

}
