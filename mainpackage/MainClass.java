package mainpackage;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application {

	public static void main(String[] args) {
		
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setResizable(false);
		primaryStage.setScene(new LoginScene(primaryStage));
		primaryStage.setTitle("HCI Project");
		primaryStage.sizeToScene();
		primaryStage.show();
		
	}

}
