package eth;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MyLogin {
	
	private static String name = new String();
	private static String pw   = new String();
	private static String datenquelle = new String();
	private static boolean sucessfully = false;
	private static boolean canceled = false;
	private static boolean showGalery = false;
	
	public String getname() {
		return this.name;
	}
	
	public String getDatenquelle() {
		return this.datenquelle;
	}

	public String getPW() {
		return this.pw;
	}
	
	public boolean loginSucessfully() {
		return this.sucessfully;
	}
	
	public boolean loginCanceled() {
		return this.canceled;
	}
	
	public boolean showGalery() {
		return this.showGalery;
	}
	
	/*
	 * LoginGridPane
	 */
	public MyLogin() {

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));


		// Titel
		final Text scenetitle = new Text("Willkommen zum login");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);

		// Sprache Eingabefeld
		Label sprache = new Label("Sprache:");
		grid.add(sprache, 0, 1);

		final ChoiceBox<String> spracheChoiceField = new ChoiceBox<String>();
		spracheChoiceField.getItems().addAll("Deutsch", "Englisch");
		spracheChoiceField.setValue("Deutsch");
		grid.add(spracheChoiceField, 1, 1);

		// Datenbank Eingabefeld
		Label db = new Label("Datenbank:");
		grid.add(db, 0, 2);

		final ChoiceBox<String> dbChoiceField = new ChoiceBox<String>();
		dbChoiceField.getItems().addAll("Excel", "MySQL");
		dbChoiceField.setValue("MySQL");
		grid.add(dbChoiceField, 1, 2);

		// UserID Eingabefeld
		Label userName = new Label("User:");
		grid.add(userName, 0, 3);

		final TextField userTextField = new TextField("Magnus");
		// final TextField userTextField = new TextField();
		grid.add(userTextField, 1, 3);

		// Password Eingabefeld
		Label pwCode = new Label("Password:");
		grid.add(pwCode, 0, 4);

		final PasswordField pwBox = new PasswordField();
		pwBox.setText("neumann");
		grid.add(pwBox, 1, 4);

		// Anmeldeknopf, füllt Userid und password
		Button btn = new Button("Anmelden");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 0, 6);
		// Anmeldebutton-Text
		final Text actiontargetc = new Text();
		grid.add(actiontargetc, 0, 6);
		
		// Galery-Knopf
		Button btng = new Button("Galery");
		HBox hbBtng = new HBox(10);
		hbBtng.setAlignment(Pos.BOTTOM_LEFT);
		hbBtng.getChildren().add(btng);
		grid.add(hbBtng, 1, 6);
		// Galery-Knopf-Text
		final Text actiontargetg = new Text();
		grid.add(actiontargetg, 1, 6);
		
		// Abbruchknopf
		Button btnc = new Button("Abbruch");
		HBox hbBtnc = new HBox(10);
		hbBtnc.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnc.getChildren().add(btnc);
		grid.add(hbBtnc, 2, 6);
		// Galery-Knopf
		final Text actiontarget = new Text();
		grid.add(actiontarget, 2, 6);
		
		// Michi-Knopf
		Button btnM = new Button("Michi");
		HBox hbBtnM = new HBox(10);
		hbBtnM.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnM.getChildren().add(btnM);
		grid.add(hbBtnM, 3, 6);
		// Galery-Knopf-Text
		final Text actiontargetM = new Text();
		grid.add(actiontargetM, 3, 6);
		
		// Magnus-Knopf
		Button btnMa = new Button("Magnus");
		HBox hbBtnMa = new HBox(10);
		hbBtnMa.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnMa.getChildren().add(btnMa);
		grid.add(hbBtnMa, 4, 6);
		// Galery-Knopf-Text
		final Text actiontargetMa = new Text();
		grid.add(actiontargetMa, 4, 6);
		
		// Jens-Knopf
		Button btnJ = new Button("Jens");
		HBox hbBtnJ = new HBox(10);
		hbBtnJ.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnJ.getChildren().add(btnJ);
		grid.add(hbBtnJ, 5, 6);
		// Galery-Knopf-Text
		final Text actiontargetJ = new Text();
		grid.add(actiontargetJ, 5, 6);
		
		// Profil-Knopf
		Button btnP = new Button("Profil");
		HBox hbBtnP = new HBox(10);
		hbBtnP.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnP.getChildren().add(btnP);
		grid.add(hbBtnP, 6, 6);
		// Galery-Knopf-Text
		final Text actiontargetP = new Text();
		grid.add(actiontargetP, 6, 6);
		
		Scene scene = new Scene(grid);
		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("User-Login");
		primaryStage.setScene(scene);
		
		btnc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontargetc.setId("anmeldeknopf");
				//Login-Fenster schliessen
				canceled = true;
				Stage stage = (Stage) grid.getScene().getWindow();
				stage.close();
			}});
		
		btnM.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontargetc.setId("anmeldeknopf");
				//auf Michaele umschalten
				pwBox.setText("island");
				userTextField.setText("Michaele");

			}});
		
		btnMa.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontargetc.setId("anmeldeknopf");
				//auf Magnus umschalten
				pwBox.setText("neumann");
				userTextField.setText("Magnus");

			}});
		
		btnJ.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontargetc.setId("anmeldeknopf");
				//auf Jens umschalten
				pwBox.setText("azrael");
				userTextField.setText("Jens");

			}});
		
		btnP.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontargetc.setId("anmeldeknopf");
				//Profil-Pane anzeigen
				MyProfile profile = new MyProfile(getname(), getPW());
				

			}});
		
		btng.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontargetc.setId("anmeldeknopf");
				//Login-Fenster schliessen
				showGalery = true;
				name = userTextField.getText();
				String spracheChoice = spracheChoiceField.getValue();
				String database = dbChoiceField.getValue();
				datenquelle = database;
				String sprache = new String();
				
				switch (spracheChoice) {
				case "Deutsch":
					sprache = "DE";
					break;
				case "Englisch":
					sprache = "EN";
					break;
				default:
					sprache = "DE";
				}

				CharSequence password = pwBox.getCharacters();
				pw = password.toString();

				// User Anmeldung prüfen und User_Gruppe zuordnen

				switch (database) {
					case "MySQL":
						sucessfully = MyGameSQLConnection.pruefeLogin(name, pw);
						break;
					case "Excel":
						sucessfully = true;
						break;
					default:
						sucessfully = false;
						break;
				}
								
				if (!sucessfully){
					System.out.println("Anmeldung fehlerhaft oder Datenbank nicht vorhanden !");
				}
				
				//System.out.println("User_name    : " + getname());
				//System.out.println("User_pw      : " + getPW());
				//System.out.println("erfolgreich  : " + loginSucessfully());
				
				//Login-Fenster schliessen
				Stage stage = (Stage) grid.getScene().getWindow();
				stage.close();
				//primaryStage.close();
			
			}});
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontarget.setId("anmeldeknopf");
				name = userTextField.getText();
				String spracheChoice = spracheChoiceField.getValue();
				String database = dbChoiceField.getValue();
				datenquelle = database;
				showGalery = false;
				String sprache = new String();
				
				switch (spracheChoice) {
				case "Deutsch":
					sprache = "DE";
					break;
				case "Englisch":
					sprache = "EN";
					break;
				default:
					sprache = "DE";
				}

				CharSequence password = pwBox.getCharacters();
				pw = password.toString();

				// User Anmeldung prüfen und User_Gruppe zuordnen

				switch (database) {
					case "MySQL":
						sucessfully = MyGameSQLConnection.pruefeLogin(name, pw);
						break;
					case "Excel":
						sucessfully = true;
						break;
					default:
						sucessfully = false;
						break;
				}
								
				if (!sucessfully){
					System.out.println("Anmeldung fehlerhaft oder Datenbank nicht vorhanden !");
				}
				
				System.out.println("User_name    : " + getname());
				System.out.println("User_pw      : " + getPW());
				System.out.println("erfolgreich  : " + loginSucessfully());
				System.out.println("Lv/Thema  : " + MyGame.level + MyGame.thema);
				
				//Login-Fenster schliessen
				Stage stage = (Stage) grid.getScene().getWindow();
				stage.close();
				//primaryStage.close();
			}
			});
		
		
		primaryStage.showAndWait();
	}
	
	


}
