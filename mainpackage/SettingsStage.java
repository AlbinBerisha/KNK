package mainpackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SettingsStage extends Stage {
	
	private File profilePicFile;
	private Statement statement;
	private Image profileImage;
	private ImageView profileImageView;
	
	public SettingsStage(MainScene ms, Stage primaryStage) {
		super();
		this.statement = DBConnection.createStatement();
		this.setup(ms, primaryStage);
	}
	
	private void setup(MainScene ms, Stage primaryStage) {
		BorderPane borderPane = new BorderPane();
		Scene scene = new Scene(borderPane, 480, 720);
		HBox hBoxError = new HBox();
		Label errorLabel = new Label("Hello...");
		FileChooser profilePicChooser = new FileChooser();
		GridPane settingsGridPane = new GridPane();
		Button uploadButton = new Button("Upload");
		this.profilePicFile = new File("");
		try {
			ResultSet rSet = this.statement.executeQuery("SELECT uProfilePicture FROM users WHERE uUsername = '" + ms.getUsername() + "';");
			rSet.next();
			if(new File("C:/Users/albin/eclipse-workspace/ProjektiKNK/src/images/" + rSet.getString("uProfilePicture")).exists()) {
				this.profileImage = new Image("/images/" + rSet.getString("uProfilePicture"), 150, 150, false, true);
				this.profileImageView = new ImageView(this.profileImage);
			}
			else
				this.profileImageView = new ImageView(new Image("/images/Default Profile Picture.png", 150, 150, false, true));
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
		borderPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #00ffff, #003f3f);");
		borderPane.setCenter(settingsGridPane);
		borderPane.setBottom(hBoxError);
		hBoxError.getChildren().add(errorLabel);
		hBoxError.setStyle("-fx-background-color: #0000005f; -fx-padding: 20px; -fx-alignment: center-right;");
		errorLabel.setTextFill(Color.RED);
		profilePicChooser.setTitle("Choose image");
		settingsGridPane.add(this.profileImageView, 0, 0);
		settingsGridPane.add(uploadButton, 0, 1);
		uploadButton.setOnAction(e -> {
			try {
				this.profilePicFile = profilePicChooser.showOpenDialog(this);
				BufferedImage bufferedImage = ImageIO.read(this.profilePicFile);
				this.profileImage = SwingFXUtils.toFXImage(bufferedImage, null);
				this.saveImage(this.profileImage, ms.getUsername() + "ProfilePicture.png", ms);
				this.profileImage = new Image("/images/" + ms.getUsername() + "ProfilePicture.png", 150, 150, false, true);
				this.profileImageView.setImage(this.profileImage);
				ms.setup(primaryStage);
			} catch (IOException e1) {
				errorLabel.setText("Image could not be read.");
				this.profileImage = new Image("/images/Default Profile Picture.png");
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
				this.profileImageView  = new ImageView(new Image("/images/Default Profile Picture.png"));
			}
		});
		settingsGridPane.setAlignment(Pos.CENTER);
		settingsGridPane.setStyle("-fx-vgap: 10px; -fx-hgap: 10px;");
		
		this.setX((Screen.getPrimary().getVisualBounds().getWidth()-480)/2);
		this.setY((Screen.getPrimary().getVisualBounds().getHeight()-720)/2);
		this.setTitle("Settings");
		this.setScene(scene);
		this.sizeToScene();
		this.setResizable(false);
	}
	
	private void saveImage(Image image, String pictureName, MainScene ms) {
		File file = new File("C:/Users/albin/eclipse-workspace/ProjektiKNK/src/images/" + pictureName);
		BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(bufferedImage, "png", file);
			this.statement.executeUpdate("UPDATE users SET uProfilePicture = '" + pictureName + "' WHERE uUsername = '" + ms.getUsername() + "';");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
