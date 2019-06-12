package mainpackage;

import java.sql.Connection;
import java.sql.DriverManager;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginScene extends Scene {
	
	private Statement statement;
	private String[] loginData;
	
	public LoginScene(Stage primaryStage) {
		super(new GridPane(), 480, 720);
		this.initializeDatabase();
		this.setup(primaryStage);
	}
	
	private void setup(Stage primaryStage) {
		primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth()-480)/2);
		primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight()-720)/2);
		this.loginData = new String[2];
		
		BorderPane borderPane = new BorderPane();
		HBox languageHBox = new HBox();
		ComboBox<String> languageCB = new ComboBox<String>(FXCollections.observableArrayList("AL", "EN"));
		GridPane grid = new GridPane();
		HBox hBoxProfileImg = new HBox();
		Image profileImg = new Image("/images/Default Profile Picture.png");
		ImageView profileImgView = new ImageView(profileImg);
		Text sceneTitle = I18N.getText("welcomeText");
		TextField username = new TextField();
		PasswordField password = new PasswordField();
		HBox hBoxLogin = new HBox();
		Button loginButton = I18N.getButton("loginButton");
		HBox hBoxSignup = new HBox();
		Button signupButton = I18N.getButton("signupButton");
		HBox hBoxError = new HBox();
		Label errorLabel = new Label("Hello...");
		
		borderPane.setTop(languageHBox);
		borderPane.setCenter(grid);
		borderPane.setBottom(hBoxError);
		borderPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #00ffff, #003f3f);");
		languageHBox.getChildren().addAll(I18N.getLabel("languageLabel"), languageCB);
		languageHBox.setStyle("-fx-padding: 20px; -fx-spacing: 10px;");
		languageHBox.setAlignment(Pos.CENTER_RIGHT);
		languageCB.setValue("EN");
		languageCB.setOnAction(e -> {
			I18N.setLocale(new Locale(languageCB.getValue().toLowerCase()));
		});
		languageCB.setStyle("-fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: #000000; -fx-background-color: #00000000;");
		profileImgView.setFitWidth(150);
		profileImgView.setFitHeight(150);
		hBoxProfileImg.getChildren().add(profileImgView);
		hBoxProfileImg.setAlignment(Pos.CENTER);
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		username.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				this.checkUsernameAndPassword(username, password, primaryStage, errorLabel);
		});
		password.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER)
				this.checkUsernameAndPassword(username, password, primaryStage, errorLabel);
		});
		hBoxLogin.setAlignment(Pos.CENTER_RIGHT);
		hBoxLogin.getChildren().add(loginButton);
		loginButton.setOnAction(e -> {
			this.checkUsernameAndPassword(username, password, primaryStage, errorLabel);
		});
		loginButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		loginButton.setOnMouseEntered(e -> {
			loginButton.setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		loginButton.setOnMouseExited(e -> {
			loginButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		hBoxSignup.getChildren().addAll(I18N.getLabel("questionLabel"), signupButton);
		hBoxSignup.setSpacing(120);
		signupButton.setOnAction(e -> {
			primaryStage.setScene(new SignupScene(primaryStage));
		});
		signupButton.setOnMouseEntered(e -> {
			signupButton.setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		signupButton.setOnMouseExited(e -> {
			signupButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		signupButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		hBoxError.getChildren().add(errorLabel);
		hBoxError.setStyle("-fx-background-color: #0000005f; -fx-padding: 20px; -fx-alignment: center-right;");
		errorLabel.setTextFill(Color.RED);
		grid.add(hBoxProfileImg, 0, 0, 2, 1);
		grid.add(sceneTitle, 0, 1, 2, 1);
		grid.add(I18N.getLabel("usernameLabel"), 0, 2);
		grid.add(username, 1, 2);
		grid.add(I18N.getLabel("passwordLabel"), 0, 3);
		grid.add(password, 1, 3);
		grid.add(hBoxLogin, 1, 4);
		grid.add(hBoxSignup, 0, 7, 2, 1);
		grid.setStyle("-fx-alignment: center; -fx-vgap: 20px; -fx-hgap: 20px;");
		
		super.setRoot(borderPane);
	}
	
	private void initializeDatabase() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/HCIProject?characterEncoding=UTF-8&useSSL=false", "root", "matematikaV2");
		} catch (SQLException e) {
			e.printStackTrace();
			connection = null;
		}
		try {
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void checkUsernameAndPassword(TextField username, TextField password, Stage primaryStage, Label errorLabel) {
		try {
			this.loginData[0] = username.getText();
			this.loginData[1] = password.getText();
			ResultSet resultSet = this.statement.executeQuery("select uUsername, uPassword from users where uUsername = '" + this.loginData[0] + "';");
			
			if(this.loginData[0].equals("") || this.loginData[1].equals("")) {
				throw new Exception("Please fill all fields.");
			}
			else {
				resultSet.next();
				if(resultSet.getString(1).equals(this.loginData[0]) && resultSet.getString(2).equals(this.loginData[1]))
					this.login(primaryStage, this.loginData[0]);
				else
					throw new Exception("Wrond username or password.");
			}
		} catch (SQLException e1) {
			errorLabel.setText("Wrong username or password.");
		} catch (Exception e1) {
			errorLabel.setText(e1.getMessage());
		}
	}
	
	private void login(Stage primaryStage, String username) {
		primaryStage.setScene(new MainScene(primaryStage, username));
	}

}