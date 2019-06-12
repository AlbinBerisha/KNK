package mainpackage;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SignupScene extends Scene {
	
	private Statement statement;
	private String[] signupData;
	private boolean signedUp;
	
	public SignupScene(Stage primaryStage) {
		super(new BorderPane(), 480, 720);
		this.statement = DBConnection.createStatement();
		this.setup(primaryStage);
	}
	
	private void setup(Stage primaryStage) {
		primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth()-480)/2);
		primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight()-720)/2);
		this.signupData = new String[6];
		this.signedUp = false;
		
		BorderPane borderPane = new BorderPane();
		HBox hBoxBackButton = new HBox();
		Button backButton = I18N.getButton("backButton");
		BorderPane topBorderPane = new BorderPane();
		HBox languageHBox = new HBox();
		ComboBox<String> languageCB = new ComboBox<String>(FXCollections.observableArrayList("AL", "EN"));
		GridPane grid = new GridPane();
		Text sceneTitle = I18N.getText("signupHeaderText");
		TextField firstName = new TextField();
		TextField lastName = new TextField();
		HBox hBoxGender = new HBox();
		ToggleGroup gender = new ToggleGroup();
		RadioButton femaleButton = new RadioButton("Female");
		RadioButton maleButton = new RadioButton("Male");
		DatePicker date = new DatePicker();
		TextField username = new TextField();
		PasswordField password = new PasswordField();
		PasswordField confirmedPassword = new PasswordField();
		HBox hBoxSignup = new HBox();
		Button signupButton = I18N.getButton("signupButton");
		HBox hBoxError = new HBox();
		Label errorLabel = new Label("Hello...");
		
		hBoxBackButton.getChildren().add(backButton);
		hBoxBackButton.setStyle("-fx-padding: 20px; -fx-alignment: center-left;");
		backButton.setOnAction(e -> {
			primaryStage.setScene(new LoginScene(primaryStage));
		});
		backButton.setStyle("-fx-background-color: #00000000; -fx-cursor: hand; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-text-fill: black;");
		backButton.setOnMouseEntered(e -> {
			backButton.setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		backButton.setOnMouseExited(e -> {
			backButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		languageHBox.getChildren().addAll(I18N.getLabel("languageLabel"), languageCB);
		languageHBox.setAlignment(Pos.CENTER_RIGHT);
		languageHBox.setStyle("-fx-spacing: 10px;");
		languageCB.setStyle("-fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: #000000; -fx-background-color: #00000000;");
		languageCB.setValue("EN");
		languageCB.setOnAction(e -> {
			I18N.setLocale(new Locale(languageCB.getValue().toLowerCase()));
		});
		topBorderPane.setCenter(backButton);
		topBorderPane.setRight(languageHBox);
		BorderPane.setAlignment(topBorderPane.getCenter(), Pos.CENTER_LEFT);
		topBorderPane.setStyle("-fx-padding: 20px;");
		borderPane.setTop(topBorderPane);
		borderPane.setCenter(grid);
		borderPane.setBottom(hBoxError);
		borderPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #00ffff, #003f3f);");
		borderPane.setPrefSize(480, 720);
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		femaleButton.setToggleGroup(gender);
		femaleButton.setStyle("-fx-cursor: hand;");
		maleButton.setToggleGroup(gender);
		maleButton.setStyle("-fx-cursor: hand;");
		hBoxGender.getChildren().addAll(femaleButton, maleButton);
		hBoxGender.setSpacing(20);
		hBoxSignup.getChildren().add(signupButton);
		hBoxSignup.setAlignment(Pos.CENTER_RIGHT);
		signupButton.setOnAction(e -> {
			try {
				this.signupData[0] = firstName.getText();
				this.signupData[1] = lastName.getText();
				if(femaleButton.isSelected())
					this.signupData[2] = "Female";
				else if(maleButton.isSelected())
					this.signupData[2] = "Male";
				else
					this.signupData[2] = "";
				try {
					this.signupData[3] = date.getValue().toString();
				} catch(Exception ex) {
					this.signupData[3] = "";
				}
				this.signupData[4] = username.getText();
				this.signupData[5] = password.getText();
				if(this.signupData[0] == "" || this.signupData[1] == "" || this.signupData[2] == "" || this.signupData[3] == "" || this.signupData[4] == "" || this.signupData[5] == "")
					throw new Exception();
				else if(!this.signedUp) {
					this.signup(primaryStage);
					this.signedUp = true;
				}
			} catch (Exception ex) {
				errorLabel.setText("Please fill all fields.");
				this.signedUp = false;
			}
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
		grid.add(sceneTitle, 0, 0, 2, 1);
		grid.add(I18N.getLabel("firstNameLabel"), 0, 1);
		grid.add(firstName, 1, 1);
		grid.add(I18N.getLabel("lastNameLabel"), 0, 2);
		grid.add(lastName, 1, 2);
		grid.add(I18N.getLabel("genderLabel"), 0, 3);
		grid.add(hBoxGender, 1, 3);
		grid.add(I18N.getLabel("birthdayLabel"), 0, 4);
		grid.add(date, 1, 4);
		grid.add(I18N.getLabel("createUsernameLabel"), 0, 5);
		grid.add(username, 1, 5);
		grid.add(I18N.getLabel("createPasswordLabel"), 0, 6);
		grid.add(password, 1, 6);
		grid.add(I18N.getLabel("confirmPasswordLabel"), 0, 7);
		grid.add(confirmedPassword, 1, 7);
		grid.add(hBoxSignup, 0, 8, 2, 1);
		grid.setStyle("-fx-alignment: center; -fx-vgap: 20px; -fx-hgap: 20px;");
		
		super.setRoot(borderPane);
	}
	
	private void signup(Stage primaryStage) {
		try {
			this.statement.executeUpdate("insert into users(uFirstName, uLastName, uGender, uBirthDate, uUsername, uPassword) values ('" + this.signupData[0] + "', '" + this.signupData[1] + "', '" + this.signupData[2] + "', '" + this.signupData[3] + "', '" + this.signupData[4] + "', '" + this.signupData[5] + "');");
			primaryStage.setScene(new MainScene(primaryStage, this.signupData[4]));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
