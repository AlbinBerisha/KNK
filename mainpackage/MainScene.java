package mainpackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainScene extends Scene {
	
	private Statement statement;
	private String firstName;
	private String username;
	private SettingsStage settingsWindow;
	private ImageView profileImageView;
	
	public MainScene(Stage primaryStage, String username) {
		super(new BorderPane(), 1280, 720);
		this.username = username;
		this.statement = DBConnection.createStatement();
		this.initializeUserData(username);
		this.setup(primaryStage);
	}
	
	public void setup(Stage primaryStage) {
		primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth()-1280)/2);
		primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight()-720)/2);
		
		BorderPane borderPane = new BorderPane();
		BorderPane leftBorderPane = new BorderPane();
		VBox leftVBox = new VBox();
		this.profileImageView = new ImageView();
		Image profileImage;
		try {
			ResultSet rSet = this.statement.executeQuery("SELECT uProfilePicture FROM users WHERE uUsername = '" + this.username + "';");
			rSet.next();
			profileImage = new Image("/images/" + rSet.getString("uProfilePicture"), 100, 100, true, false);
		} catch (SQLException e1) {
			profileImage = new Image("/images/Default Profile Picture.png", 100, 100, true, false);
		}
		Text profileFirstName = new Text(this.firstName);
		VBox settingsAndLogout = new VBox();
		Button settingsButton = new Button("Settings");
		Button logoutButton = new Button("Log Out");
		BorderPane topBorderPane = new BorderPane();
		HBox menu = new HBox();
		BorderPane centerBorderPane = new BorderPane();
		Button[] menuButtons = new Button[] {I18N.getButton("homeMenuButton"), I18N.getButton("carsMenuButton"), I18N.getButton("rentMenuButton"), I18N.getButton("historyMenuButton")};
		HBox languageHBox = new HBox();
		ComboBox<String> languageCB = new ComboBox<String>(FXCollections.observableArrayList("AL", "EN"));
		HBox hBoxError = new HBox();
		Label errorLabel = new Label("Hello...");
		RentalHistoryStage rentalHistoryWindow = new RentalHistoryStage(this.username);
		this.settingsWindow = new SettingsStage(this, primaryStage);
		boolean[] menuStatus = new boolean[] {true, false, false, false};
		
		borderPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #00ffff, #003f3f);");
		borderPane.setLeft(leftBorderPane);
		borderPane.setCenter(centerBorderPane);
		leftBorderPane.setCenter(leftVBox);
		leftBorderPane.setBottom(settingsAndLogout);
		leftBorderPane.setStyle("-fx-padding: 20px; -fx-background-color: #0000005f;");
		leftVBox.getChildren().addAll(this.profileImageView, profileFirstName);
		leftVBox.setStyle("-fx-spacing: 10px;");
		this.profileImageView.setImage(profileImage);
		profileFirstName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		settingsButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		settingsButton.setOnAction(e -> {
			this.settingsWindow.show();
		});
		settingsButton.setOnMouseEntered(e -> {
			settingsButton.setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		settingsButton.setOnMouseExited(e -> {
			settingsButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		logoutButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		logoutButton.setOnAction(e -> {
			this.settingsWindow.close();
			rentalHistoryWindow.close();
			primaryStage.setScene(new LoginScene(primaryStage));
		});
		logoutButton.setOnMouseEntered(e -> {
			logoutButton.setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		logoutButton.setOnMouseExited(e -> {
			logoutButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		settingsAndLogout.getChildren().addAll(settingsButton, logoutButton);
		settingsAndLogout.setStyle("-fx-spacing: 10px;");
		centerBorderPane.setTop(topBorderPane);
		centerBorderPane.setCenter(new HomeMenu(primaryStage, this));
		centerBorderPane.setBottom(hBoxError);
		menuButtons[0].setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		for(int i = 1; i < menuButtons.length; i++)
			menuButtons[i].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		menuButtons[0].setOnAction(e -> {
			centerBorderPane.setCenter(new HomeMenu(primaryStage, this));
			menuStatus[0] = true;
			menuStatus[1] = false;
			menuStatus[2] = false;
			menuStatus[3] = false;
			menuButtons[1].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
			menuButtons[2].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
			menuButtons[3].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		menuButtons[0].setOnMouseEntered(e -> {
			menuButtons[0].setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		menuButtons[0].setOnMouseExited(e -> {
			if(!menuStatus[0])
				menuButtons[0].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		menuButtons[1].setOnAction(e -> {
			centerBorderPane.setCenter(new CarsMenu());
			menuStatus[1] = true;
			menuStatus[0] = false;
			menuStatus[2] = false;
			menuStatus[3] = false;
			menuButtons[0].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
			menuButtons[2].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
			menuButtons[3].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		menuButtons[1].setOnMouseEntered(e -> {
			menuButtons[1].setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		menuButtons[1].setOnMouseExited(e -> {
			if(!menuStatus[1])
				menuButtons[1].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		menuButtons[2].setOnAction(e -> {
			centerBorderPane.setCenter(new RentMenu(this));
			menuStatus[2] = true;
			menuStatus[1] = false;
			menuStatus[0] = false;
			menuStatus[3] = false;
			menuButtons[1].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
			menuButtons[0].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
			menuButtons[3].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		menuButtons[2].setOnMouseEntered(e -> {
			menuButtons[2].setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		menuButtons[2].setOnMouseExited(e -> {
			if(!menuStatus[2])
				menuButtons[2].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		menuButtons[3].setOnAction(e -> {
			centerBorderPane.setCenter(new HistoryMenu(rentalHistoryWindow, this));
			menuStatus[3] = true;
			menuStatus[1] = false;
			menuStatus[2] = false;
			menuStatus[0] = false;
			menuButtons[1].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
			menuButtons[2].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
			menuButtons[0].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		menuButtons[3].setOnMouseEntered(e -> {
			menuButtons[3].setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		menuButtons[3].setOnMouseExited(e -> {
			if(!menuStatus[3])
				menuButtons[3].setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		topBorderPane.setCenter(menu);
		topBorderPane.setRight(languageHBox);
		topBorderPane.setStyle("-fx-border-width: 0px 0px 1px 0px; -fx-border-style: none none solid none; -fx-border-color: #0000005f; -fx-padding: 20px;");
		menu.getChildren().addAll(menuButtons);
		menu.setStyle("-fx-spacing: 10px;");
		languageCB.setValue("EN");
		languageCB.setStyle("-fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: #000000; -fx-background-color: #00000000;");
		languageCB.setOnAction(e -> {
			I18N.setLocale(new Locale(languageCB.getValue().toLowerCase()));
		});
		languageHBox.getChildren().addAll(I18N.getLabel("languageLabel"), languageCB);
		languageHBox.setAlignment(Pos.CENTER);
		languageHBox.setStyle("-fx-spacing: 10px;");
		errorLabel.setTextFill(Color.RED);
		hBoxError.getChildren().add(errorLabel);
		hBoxError.setStyle("-fx-background-color: #0000005f; -fx-padding: 20px; -fx-alignment: center-right;");
		primaryStage.setOnCloseRequest(e -> {
			this.settingsWindow.close();
			rentalHistoryWindow.close();
		});
		
		super.setRoot(borderPane);
	}
	
	private void initializeUserData(String username) {
		ResultSet resultSet;
		try {
			resultSet = this.statement.executeQuery("select * from users;");
			while(resultSet.next())
				if(resultSet.getString(6).equals(username)) {
					this.firstName = resultSet.getString(2);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getFirstName() {
		return this.firstName;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void changeProfilePicture(String imagePath) {
		this.profileImageView = new ImageView(new Image(imagePath, 100, 100, true, false));
	}
	
}
