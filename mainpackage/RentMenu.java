package mainpackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RentMenu extends GridPane {
	
	private Statement statement;

	public RentMenu(MainScene ms) {
		super();
		this.statement = DBConnection.createStatement();
		this.setup(ms);
	}
	
	private void setup(MainScene ms) {
		Text header = I18N.getText("rentHeaderText");
		ComboBox<String> countryCB = new ComboBox<String>(FXCollections.observableArrayList("Albania", "Kosovo", "Montenegro", "North Macedonia"));
		ComboBox<String> cityCB = new ComboBox<String>(FXCollections.observableArrayList("Tirana", "Prishtina", "Podgorica", "Skopje"));
		DatePicker pick_upDatePicker = new DatePicker(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), (LocalDate.now().getDayOfMonth()+1 <= LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()))?(LocalDate.now().getDayOfMonth()+1):((LocalDate.now().getDayOfMonth()+1)%LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()))));
		DatePicker drop_offDatePicker = new DatePicker(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), (LocalDate.now().getDayOfMonth()+4 <= LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()))?(LocalDate.now().getDayOfMonth()+4):((LocalDate.now().getDayOfMonth()+4)%LocalDate.now().getMonth().length(LocalDate.now().isLeapYear()))));
		ComboBox<String> carCB = new ComboBox<String>();
		VBox carImageVBox = new VBox();
		Text carName = new Text();
		Image carImage = new Image("/images/Audi Q5.png", 720, 480, true, true);
		ImageView carImageView = new ImageView(carImage);
		HBox rentButtonHBox = new HBox();
		Button rentButton = new Button("Rent");
		
		header.setFont(Font.font("Tahoma", FontWeight.NORMAL, 50));
		countryCB.setValue("Albania");
		cityCB.setValue("Tirana");
		carCB.setValue("Audi Q5");
		carName.setText(carCB.getValue());
		carImageVBox.setStyle("-fx-background-color: #0000005f; -fx-padding: 20px;");
		carImageVBox.getChildren().addAll(carName, carImageView);
		rentButtonHBox.getChildren().add(rentButton);
		rentButtonHBox.setAlignment(Pos.CENTER_RIGHT);
		rentButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		rentButton.setOnMouseEntered(e -> {
			rentButton.setStyle("-fx-background-color: #000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: aqua;");
		});
		rentButton.setOnMouseExited(e -> {
			rentButton.setStyle("-fx-background-color: #00000000; -fx-border-width: 1px; -fx-border-style: solid; -fx-border-color: black; -fx-cursor: hand; -fx-text-fill: black;");
		});
		rentButton.setOnAction(e -> {
			try {
				this.statement.executeUpdate("INSERT INTO rentHistory (uUsername, rOrderDate, rCar, rLocation, rPick_upDate, rDrop_offDate) VALUES ('" + ms.getUsername() + "', '" + LocalDate.now().toString() + "', '" + carCB.getValue() + "', '" + cityCB.getValue() + ", " + countryCB.getValue() + "', '" + pick_upDatePicker.getValue() + "', '" + drop_offDatePicker.getValue() + "');");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		carCB.setOnAction(e -> {
			carName.setText(carCB.getValue());
			ResultSet rSet;
			try {
				rSet = this.statement.executeQuery("SELECT carImagePath FROM cars WHERE carManufacturer = '" + carCB.getValue().split(" ")[0] + "' AND carModel = '" + carCB.getValue().split(" ")[1] + ((carCB.getValue().split(" ").length == 3)?(" " + carCB.getValue().split(" ")[2]):"") + "';");
				rSet.next();
				carImageView.setImage(new Image(rSet.getString("carImagePath"), 720, 480, true, true));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		try {
			ResultSet rSet = this.statement.executeQuery("SELECT carManufacturer, carModel FROM cars;");
			while(rSet.next())
				carCB.getItems().add(rSet.getString("carManufacturer") + ' ' + rSet.getString("carModel"));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		this.add(header, 0, 0, 3, 1);
		this.add(I18N.getLabel("countryLabel"), 0, 1);
		this.add(countryCB, 1, 1);
		this.add(I18N.getLabel("cityLabel"), 0, 2);
		this.add(cityCB, 1, 2);
		this.add(I18N.getLabel("pick-upDateLabel"), 0, 3);
		this.add(pick_upDatePicker, 1, 3);
		this.add(I18N.getLabel("drop-offDateLabel"), 0, 4);
		this.add(drop_offDatePicker, 1, 4);
		this.add(I18N.getLabel("carLabel"), 0, 5);
		this.add(carCB, 1, 5);
		this.add(carImageVBox, 2, 1, 1, 6);
		this.add(rentButtonHBox, 0, 6, 2, 1);
		this.setStyle("-fx-padding: 20px; -fx-vgap: 20px; -fx-hgap: 20px;");
	}
	
}
