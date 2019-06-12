package mainpackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RentTableView extends TableView<RentRecord> {
	
	private Statement statement;
	
	public RentTableView(String username) {
		super();
		this.statement = DBConnection.createStatement();
		this.setup(username);
	}
	
	private void setup(String username) {
		ResultSet rSet;
		try {
			rSet = this.statement.executeQuery("SELECT * FROM rentHistory WHERE uUsername = '" + username + "';");
		} catch (SQLException e1) {
			rSet = null;
			e1.printStackTrace();
		}
		TableColumn<RentRecord, Integer> idColumn = new TableColumn<RentRecord, Integer>("ID");
		TableColumn<RentRecord, String> orderDateColumn = new TableColumn<RentRecord, String>("Order date");
		TableColumn<RentRecord, String> carColumn = new TableColumn<RentRecord, String>("Car");
		TableColumn<RentRecord, String> locationColumn = new TableColumn<RentRecord, String>("Location");
		TableColumn<RentRecord, String> pick_upDateColumn = new TableColumn<RentRecord, String>("Pick-up date");
		TableColumn<RentRecord, String> drop_offDateColumn = new TableColumn<RentRecord, String>("Drop-off date");
		
		this.getColumns().add(idColumn);
		this.getColumns().add(orderDateColumn);
		this.getColumns().add(carColumn);
		this.getColumns().add(locationColumn);
		this.getColumns().add(pick_upDateColumn);
		this.getColumns().add(drop_offDateColumn);
		this.setEditable(false);
		idColumn.setCellValueFactory(new PropertyValueFactory<RentRecord, Integer>("id"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<RentRecord, String>("orderDate"));
		carColumn.setCellValueFactory(new PropertyValueFactory<RentRecord, String>("car"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<RentRecord, String>("location"));
		pick_upDateColumn.setCellValueFactory(new PropertyValueFactory<RentRecord, String>("pick_upDate"));
		drop_offDateColumn.setCellValueFactory(new PropertyValueFactory<RentRecord, String>("drop_offDate"));
		
		try {
			while(rSet.next())
				this.getItems().add(new RentRecord(rSet.getInt("rID"), rSet.getDate("rOrderDate"), rSet.getString("rCar"), rSet.getString("rLocation"), rSet.getDate("rPick_upDate"), rSet.getDate("rDrop_offDate")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
