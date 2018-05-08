package eth;

import javafx.stage.Stage;

public class MyStageController {
	
	private Stage stage = new Stage();
	
	public MyStageController(Stage primaryStage){
		this.stage = primaryStage;
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public void setStage(Stage primaryStage) {
		this.stage = primaryStage;
	}
	
	public void closeStage() {
		this.stage.close();
	}	
	

}
