package mainpackage;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeMenu extends GridPane {
	
	public HomeMenu(Stage primaryStage, MainScene mainScene) {
		super();
		this.setup(primaryStage, mainScene);
	}
	
	private void setup(Stage primaryStage, MainScene mainScene) {
		Text welcomeText = I18N.getText("homeHeaderText", mainScene.getFirstName());
		Pane homePane = new Pane();
		Image homeImage = new Image("/images/Audi.jpg", 1100, 350, true, true);
		ImageView homeImageView = new ImageView(homeImage);
		Text homeImageText = new Text(50, 100, "The new Audi Q5.");
		Text homeImageDescription = new Text(50, 175, "Character on the trip\nDesign and Technology.");
		Button homeImageButton = new Button("Rent Now");
		
		this.setStyle("-fx-padding: 20px; -fx-vgap: 20px;");
		welcomeText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
		this.add(welcomeText, 0, 0, 4, 1);
		homeImageText.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
		homeImageText.setStyle("-fx-fill: linear-gradient(to bottom right, #003f3f, #00ffff);");
		homeImageDescription.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
		homeImageDescription.setStyle("-fx-fill: linear-gradient(to bottom right, #003f3f, #00ffff);");
		homeImageButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #003f3f, #00ffff); -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand;");
		homeImageButton.setOnMouseEntered(e -> {
			homeImageButton.setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		homeImageButton.setOnMouseExited(e -> {
			homeImageButton.setStyle("-fx-background-color: linear-gradient(to bottom right, #003f3f, #00ffff); -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		homeImageButton.relocate(50, 250);
		homePane.getChildren().addAll(homeImageView, homeImageText, homeImageDescription, homeImageButton);
		this.add(homePane, 0, 1, 4, 1);
	}

}
