package mainpackage;

import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class RentalHistoryStage extends Stage {
	
	private Scene scene;

	public RentalHistoryStage(String username) {
		super();
		this.setup(username);
	}
	
	public void setup(String username) {
		this.scene = new Scene(new RentTableView(username), 720, 480);
		
		this.setX((Screen.getPrimary().getVisualBounds().getWidth()-720)/2);
		this.setY((Screen.getPrimary().getVisualBounds().getHeight()-480)/2);
		this.setTitle("Rental history");
		this.setScene(scene);
		this.sizeToScene();
		this.setResizable(false);
	}
	
}
