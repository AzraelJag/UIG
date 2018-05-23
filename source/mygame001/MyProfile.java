package eth;

import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MyProfile {
	
	private static String name 			= new String();
	private static String pw   			= new String();
	private static String email   		= new String();
	private static String sprache   	= new String();
	private static String wallet 		= new String();
	private static String rahmen 		= new String();
	private static BigDecimal balanceUIG 	= new BigDecimal("0");

	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getSprache() {
		return this.sprache;
	}
	
	public String getWallet() {
		return this.wallet;
	}
	
	public String getRahmen() {
		return this.rahmen;
	}
	
	public String getPW() {
		return this.pw;
	}

	
	/*
	 * LoginGridPane
	 */
	public MyProfile(String name, String pw) {
		boolean erfolgreich = false;
		
		this.name = name;
		this.pw   = pw;

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		//Profildaten einlesen
		
		erfolgreich = MyGameSQLConnection.getProfile(this);

		if (erfolgreich) {
			
		// UserID Eingabefeld
		Label userName = new Label("User:");
		grid.add(userName, 0, 1);
		final TextField userTextField = new TextField(MyProfile.name);
		grid.add(userTextField, 1, 1);

		// Password Eingabefeld
		Label pwCode = new Label("Password:");
		grid.add(pwCode, 0, 2);
		final PasswordField pwBox = new PasswordField();
		pwBox.setText(MyProfile.pw);
		grid.add(pwBox, 1, 2);
		
		// Sprache Eingabefeld
		Label sprache = new Label("Sprache:");
		grid.add(sprache, 0, 3);
		final TextField spracheTextField = new TextField(MyProfile.sprache);
		grid.add(spracheTextField, 1, 3);
		
		// Email Eingabefeld
		Label email = new Label("Email:");
		grid.add(email, 0, 4);
		final TextField emailTextField = new TextField(MyProfile.email);
		emailTextField.setMinWidth(500);
		grid.add(emailTextField, 1, 4);
		
		// Wallet Eingabefeld
		Label wallet = new Label("Wallet:");
		grid.add(wallet, 0, 5);
		final TextField walletTextField = new TextField(MyProfile.wallet);
		walletTextField.setMinWidth(500);
		grid.add(walletTextField, 1, 5);
		
		// Balance Eingabefeld
		Label balance = new Label("Balance:");
		grid.add(balance, 0, 6);
		final TextField balanceTextField = new TextField(MyProfile.balanceUIG.toString() + " UIG Token");
		balanceTextField.setEditable(false);
		grid.add(balanceTextField, 1, 6);
		
		//BUTTONS

		// Änderung Speichern 
		Button btn = new Button("Änderung speichern");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 7);
		final Text actiontargetc = new Text();
		grid.add(actiontargetc, 1, 7);
		
		// Abbruchknopf
		Button btnc = new Button("Abbruch");
		HBox hbBtnc = new HBox(10);
		hbBtnc.setAlignment(Pos.BOTTOM_LEFT);
		hbBtnc.getChildren().add(btnc);
		grid.add(hbBtnc, 2, 7);
		final Text actiontarget = new Text();
		grid.add(actiontarget, 2, 7);
		
		btnc.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontargetc.setId("anmeldeknopf");
				//Profil-Fenster schliessen
				Stage stage = (Stage) grid.getScene().getWindow();
				stage.close();
			}});
		
		// User-Transaktionshistorie
		Button btntrx = new Button("UIG Transaktionen");
		HBox hbBtntrx = new HBox(10);
		hbBtntrx.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtntrx.getChildren().add(btntrx);
		grid.add(hbBtntrx, 4, 7);
		final Text actiontargettrx = new Text();
		grid.add(actiontargettrx, 4, 7);
		
		btntrx.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				actiontargettrx.setId("anmeldeknopf");
				// füllen User-TransaktionsList
				//UserID ermitteln
				String userid = MyGameSQLConnection.ermittleUserid(walletTextField.getText());
				MyGame.accounts.clear();
				MyGame.accounts.add(new Account(userid, name, pwBox.getText() 
						, walletTextField.getText(),"0"));
				MyGame.trxAcountHistorie.clear();
				MyGame.trxAcountHistorie.add(new AccountTransaktionList(userid));
				
				MyGameSQLConnection.getAccountTransaktionen(MyGame.accounts.get(0).getId(), "ropsten");
				
				MyGame.trxAccountTableView = new TableView<Transaktion>();
				MyGame.trxAccountTableView = TransaktionTableView
						.getTransaktionTableView(MyGame.trxAcountHistorie.get(0).getItemList());
				
				VBox vbox = new VBox();
				vbox.setSpacing(10);
				vbox.autosize();
				VBox.setVgrow(MyGame.trxAccountTableView, Priority.ALWAYS);
				vbox.getChildren().addAll(MyGame.trxAccountTableView);
				Scene scene = new Scene(vbox, 1020, 600);
				Stage primaryStage = new Stage();
				primaryStage.setTitle("UIG Transaktionshistorie: ");
				primaryStage.setScene(scene);
				primaryStage.setX(450);
				primaryStage.setY(150);
				primaryStage.showAndWait();
			}});
		
		}
		
		Scene scene = new Scene(grid);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Profil: " + this.getName());
		primaryStage.setScene(scene);
		primaryStage.showAndWait();

	}

	public void setPW(String pw) {
		this.pw = pw;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSprache(String sprache) {
		this.sprache = sprache;
	}
	
	public void setWallet(String wallet) {
		this.wallet = wallet;
	}
	
	public void setBalanceUIG(BigDecimal balanceUIG ) {
		this.balanceUIG = balanceUIG ;
	}

}
