package mainpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CarSection extends GridPane {
	
	Statement statement;
	int carID;
	
	public CarSection(int carID) {
		super();
		this.carID = carID;
		this.statement = DBConnection.createStatement();
		this.setup();
	}
	
	private void setup() {
		Image carImage;
		ImageView carImageView = new ImageView();
		ResultSet resultSet;
		Text carModel = new Text();
		try {
			resultSet = this.statement.executeQuery("select * from cars;");
			while(resultSet.next())
				if(this.carID == resultSet.getInt("carID")) {
					carModel.setText(resultSet.getString("carManufacturer") + ' ' + resultSet.getString("carModel"));
					carImage = new Image(resultSet.getString("carImagePath"));
					carImageView.setImage(carImage);
					carImageView.setPreserveRatio(true);
					carImageView.setFitWidth(550);
					break;
				}
		} catch (SQLException e) {
			carImageView = new ImageView();
			e.printStackTrace();
		}
		
		carModel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
		
		this.add(carImageView, 0, 0, 1, 2);
		this.add(carModel, 1, 0);
		this.setStyle("-fx-background-color: #0000005f; -fx-hgap: 275px; -fx-padding: 20px;");
	}

}
