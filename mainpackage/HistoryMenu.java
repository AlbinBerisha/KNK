package mainpackage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class HistoryMenu extends VBox {
	
	public HistoryMenu(RentalHistoryStage rentalHistoryWindow, MainScene ms) {
		super();
		this.setup(rentalHistoryWindow, ms);
	}
	
	private void setup(RentalHistoryStage rentalHistoryWindow, MainScene ms) {
		Text historyHeader = new Text("Your rental history");
		historyHeader.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
		RentTableView tableView = new RentTableView(ms.getUsername());
		HBox newTabButtonHBox = new HBox();
		Button newTabButton = new Button("Show in a new tab");
		newTabButtonHBox.getChildren().add(newTabButton);
		newTabButtonHBox.setAlignment(Pos.CENTER_RIGHT);
		newTabButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		newTabButton.setOnMouseEntered(e -> {
			newTabButton.setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		newTabButton.setOnMouseExited(e -> {
			newTabButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		newTabButton.setOnAction(e -> {
			rentalHistoryWindow.setup(ms.getUsername());
			rentalHistoryWindow.show();
		});
		
		this.getChildren().addAll(historyHeader, tableView, newTabButtonHBox);
		this.setStyle("-fx-padding: 20px; -fx-spacing: 20px;");
	}
	
}
