package eth;

import java.awt.event.ActionListener;

import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import allgemein.Hilfsmethoden;

public class MyGame extends Application {

	// Anwendungsumgebung
	// public static String env = "home";
	public static String env = "work";

	public static int displayX = 1920;
	public static int displayY = 1080;
	public static int anzahlAccounts = 3;
	public static int anzahlWuerfel = 8;
	public static int level = 1;
	public static int thema = 1;
	public static boolean gewonnen = false;

	public static BigDecimal gameBalanceStart = new BigDecimal("1000.0");
	public static BigDecimal gameBalanceUIG = new BigDecimal("1000.0");
	public static StringProperty aktBalanceString = new SimpleStringProperty(
			"GameToken: 0 UIG");

	public static String gameWallet = "0xF21B49C1434AF4ada03F0Aa84D0907CdaF92a82c";
	public static String contract = "0x5ab39784b7ab27692dc2748dadfaefe314a712e5";
	public static ImageView walletImage = new ImageView();

	// Auswahlfelder
	static BorderPane border = null;
	public static ChoiceBox<String> levelChoiceField = new ChoiceBox<String>();
	public static TreeView<String> levelTree = new TreeView<String>();
	public static ChoiceBox<String> anzAccChoiceField = new ChoiceBox<String>();
	public static ChoiceBox<String> spielChoiceField = new ChoiceBox<String>();

	// Pfadangaben
	public static File pfad_data = new File("c://");
	public static String pfad_data_css = new String();
	public static String pfad_data_itemjpg = new String();
	public static String pfad_data_ton = new String();

	public static File pfad_data_vw = new File("c://d/java/data/eth/mygame");
	public static String pfad_data_vw_itemjpg = "c://d/java/data/eth/mygame/Bilder/";
	public static String pfad_data_vw_ton = "c:/d/java/data/eth/mygame/Ton/";
	public static String pfad_data_vw_css = "file:///c:/d/java/data/eth/mygame/";

	// Festplatte
	// public static File pfad_data_home = new File("e://java/data/mygame");
	// public static String pfad_data_home_itemjpg =
	// "e://java/data/mygame/Bilder/";
	// public static String pfad_data_home_ton = "e:/java/data/mygame/Ton/";

	// NAS
	public static File pfad_data_home = new File("z:/mygame");
	public static String pfad_data_home_itemjpg = "z:/mygame/Bilder/";
	public static String pfad_data_home_ton = "z:/mygame/Ton/";
	public static String pfad_data_home_css = ("file:///z:/mygame/");

	// -----------------------------------------------------------------------------
	// Picture Tree:
	// -------------
	public final static String lv11 = "Familie";
	public final static String lv21 = "Ostereier";
	public final static String lv22 = "Shiny";
	public final static String lv23 = "xxx";
	public final static String lv24 = "xxx";
	public final static String lv31 = "Best Pokemon Jens";
	public final static String lv32 = "Hummel";
	public final static String lv33 = "Blumenwiese";
	public final static String lv34 = "Wurzel";
	public final static String lv41 = "Sonnenuntergang";
	public final static String lv42 = "Golf";
	public final static String lv43 = "Best Pokemon Magnus";
	public final static String lv44 = "VW Kraftwerk";
	// -----------------------------------------------------------------------------

	// Cache für die PrimaryStages
	public static ObservableList<MyStageController> accStages = FXCollections
			.observableArrayList();
	public static Stage mainStage = new Stage();

	// Cache für die Transaktionshistorie
	public static ObservableList<Transaktion> trxHistorie = FXCollections
			.observableArrayList();
	static TableView<Transaktion> trxHistorieTableView = new TableView<Transaktion>();

	// Cache für die AccountTransaktionList
	public static ObservableList<AccountTransaktionList> trxAccountHistorie = FXCollections
			.observableArrayList();
	//Anzeige der Usertransaktionshistorie über das Profil
	public static ObservableList<Transaktion> trxAccountList = FXCollections
			.observableArrayList();
	private static TableView<Transaktion> trxAccountTableView = new
	TableView<Transaktion>();

	// Cache für die Accounts
	public static ObservableList<Account> accounts = FXCollections
			.observableArrayList();
	private static TableView<Account> accountsTableView = new TableView<Account>();

	// Cache für die Items
	public static ObservableList<Item> items = FXCollections
			.observableArrayList();
	private static TableView<Item> itemTableView = new TableView<Item>();

	// Cache für die AccountItemList
	public static ObservableList<AccountItemList> accountItemList = FXCollections
			.observableArrayList();
	private static TableView<AccountItemList> AccountItemListTableView = new TableView<AccountItemList>();

	public static List<String> readFileInhalt = new ArrayList<String>();
	public static BigInteger letzterBlock = new BigInteger("0");

	public static int wuerfelAcc = 0;

	// Layouts Rahmen
	/*
	 * public static String cssLayout = "-fx-border-color: grey;\n" +
	 * "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 50, 0, 0, 0);\n"
	 * + "-fx-border-insets: 0.1;\n" + "-fx-border-width: 0.5;\n" +
	 * "-fx-background-color: firebrick, derive(firebrick, 25%);\n" +
	 * "-fx-background-insets: -20, -10;\n";
	 */
	public static void main(String[] args) {
		// Anwendungsumgebung ermitteln und entsprechend initialisieren
		if (env == "home") {
			pfad_data = pfad_data_home;
			pfad_data_itemjpg = pfad_data_home_itemjpg;
			pfad_data_ton = pfad_data_home_ton;
			pfad_data_css = pfad_data_home_css;

			// GameWalletBalance einlesen falls ETH Blockchain erreichbar
			boolean available = ETHReader.availableTestETHRobsten();
			if (available) {
				ETHReader ropstenETHReader = new ETHReader();
				// ropstenETHReader.startETHReader("ropsten");
				gameBalanceUIG = ropstenETHReader.getBalance("ropsten",
						gameWallet, contract);
				aktBalanceString = new SimpleStringProperty("GameToken: "
						+ gameBalanceUIG.intValue() + " UIG");
			}

		} else {
			pfad_data = pfad_data_vw;
			pfad_data_itemjpg = pfad_data_vw_itemjpg;
			pfad_data_ton = pfad_data_vw_ton;
			pfad_data_css = pfad_data_vw_css;
			gameBalanceUIG = gameBalanceStart;
		}

		// Monitorauflösung ermitteln
		displayX = (int) Screen.getPrimary().getVisualBounds().getWidth();
		displayY = (int) Screen.getPrimary().getVisualBounds().getHeight();

		// Wallet Picture
		Image walletImageJpg = new Image("file:///" + pfad_data_itemjpg
				+ "gameWallet.jpg");
		walletImage = new ImageView(walletImageJpg);

		// Anwendung starten
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		// Game-Items einlesen
		boolean dbvorhanden = MyGameSQLConnection.fillItem();
		// dbvorhanden = false;
		if (!dbvorhanden) {
			boolean erfolgreich = readItems();
			if (!erfolgreich)
				Hilfsmethoden.setHinweisDialog(
						"Achtung: GameItems konnten nicht gelesen werden !",
						null, 0);
		}
		// showItemsTableView();
		mainStage = primaryStage;

		TextField aktBalance = new TextField("GameToken: "
				+ aktBalanceString.getValue() + " UIG");
		aktBalance
				.setStyle("-fx-background-color: transparent;-fx-text-fill: WHITE;");
		// HBox.setMargin(aktAuftrag, new Insets(20, 10, 10, 10));
		aktBalance.textProperty().bind(aktBalanceString);

		// Spielmodus auswählen
		spielChoiceField = new ChoiceBox<String>();
		spielChoiceField.getItems().addAll("Tunier", "Solospiel");
		spielChoiceField.setValue("Tunier");
		spielChoiceField.setPrefSize(230, 50);

		Button btnLoadtrxHistorie = new Button("Load Transaktionshistorie");
		btnLoadtrxHistorie.setOnAction(btnLoadtrxHistorieEventListener);
		// HBox.setMargin(btnLoadtrxHistorie, new Insets(15, 0, 0, 5));

		// Button btnLoadAccounts = new Button("Load Accounts            ");
		// btnLoadAccounts.setOnAction(btnLoadAccountsEventListener);
		// HBox.setMargin(btnLoadAccounts, new Insets(15, 0, 0, 5));

		// Game-Items Button
		// Button btnLoadItems = new Button("Load Items               ");
		// btnLoadItems.setOnAction(btnLoadItemsEventListener);
		// HBox.setMargin(btnLoadItems, new Insets(15, 0, 0, 5));

		// Level und Bild auswählen
		TreeItem<String> rootItem = new TreeItem<String>("Puzzle: ");
		rootItem.setExpanded(true);
		TreeItem<String> itemLv1  = new TreeItem<String>("Sonstiges");
		TreeItem<String> itemLv11 = new TreeItem<String>(lv11);
		TreeItem<String> itemLv21 = new TreeItem<String>(lv21);
		itemLv1.getChildren().addAll(itemLv11, itemLv21);
		TreeItem<String> itemLv2a = new TreeItem<String>("Pokemons");
		TreeItem<String> itemLv22 = new TreeItem<String>(lv22);
		TreeItem<String> itemLv23 = new TreeItem<String>(lv23);
		TreeItem<String> itemLv24 = new TreeItem<String>(lv24);
		TreeItem<String> itemLv31 = new TreeItem<String>(lv31);
		TreeItem<String> itemLv43 = new TreeItem<String>(lv43);
		itemLv2a.getChildren().addAll(itemLv22, itemLv31, itemLv43);
		itemLv1.getChildren().addAll(itemLv2a);
		TreeItem<String> itemLv4 = new TreeItem<String>("Fotos");
		TreeItem<String> itemLv4a = new TreeItem<String>("Wolfsburg");
		TreeItem<String> itemLv41 = new TreeItem<String>(lv41);
		TreeItem<String> itemLv42 = new TreeItem<String>(lv42);
		TreeItem<String> itemLv44 = new TreeItem<String>(lv44);
		itemLv4a.getChildren().addAll(itemLv41, itemLv42, itemLv44);
		TreeItem<String> itemLv4b = new TreeItem<String>("Hasselbachtal");
		TreeItem<String> itemLv32 = new TreeItem<String>(lv32);
		TreeItem<String> itemLv33 = new TreeItem<String>(lv33);
		TreeItem<String> itemLv34 = new TreeItem<String>(lv34);
		itemLv4b.getChildren().addAll(itemLv32, itemLv33, itemLv34);
		itemLv4.getChildren().addAll(itemLv4a, itemLv4b);

		// Add to Root
		rootItem.getChildren().addAll(itemLv1, itemLv4);
		levelTree = new TreeView<String>(rootItem);
		itemLv1.setExpanded(true);
		itemLv4.setExpanded(true);
		// VBox.setMargin(levelTree, new Insets(10, 0, 0, 10));

		// Anzahl Spieler beim Tuniermodus auswählen
		anzAccChoiceField = new ChoiceBox<String>();
		anzAccChoiceField.getItems().addAll("1 Spieler", "2 Spieler",
				"3 Spieler");
		anzAccChoiceField.setValue("1 Spieler");
		anzAccChoiceField.setPrefSize(230, 50);
		// VBox.setMargin(anzAccChoiceField, new Insets(15, 0, 0, 5));

		Button btnLoadAccountItems = new Button("Starte Spiel");
		// VBox.setMargin(btnLoadAccountItems, new Insets(15, 0, 0, 5));
		btnLoadAccountItems.setOnAction(btnLoadAccountItemsEventListener);

		Button btnProcessAccountTransaktionList = new Button(
				"Process Transaktionshistorie");
		btnProcessAccountTransaktionList
				.setOnAction(btnProcessAccountTransaktionListEventListener);
		// VBox.setMargin(btnProcessAccountTransaktionList, new Insets(15, 0, 0,
		// 5));

		Button btnSaveAndExit = new Button("Speichern und ENDE");
		btnSaveAndExit.setOnAction(btnSaveAndExitListEventListener);
		// VBox.setMargin(btnSaveAndExit, new Insets(15, 0, 0, 5));

		Button btnShowgameWallet = new Button("GameWalletAdresse");
		btnShowgameWallet.setOnAction(btnShowgameWalletEventListener);
		// VBox.setMargin(btnShowgameWallet, new Insets(15, 0, 0, 5));

		btnLoadtrxHistorie.setPrefSize(230, 50);
		// btnLoadAccounts.setPrefSize(230, 50);
		// btnLoadItems.setPrefSize(230, 50);
		btnLoadAccountItems.setPrefSize(230, 50);
		btnProcessAccountTransaktionList.setPrefSize(230, 50);
		btnSaveAndExit.setPrefSize(230, 50);
		btnShowgameWallet.setPrefSize(230, 50);

		VBox rootBox = new VBox();
		rootBox.getChildren().add(new Label(" "));
		rootBox.getChildren().add(aktBalance);
		rootBox.getChildren().add(new Label(" "));
		// rootBox.getChildren().add(btnLoadtrxHistorie);
		rootBox.getChildren().add(spielChoiceField);
		rootBox.getChildren().add(new Label(" "));
		rootBox.getChildren().add(anzAccChoiceField);
		rootBox.getChildren().add(new Label(" "));
		rootBox.getChildren().add(levelTree);
		rootBox.getChildren().add(new Label(" "));
		// rootBox.getChildren().add(btnLoadItems);
		// rootBox.getChildren().add(btnLoadAccounts);
		// rootBox.getChildren().add(btnProcessAccountTransaktionList);
		rootBox.getChildren().add(new Label(" "));
		rootBox.getChildren().add(btnLoadAccountItems);
		rootBox.getChildren().add(new Label(" "));
		rootBox.getChildren().add(btnSaveAndExit);
		rootBox.getChildren().add(new Label(" "));
		rootBox.getChildren().add(new Label(" "));
		rootBox.getChildren().add(btnShowgameWallet);
		border = new BorderPane();
		border.getStylesheets().add(pfad_data_css + "mygame.css");
		border.setPrefSize(displayX, displayY);
		// border.setStyle("-fx-background-color: #4d66cc ;-fx-text-fill: WHITE;");
		border.autosize();
		VBox.setMargin(rootBox, new Insets(10, 10, 10, 10));
		border.setRight(rootBox);
		Scene scene = new Scene(border);
		// Scene scene = new Scene(rootBox, 249, displayY-50);
		// primaryStage.setFullScreen(true);
		// primaryStage.setAlwaysOnTop(true);
		primaryStage.setMaximized(true);

		primaryStage
				.setTitle("BigPicture würfeln mit UIG Token (c) by Jens Neumann 2018");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	//
	// SPEICHERN und ENDE
	//
	EventHandler<ActionEvent> btnSaveAndExitListEventListener = (
			ActionEvent event) -> {
		if (spielChoiceField.getValue() == "Solospiel") {

			// aktuelle AccountItemList speichern
			try {
				ExcelReadWriter.writeAccountItemTableToXLSXFile(new File(
						pfad_data + "/" + accounts.get(0).getId()
								+ "_Items.xlsx"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			/*
			 * // aktuelle Accountstände speichern try {
			 * ExcelReadWriter.writeAccountTableToXLSXFile(new File(pfad_data +
			 * "/Game001_Accounts.xlsx")); } catch (IOException e1) {
			 * e1.printStackTrace(); }
			 * 
			 * // aktuelle AccountItemList speichern try {
			 * ExcelReadWriter.writeAccountItemTableToXLSXFile(new File(
			 * pfad_data + "/Game001_Account_Items.xlsx")); } catch (IOException
			 * e1) { e1.printStackTrace(); }
			 * 
			 * // aktuelle Account-Transaktionen speichern try { ExcelReadWriter
			 * .writeAccountTransaktionTableToXLSXFile(new File( pfad_data +
			 * "/Game001_Account_Transaktionen.xlsx")); } catch (IOException e1)
			 * { e1.printStackTrace(); }
			 * 
			 * try { ExcelReadWriter .writeAccountTransaktionTableToXLSXFile(new
			 * File( pfad_data + "/Game001_Account_Transaktionen.xlsx")); }
			 * catch (IOException e1) { e1.printStackTrace(); }
			 */
		}

		// alle Stages (Fenster) schließen
		for (int i = 0; i < accStages.size(); i++) {
			accStages.get(i).closeStage();
		}

		mainStage.close();

	};

	//
	// GameWallet anzeigen
	//
	EventHandler<ActionEvent> btnShowgameWalletEventListener = (
			ActionEvent event) -> {

		Hilfsmethoden
				.setGameWalletDialog(
						"UIG Wallet Adresse: \n 0xF21B49C1434AF4ada03F0Aa84D0907CdaF92a82c",
						walletImage);
	};

	//
	// LoadButton -> Transaktionshistorie-Datei einlesen und im TableView
	// darstellen
	//
	EventHandler<ActionEvent> btnLoadtrxHistorieEventListener = (
			ActionEvent event) -> {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("TXT files (*.TXT)", "*.TXT"),
				new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"));

		fileChooser.setInitialDirectory(pfad_data);
		File file = fileChooser.showOpenDialog(null);

		trxHistorie.clear();
		if (file != null) {
			readFileInhalt = Hilfsmethoden.readLineByLineJava8(file.getPath()
					.toString());
			// Hilfsmethoden.setHinweisDialog("Datei wurde eingelesen");

			// letzerGelesenerBlock
			letzterBlock = new BigInteger(readFileInhalt.get(0));
			// System.out.println("letzter Block : " + letzterBlock);

			// Transaktionshistorie aus Contract-Sicht
			for (int i = 1; i < readFileInhalt.size(); i++) {
				Transaktion trx = new Transaktion(readFileInhalt.get(i));
				trxHistorie.add(trx);
			}
		}

		trxHistorieTableView = new TableView<Transaktion>();
		trxHistorieTableView = TransaktionTableView
				.getTransaktionTableView(trxHistorie);
		/*
		 * VBox vbox = new VBox(); vbox.setSpacing(10); vbox.autosize();
		 * VBox.setVgrow(trxHistorieTableView, Priority.ALWAYS);
		 * vbox.getChildren().addAll(trxHistorieTableView); Scene scene = new
		 * Scene(vbox, 1200, 900); Stage primaryStage = new Stage();
		 * primaryStage.setTitle("Transaktionshistorie");
		 * primaryStage.setScene(scene); primaryStage.show();
		 */
	};

	//
	// LoadButton -> Item-Datei einlesen und im TableView darstellen
	//
	// EventHandler<ActionEvent> btnLoadItemsEventListener = (ActionEvent event)
	// -> {

	private static boolean readItems() {
		// Items über den File-Chooser einlesen
		/*
		 * FileChooser fileChooser = new FileChooser();
		 * fileChooser.getExtensionFilters() .addAll(new
		 * FileChooser.ExtensionFilter("XLSX files (*.XLSX)", "*.XLSX"), new
		 * FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"));
		 * 
		 * fileChooser.setInitialDirectory(pfad_data); File file =
		 * fileChooser.showOpenDialog(null);
		 */

		boolean erfolgreich = true;
		items.clear();

		File file = new File(pfad_data + "/Game001_Items.xlsx");
		if (file != null) {
			try {
				ExcelReadWriter.readXLSXFileToItemTable(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				erfolgreich = false;
			}
			// Hilfsmethoden.setHinweisDialog("Datei wurde eingelesen");
		}

		itemTableView = new TableView<Item>();
		itemTableView = ItemTableView.getItemTableView(items);

		/*
		 * VBox vbox = new VBox(); vbox.setSpacing(10); vbox.autosize();
		 * VBox.setVgrow(itemTableView, Priority.ALWAYS);
		 * vbox.getChildren().addAll(itemTableView); Scene scene = new
		 * Scene(vbox, 900, 900); Stage primaryStage = new Stage();
		 * primaryStage.setTitle("Items"); primaryStage.setScene(scene);
		 * primaryStage.show();
		 */
		return erfolgreich;
	};

	// =============================================================================================
	// SPIELSTART
	// =============================================================================================
	EventHandler<ActionEvent> btnLoadAccountItemsEventListener = (
			ActionEvent event) -> {

		gewonnen = false;

		/*
		 * FileChooser fileChooser = new FileChooser();
		 * fileChooser.getExtensionFilters() .addAll(new
		 * FileChooser.ExtensionFilter("XLSX files (*.XLSX)", "*.XLSX"), new
		 * FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"));
		 * 
		 * fileChooser.setInitialDirectory(pfad_data); File file =
		 * fileChooser.showOpenDialog(null);
		 */

		// Level und Thema ermitteln
		try {
			String levelString = levelTree.getSelectionModel()
					.getSelectedItem().getValue();

			switch (levelString) {
			case lv11:
				level = 1;
				thema = 1;
				break;
			case lv21:
				level = 2;
				thema = 1;
				break;
			case lv22:
				level = 2;
				thema = 2;
				break;
			// case lv23:
			// level = 2;
			// thema = 3;
			// break;
			// case lv24:
			// level = 2;
			// thema = 4;
			// break;
			case lv31:
				level = 3;
				thema = 1;
				break;
			case lv32:
				level = 3; 
				thema = 2; 
				break; 
			case lv33:
				level = 3;
				thema = 3; 
				break; 
			case lv34: 
				level = 3; 
				thema = 4; 
				break;
			case lv41:
				level = 4;
				thema = 1;
				break;
			case lv42:
				level = 4;
				thema = 2;
				break;
			case lv43:
				level = 4;
				thema = 3;
				break;
			case lv44:
				level = 4;
				thema = 4;
				break;
			default:
				level = 1;
				thema = 1;
				break;
			}
		} catch (Exception e) {
			level = 1;
			thema = 1;
		}

		// Accounts, AccountItems und AccountTransaktionen laden, wenn kein
		// "Tunier"-Modus eingestellt ist
		if (spielChoiceField.getValue().equals("Solospiel")) {
			/*
			 * // neue Transaktionen von der Blockchain ermitteln und den
			 * Accounts // gutschreiben boolean available =
			 * ETHReader.availableTestETHRobsten(); if (available) {
			 * System.out.println("starte Transaktionstask ...");
			 * ETHReader.starteTransaktionsTask("ropsten"); }
			 */

			// Anzahl Accounts setzen
			anzahlAccounts = 1;

			// LoginPane -> UserLogin prüfen und Account + Account-Items laden
			boolean loginOK = false;
			boolean canceled = false;
			boolean showGalery = false;
			while (!loginOK && !canceled){
				MyLogin login = new MyLogin();
				//GridPane grid = login.MyLogin();
				//border.setCenter(grid);

				loginOK = login.loginSucessfully();
				canceled = login.loginCanceled();
				showGalery = login.showGalery();
				
				

				if (!login.loginSucessfully()) {
					Hilfsmethoden.setHinweisDialog(
							"Account nicht bekannt, bitte prüfen !!!", null, 0);
				} else {
					// Login erfolgreich
					// Account und Account-Item laden
					// System.out.println ("lname:" + login.getname());
					// System.out.println ("lpw:" + login.getPW());

					loadAccount(login.getname(), login.getPW(),
							login.getDatenquelle());

					loadAccountItems(accounts.get(0).getId(),
							login.getDatenquelle());
					// System.out.println("AnzItems nachLoad: " +
					// MyGame.accountItemList.get(0).getItemList().size());
					// showAccountItemsTableView(accounts.get(0).getId());

					if (showGalery) {
						Stage mygalery = MyGalery
								.getGaleryStage(accountItemList.get(0)
										.getItemList());
						mygalery.showAndWait();
					}
				}
			}

			
			if (canceled) {
				// Anwendung beenden
				// alle Stages (Fenster) schließen
				for (int i = 0; i < accStages.size(); i++) {
					accStages.get(i).closeStage();
				}
				mainStage.close();
			}
			// falls Tuniermodus ausgewöhlt, dann DefaultAccounts aufbauen
		} else {
			anzahlAccounts = Integer.parseInt(anzAccChoiceField.getValue()
					.substring(0, 1));
			initialisiereTunierAccounts(Integer.parseInt(anzAccChoiceField
					.getValue().substring(0, 1)));
		}

		// -------------------------------------------------------------
		// Spielfeld aufbauen
		// -------------------------------------------------------------
		for (int i = 0; i < accounts.size() && i < anzahlAccounts; i++) {
			if (!spielChoiceField.getValue().equals("Solospiel")) {
				wuerfelAcc = i;
			}
			// System.out.println("Würfel-Acc:" + wuerfelAcc);
			AccountItemListTableView = new TableView<AccountItemList>();

			AccountItemListTableView = AccountItemTableView
					.getAccountItemTableView(accountItemList,
							accounts.get(wuerfelAcc).getId());

			VBox vbox = new VBox();
			vbox.setSpacing(10);
			vbox.autosize();
			// AccountItem-TableView
			VBox.setVgrow(AccountItemListTableView, Priority.ALWAYS);
			// vbox.getChildren().add(AccountItemListTableView);

			Boolean gefunden = false;
			for (int k = 0; k < accountItemList.size() && !gefunden; k++) {
				if (accounts.get(wuerfelAcc).getId()
						.equals(accountItemList.get(k).getId())) {
					gefunden = true;
					ObservableList<AccountItemList> accountItemListCopy = accountItemList;
					VBox vboxPuzzle = getbildBox(accountItemListCopy.get(k),
							level);
					vboxPuzzle.setAlignment(Pos.CENTER);
					vbox.getChildren().add(vboxPuzzle);
					vbox.setAlignment(Pos.CENTER);
				}
			}
			Button btnGamble = new Button("würfeln");
			btnGamble.setPrefSize(150, 50);
			
			// Sell Button anzeigen, falls mind. eine Karte >4 mal vorhanden
			// ist
			Button btnSell = new Button("Verkaufen");
			btnSell.setPrefSize(150, 50);
			btnSell.setId("SellButton");
			btnSell.addEventHandler(ActionEvent.ACTION, new MyActionListener(
					wuerfelAcc) {
				@Override
				public void handle(ActionEvent event) {
					// System.out.println("Verkaufen für Account " +
					// this.getId());
					Boolean gefunden = false;
					for (int i = 0; i < accountItemList.get(this.getId())
							.getItemList().size()
							&& !gefunden; i++) {
						if (accountItemList.get(this.getId()).getItemList()
								.get(i).getCount().longValue() > 2) {
							// System.out.println("Du hast ein Set verkauft !!! AccID: "
							// + this.getId());

							// vom Item Anzahl 2 abziehen
							accountItemList.get(this.getId()).getItemList()
									.get(i).subCount(new BigDecimal("2.0"));

							// Karte auswählen
							Image image = new Image("file:///" + pfad_data_itemjpg
									+ "uig1.jpg");
							ImageView imgView = new ImageView(image);

							String auswahlItem = Hilfsmethoden
									.setAuswahlDialog("Kaufkarte", imgView, level,
											this.getId());
							String auswahlAccId = new String();
							String levelString = String.valueOf(level);
							String themaString = String.valueOf(thema);
							switch (levelString + themaString) {
							case "11":
								switch (auswahlItem) {
								case "Position 1/1":
									auswahlAccId = "ITEM00000001";
									break;
								case "Position 1/2":
									auswahlAccId = "ITEM00000002";
									break;
								case "Position 2/1":
									auswahlAccId = "ITEM00000003";
									break;
								case "Position 2/2":
									auswahlAccId = "ITEM00000004";
									break;
								default:
									auswahlAccId = "ITEM00000000";
									break;
								}
							default:
								auswahlAccId = "ITEM0" + level + "0" + thema
										+ "0" + auswahlItem.substring(9, 12);
								break;
							}
							// accountItemList.get(this.getId()).getItemList()
							// .get(i).subCount(new BigDecimal("2"));

							boolean dbsave = false;
							if (spielChoiceField.getValue().equals("Solospiel"))
								dbsave = true;
							accountItemList.get(this.getId()).addItem(
									auswahlAccId, "1", dbsave);

							boolean guthabenVorhanden = accounts.get(
									this.getId()).subBalance(
									new BigDecimal("1.0"));
							if (!guthabenVorhanden) {
								Hilfsmethoden
										.setkeinGuthabenDialog(
												"UIG: WalletAdresse: \n 0xF21B49C1434AF4ada03F0Aa84D0907CdaF92a82c",
												walletImage);
							} else {
								gameBalanceUIG = gameBalanceUIG
										.add(new BigDecimal("1.0"));

								// beim SoloSpiel über die Datenbank
								if (spielChoiceField.getValue().equals(
										"Solospiel"))
									MyGameSQLConnection
											.userBalanceGutschreiben(accounts
													.get(this.getId()).getId(),
													"-1.0");

								aktBalanceString.setValue("GameToken: "
										+ gameBalanceUIG.intValue() + " UIG");
							}

							gefunden = true;
							VBox vbox = new VBox();
							ObservableList<AccountItemList> accountItemListCopy = accountItemList;
							VBox vboxPuzzle = getbildBox(
									accountItemListCopy.get(this.getId()),
									level);
							vboxPuzzle.setAlignment(Pos.CENTER);
							vbox.getChildren().add(vboxPuzzle);
							vbox.setAlignment(Pos.CENTER);
							if (!MyGame.gewonnen)
								vbox.getChildren().add(btnGamble);
							setAccStage(vbox, this.getId());
						}
					}
					if (!gefunden)
						Hilfsmethoden
								.setGewinnDialog(
										"Du hast leider noch keine 4 gleichen Karten zum Verkauf",
										null);
				}
			});

			btnGamble.addEventHandler(ActionEvent.ACTION, new MyActionListener(
					wuerfelAcc) {
				@Override
				public void handle(ActionEvent event) {
					// System.out.println("würfeln für Account " +
					// this.getId());
					// System.out.println("====================================");
					String musicFile = pfad_data_ton + "wuerfelbecher.mp3";
					Media sound = new Media(new File(musicFile).toURI()
							.toString());
					MediaPlayer mediaPlayer = new MediaPlayer(sound);
					mediaPlayer.play();

					Random rand = new Random();
					int value = rand.nextInt(level * 8) + 1;

					// 1 UIG nach dem Würfeln abziehen

					// beim Solospiel über die Datenbank
					if (spielChoiceField.getValue().equals("Solospiel"))
						MyGameSQLConnection.userBalanceGutschreiben(accounts
								.get(this.getId()).getId(), "-1.0");

					boolean guthabenVorhanden = accounts.get(this.getId())
							.subBalance(new BigDecimal("1.0"));
					if (!guthabenVorhanden) {
						Hilfsmethoden
								.setkeinGuthabenDialog(
										"UIG: WalletAdresse: \n 0xF21B49C1434AF4ada03F0Aa84D0907CdaF92a82c",
										walletImage);
					} else {
						gameBalanceUIG = gameBalanceUIG.add(new BigDecimal(
								"1.0"));
						aktBalanceString.setValue("GameToken: "
								+ gameBalanceUIG.intValue() + " UIG");
					}

					boolean gewonnen = false;
					for (int i = 0; i < items.size(); i++) {
						if (       (value == items.get(i).getWuerfelpos())
								&& (thema == items.get(i).getWertigkeit())
								&& (level == items.get(i).getLevel())) {

							gewonnen = true;
							musicFile = pfad_data_ton + "gewinn.mp3";
							Media Gsound = new Media(new File(musicFile)
									.toURI().toString());
							mediaPlayer = new MediaPlayer(Gsound);
							mediaPlayer.play();

							boolean gefunden = false;
							for (int y = 0; y < accountItemList.size()
									&& !gefunden; y++) {
								if (accountItemList
										.get(y)
										.getId()
										.equals(accounts.get(this.getId())
												.getId())) {
									boolean dbsave = false;
									if (spielChoiceField.getValue().equals(
											"Solospiel"))
										dbsave = true;
									accountItemList.get(y).addItem(
											items.get(i).getId(), "1", dbsave);
									gefunden = true;
								}
							}

							AccountItemListTableView = new TableView<AccountItemList>();

							AccountItemListTableView = AccountItemTableView
									.getAccountItemTableView(accountItemList,
											accounts.get(this.getId()).getId());

							VBox vbox = new VBox();
							vbox.setSpacing(10);
							vbox.autosize();
							vbox.getStylesheets().add(
									pfad_data_css + "mygame.css");
							// AccountItem-TableView
							VBox.setVgrow(AccountItemListTableView,
									Priority.ALWAYS);

							gefunden = false;
							for (int k = 0; k < accountItemList.size()
									&& !gefunden; k++) {

								if (accounts.get(this.getId()).getId()
										.equals(accountItemList.get(k).getId())) {
									gefunden = true;
									ObservableList<AccountItemList> accountItemListCopy = accountItemList;
									VBox vboxPuzzle = getbildBox(
											accountItemListCopy.get(k), level);
									vboxPuzzle.setAlignment(Pos.CENTER);
									vbox.getChildren().add(vboxPuzzle);
									vbox.setAlignment(Pos.CENTER);
									vbox.getStylesheets().add(
											pfad_data_css + "mygame.css");
								}
							}
							if (!MyGame.gewonnen) {
								vbox.getChildren().add(btnGamble);
								if (sellable(accountItemList.get(this.getId()).getItemList(), level, thema, this.getId()))
									vbox.getChildren().add(btnSell);
							}

							// setAccStage(vbox, this.getId());

							Image image = new Image("file:///"
									+ pfad_data_itemjpg + "/L" + level + thema
									+ "/" + items.get(i).getName());
							ImageView imgView = new ImageView(image);
							imgView.setFitHeight(100);
							imgView.setFitWidth(100);
							imgView.setFitWidth(100);
							imgView.setStyle("-fx-background-color: #336699;");
							if (anzahlAccounts > 1) {
								Hilfsmethoden.setHinweisDialog("gewonnen "
										+ items.get(i).getName(), imgView,
										this.getId());
							} else {
								Hilfsmethoden.setHinweisDialog("gewonnen "
										+ items.get(i).getName(), imgView, 0);
							}
							setAccStage(vbox, this.getId());
						}
					}

					boolean hauptgewinn = false;
					if ((level == 2) && (value == (level * 8) - 1)) {
						hauptgewinn = true;
					}
					if (value == level * 8
							|| (level == 4 && value == (level * 8) - 1)
							|| (level == 4 && value == (level * 8) - 2)
							|| hauptgewinn) {
						gewonnen = true;

						// beim SoloSpiel über die Datenbank
						if (spielChoiceField.getValue().equals("Solospiel"))
							MyGameSQLConnection.userBalanceGutschreiben(
									accounts.get(this.getId()).getId(), "3.0");

						accounts.get(this.getId()).addBalance(
								new BigDecimal("3.0"));

						gameBalanceUIG = gameBalanceUIG
								.subtract(new BigDecimal("3.0"));
						aktBalanceString.setValue("GameToken: "
								+ gameBalanceUIG.intValue() + " UIG");

						musicFile = pfad_data_ton + "gewinn.mp3";
						sound = new Media(new File(musicFile).toURI()
								.toString());
						mediaPlayer = new MediaPlayer(sound);
						mediaPlayer.play();

						Image image = new Image("file:///" + pfad_data_itemjpg
								+ "uig1.jpg");
						ImageView imgView = new ImageView(image);

						String auswahlItem = new String();
						if (anzahlAccounts > 1) {
							auswahlItem = Hilfsmethoden.setAuswahlDialog(
									"Hauptgewinn " + "Treffe Deine Wahl:",
									imgView, level, this.getId());
						} else {
							auswahlItem = Hilfsmethoden.setAuswahlDialog(
									"Hauptgewinn " + "Treffe Deine Wahl:",
									imgView, level, 0);
						}

						String auswahlAccId = new String();
						String levelString = String.valueOf(level);
						String themaString = String.valueOf(thema);

						switch (levelString + themaString) {
						case "11":
							switch (auswahlItem) {
							case "Position 1/1":
								auswahlAccId = "ITEM00000001";
								break;
							case "Position 1/2":
								auswahlAccId = "ITEM00000002";
								break;
							case "Position 2/1":
								auswahlAccId = "ITEM00000003";
								break;
							case "Position 2/2":
								auswahlAccId = "ITEM00000004";
								break;
							default:
								auswahlAccId = "ITEM00000000";
								break;
							}
						default:
							auswahlAccId = "ITEM0" + level + "0" + thema + "0"
									+ auswahlItem.substring(9, 12);
							break;
						}

						boolean gefunden = false;
						for (int z = 0; z < accountItemList.size() && !gefunden; z++) {
							if (accountItemList.get(z).getId()
									.equals(accounts.get(this.getId()).getId())) {
								boolean dbsave = false;
								if (spielChoiceField.getValue().equals(
										"Solospiel"))
									dbsave = true;
								accountItemList.get(z).addItem(auswahlAccId,
										"1", dbsave);
								gefunden = true;
							}
						}

						AccountItemListTableView = new TableView<AccountItemList>();
						// System.out.println("WürfelAccount-2 " +
						// this.getId());
						AccountItemListTableView = AccountItemTableView
								.getAccountItemTableView(accountItemList,
										accounts.get(this.getId()).getId());

						VBox vbox = new VBox();
						vbox.setSpacing(10);
						vbox.autosize();
						// AccountItem-TableView
						VBox.setVgrow(AccountItemListTableView, Priority.ALWAYS);
						// vbox.getChildren().add(AccountItemListTableView);
						gefunden = false;
						for (int k = 0; k < accountItemList.size() && !gefunden; k++) {

							if (accounts.get(this.getId()).getId()
									.equals(accountItemList.get(k).getId())) {
								gefunden = true;
								ObservableList<AccountItemList> accountItemListCopy = accountItemList;
								VBox vboxPuzzle = getbildBox(
										accountItemListCopy.get(k), level);
								vboxPuzzle.setAlignment(Pos.CENTER);
								vbox.getChildren().add(vboxPuzzle);
								vbox.setAlignment(Pos.CENTER);
							}
						}
						if (!MyGame.gewonnen) {
							vbox.getChildren().add(btnGamble);
							if (sellable(accountItemList.get(this.getId()).getItemList(), level, thema, this.getId()))
								vbox.getChildren().add(btnSell);

						}
						// if (anzahlAccounts > 1)
						// vbox.getChildren().add(AccountItemListTableView);
						// btnGamble.setOnAction(btnGambleEventListener);
						setAccStage(vbox, this.getId());

						musicFile = pfad_data_ton + "gewinn.mp3";
						sound = new Media(new File(musicFile).toURI()
								.toString());
						mediaPlayer = new MediaPlayer(sound);
						mediaPlayer.play();
					}

					if (!gewonnen) {
						musicFile = pfad_data_ton + "niete.mp3";
						sound = new Media(new File(musicFile).toURI()
								.toString());
						mediaPlayer = new MediaPlayer(sound);
						mediaPlayer.play();
						Image image = new Image("file:///" + pfad_data_itemjpg
								+ "niete.jpg");
						ImageView imgView = new ImageView(image);

						if (anzahlAccounts > 1) {
							Hilfsmethoden.setHinweisDialog("NIETE", imgView,
									this.getId());
						} else {
							Hilfsmethoden.setHinweisDialog("NIETE", imgView, 0);
						}

						AccountItemListTableView = new TableView<AccountItemList>();
						// System.out.println("WürfelAccount-2 " +
						// this.getId());
						AccountItemListTableView = AccountItemTableView
								.getAccountItemTableView(accountItemList,
										accounts.get(this.getId()).getId());

						VBox vbox = new VBox();
						vbox.setSpacing(10);
						vbox.autosize();
						// AccountItem-TableView
						VBox.setVgrow(AccountItemListTableView, Priority.ALWAYS);
						// vbox.getChildren().add(AccountItemListTableView);
						boolean gefunden = false;
						for (int k = 0; k < accountItemList.size() && !gefunden; k++) {

							if (accounts.get(this.getId()).getId()
									.equals(accountItemList.get(k).getId())) {
								gefunden = true;
								ObservableList<AccountItemList> accountItemListCopy = accountItemList;
								VBox vboxPuzzle = getbildBox(
										accountItemListCopy.get(k), level);
								vboxPuzzle.setAlignment(Pos.CENTER);
								vbox.getChildren().add(vboxPuzzle);
								vbox.setAlignment(Pos.CENTER);
							}
						}
						if (!MyGame.gewonnen) {
							vbox.getChildren().add(btnGamble);
							if (sellable(accountItemList.get(this.getId()).getItemList(), level, thema, this.getId()))
									vbox.getChildren().add(btnSell);
						}
						// if (anzahlAccounts > 1)
						// vbox.getChildren().add(AccountItemListTableView);
						// btnGamble.setOnAction(btnGambleEventListener);
						setAccStage(vbox, this.getId());
					}

				}
			});
			if (!gewonnen) {
				vbox.getChildren().add(btnGamble);
				if (sellable(accountItemList.get(i).getItemList(), level, thema, i))
						vbox.getChildren().add(btnSell);
					
				
			}
			// if (anzahlAccounts > 1)
			// vbox.getChildren().add(AccountItemListTableView);
			setAccStage(vbox, wuerfelAcc);
		}

	};
	
	private boolean sellable(ObservableList<Item> pitemlist, int level, int thema, int account){
		boolean sellable = false;
		boolean gefunden = false;
			for (int i=0; i<pitemlist.size() && !gefunden; i++){
				if (pitemlist.get(i).getCount().longValue()	> 2
					&& Item.getItemLevelThema(pitemlist.get(i).getId()).equals(String.valueOf(level)+String.valueOf(thema))){
					sellable = true;
					gefunden = true;
				}
			}
		return sellable;
	}

	//
	// Transaktionshistorie verarbeiten
	//
	EventHandler<ActionEvent> btnProcessAccountTransaktionListEventListener = (
			ActionEvent event) -> {

		for (int i = 0; i < trxHistorie.size(); i++) {
			// Gaming-Transaktion einem User zuordnen
			transaktionGutschreiben(trxHistorie.get(i));
		}
	};

	public static void transaktionGutschreiben(Transaktion trx) {
		for (int j = 0; j < accounts.size(); j++) {
			// Gaming-Transaktion einem User zuordnen
			if (trx.getEmpfangsAdresse()
					.toUpperCase()
					.contains(
							gameWallet.toUpperCase().substring(2,
									gameWallet.length()))) {
				if (trx.getSendeAdresse()
						.toUpperCase()
						.contains(
								accounts.get(j)
										.getWalletAddress()
										.toUpperCase()
										.substring(
												2,
												accounts.get(j)
														.getWalletAddress()
														.length()))) {

					// System.out.println("accUserID: " +
					// accounts.get(j).getId());
					// Transaktion zum User eintragen und gutschreiben
					for (int k = 0; k < trxAccountHistorie.size(); k++) {
						if (trxAccountHistorie.get(k).getId()
								.equals(accounts.get(j).getId())) {
							trxAccountHistorie.get(k).addTransaktion(trx);
						}
					}

				}
			}

		}
		accountsTableView.refresh();

		// GameBalance neu berechnen
		if (env == "home") {
			gameBalanceUIG = ETHReader.getBalance("ropsten", gameWallet,
					contract);
			trxHistorieTableView = TransaktionTableView
					.getTransaktionTableView(trxHistorie);
		}

		for (int i = 0; i < accounts.size(); i++) {
			gameBalanceUIG = gameBalanceUIG.subtract(accounts.get(i)
					.getBalance());
		}
		aktBalanceString.setValue("GameToken: " + gameBalanceUIG.intValue()
				+ " UIG");
		gameBalanceStart = gameBalanceUIG;
	}

	// BilderBox aufbauen
	public static VBox getbildBox(AccountItemList accountItemList, int level) {
		String pictureLevelThema = Integer.toString(level);
		pictureLevelThema += Integer.toString(MyGame.thema);
		// System.out.println("getBildBox: " + pictureLevelThema);
		// System.out.println("===============");

		// Account-ItemList nach gewünschtem Picture filtern
		// AccountItemList filterAccountItemlist = accountItemList;
		ObservableList<Item> accPictureItems = FXCollections
				.observableArrayList();
		accPictureItems.clear();
		String pitemLevelThema = new String();
		for (int t = 0; t < accountItemList.getItemList().size(); t++) {
			pitemLevelThema = Item.getItemLevelThema(accountItemList
					.getItemList().get(t).getId());
			// System.out.println("ItemLVTH: " + pitemLevelThema);
			if (pitemLevelThema.equals(pictureLevelThema)) {
				accPictureItems.add(accountItemList.getItemList().get(t));
			}
		}
		// accountItemList.setItemList(accPictureItems);

		HBox hBoxX = new HBox();
		HBox hBoxY = new HBox();
		VBox bildBox = new VBox();
		VBox rahmen = new VBox();
		int anzahlItems = 0;
		// System.out .println("Bilder-ItemList: " + accountItemList.getId());
		ObservableList<Item> itemList = accountItemList.getItemList();
		switch (level) {
		case 1:
			Image image11 = new Image("file:///" + pfad_data_itemjpg
					+ "uig.jpg");
			Image image12 = new Image("file:///" + pfad_data_itemjpg
					+ "uig.jpg");
			Image image21 = new Image("file:///" + pfad_data_itemjpg
					+ "uig.jpg");
			Image image22 = new Image("file:///" + pfad_data_itemjpg
					+ "uig.jpg");
			ImageView imgView11 = new ImageView(image11);
			ImageView imgView12 = new ImageView(image12);
			ImageView imgView21 = new ImageView(image21);
			ImageView imgView22 = new ImageView(image22);
			HBox bild_1_1 = new HBox();
			bild_1_1.getChildren().add(imgView11);
			HBox bild_1_2 = new HBox();
			bild_1_2.getChildren().add(imgView12);
			HBox bild_2_1 = new HBox();
			bild_2_1.getChildren().add(imgView21);
			HBox bild_2_2 = new HBox();
			bild_2_2.getChildren().add(imgView22);

			for (int i = 0; i < accountItemList.getItemList().size(); i++) {
				String itemID = accountItemList.getItemList().get(i).getId();
				// System.out .println("ItemIDAcc   : " + itemID);
				int itemLevel = 0;
				int itemPosition = 0;
				for (int j = 0; j < items.size(); j++) {
					if (itemID.equals(items.get(j).getId())) {
						// System.out .println("ItemIDItems : " +
						// items.get(j).getId());
						itemLevel = items.get(j).getLevel();
						itemPosition = items.get(j).getPosition();
					}
				}
				// System.out .println("ItemLevel   : " + itemLevel);
				// System.out .println("ItemPosition: " + itemPosition);
				if (itemLevel == 1) {
					switch (itemPosition) {
					case 1:
						Image image1 = new Image("file:///" + pfad_data_itemjpg
								+ "/L" + level + thema + "/"
								+ itemList.get(i).getName());
						ImageView imgView1 = new ImageView(image1);
						bild_1_1 = new HBox();
						bild_1_1.getChildren().add(imgView1);
						anzahlItems++;
						break;
					case 2:
						Image image2 = new Image("file:///" + pfad_data_itemjpg
								+ "/L" + level + thema + "/"
								+ itemList.get(i).getName());
						ImageView imgView2 = new ImageView(image2);
						bild_1_2 = new HBox();
						bild_1_2.getChildren().add(imgView2);
						anzahlItems++;
						break;
					case 3:
						Image image3 = new Image("file:///" + pfad_data_itemjpg
								+ "/L" + level + thema + "/"
								+ itemList.get(i).getName());
						ImageView imgView3 = new ImageView(image3);
						bild_2_1 = new HBox();
						bild_2_1.getChildren().add(imgView3);
						anzahlItems++;
						break;
					case 4:
						Image image4 = new Image("file:///" + pfad_data_itemjpg
								+ "/L" + level + thema + "/"
								+ itemList.get(i).getName());
						ImageView imgView4 = new ImageView(image4);
						bild_2_2 = new HBox();
						bild_2_2.getChildren().add(imgView4);
						anzahlItems++;
						break;
					}
				}
			}
			rahmen = new VBox();
			rahmen.setId("bilderrahmen");
			rahmen.setPrefSize((level + 1) * 100, (level + 1) * 100);
			rahmen.setMaxSize((level + 1) * 100, (level + 1) * 100);
			// rahmen.setStyle(cssLayout);
			hBoxX.getChildren().addAll(bild_1_1, bild_1_2);
			hBoxX.setAlignment(Pos.CENTER);
			hBoxY.getChildren().addAll(bild_2_1, bild_2_2);
			hBoxY.setAlignment(Pos.CENTER);
			rahmen.getChildren().addAll(hBoxX, hBoxY);
			bildBox.getChildren().addAll(rahmen);
			bildBox.setAlignment(Pos.CENTER);

			if (anzahlItems == ((level + 1) * (level + 1))) {
				gewonnen = true;
			}
			break;
		case 2:
		case 3:
		case 4:
			// System.out.println("Bildbox Level: " + level);
			// System.out.println("--------------------");
			ObservableList<HBox> bildListe = FXCollections
					.observableArrayList();
			bildListe.clear();
			for (int j = 0; j < (level + 1) * (level + 1); j++) {
				HBox einzelBildBox = new HBox();
				boolean gefunden = false;
				String itemId = new String();
				// System.out.println("Bildpos: " + j);
				// System.out.println("Bildbox Nr: " + j);
				for (int i = 0; i < accountItemList.getItemList().size()
						&& !gefunden; i++) {
					itemId = accountItemList.getItemList().get(i).getId();
					// System.out.println("getBildBox-ItemId: " + itemId);
					boolean gef = false;
					int itemLevel = 0;
					int itemThema = 0;
					int itemPos = 0;
					for (int f = 0; f < items.size() && !gef; f++) {
						// System.out.println("--- : " +
						// items.get(f).getId().toString());
						if (items.get(f).getId().equals(itemId)) {
							gef = true;
							// System.out.println("gefunden!");
							itemLevel = items.get(f).getLevel().intValue();
							itemThema = items.get(f).getWertigkeit().intValue();
							itemPos = items.get(f).getPosition().intValue();
						}
					}
					// System.out.println("ItemLv : " + itemLevel);
					// System.out.println("Itempos: " + itemPos);
					// System.out.println("Thema: " + thema);
					// System.out.println("PicturePos: " + j);
					if ((itemLevel == level) && (itemPos == j)
							&& (itemThema == thema) && gef) {
						/*
						 * System.out.println("file:///" + pfad_data_itemjpg +
						 * "/L" + level + thema + "/" +
						 * accountItemList.getItemList().get(i) .getName());
						 */
						Image image = new Image("file:///"
								+ pfad_data_itemjpg
								+ "/L"
								+ level
								+ MyGame.thema
								+ "/"
								+ accountItemList.getItemList().get(i)
										.getName());

						ImageView imgView = new ImageView(image);
						einzelBildBox.getChildren().add(imgView);
						gefunden = true;
						anzahlItems++;
						// System.out.println("AccountItem Nr: " + i +
						// " gefunden: " + gefunden);
					}
				}
				if (!gefunden) {
					// System.out.println("nicht gefunden -> UIG " + j);
					Image image = new Image("file:///" + pfad_data_itemjpg
							+ "uig.jpg");
					ImageView imgView = new ImageView(image);
					einzelBildBox.getChildren().add(imgView);
				}
				bildListe.add(einzelBildBox);
			}
			rahmen = new VBox();
			rahmen.setId("bilderrahmen");
			rahmen.setPrefSize((level + 1) * 100, (level + 1) * 100);
			rahmen.setMaxSize((level + 1) * 100, (level + 1) * 100);
			// rahmen.setStyle(cssLayout);
			HBox xBox = new HBox();
			xBox.setAlignment(Pos.CENTER);
			int xpos = 0;
			for (int i = 0; i < bildListe.size(); i++) {
				xBox.getChildren().add(bildListe.get(i));
				xpos++;
				if (xpos == level + 1) {
					rahmen.getChildren().add(xBox);
					// bildBox.setAlignment(Pos.CENTER);
					// bildBox.setStyle("-fx-background-color: #4d66cc;-fx-text-fill: WHITE;");
					xBox = new HBox();
					xBox.setAlignment(Pos.CENTER);
					xpos = 0;
				}
			}
			if (anzahlItems == ((level + 1) * (level + 1))) {
				gewonnen = true;
			}
			bildBox.getChildren().add(rahmen);
			bildBox.setAlignment(Pos.CENTER);
			break;
		default:
			break;
		}
		return bildBox;
	}

	private void setAccStage(VBox vbox, int AccNr) {
		vbox.setId("vbox" + AccNr);
		vbox.getStylesheets().add(pfad_data_css + "mygame.css");
		
		if (spielChoiceField.getValue().equals("Solospiel")) {
			border.setCenter(vbox);
		}else{
			Scene scene = new Scene(vbox,((displayX - 250) / anzahlAccounts),
				displayY - 50);

			scene.getStylesheets().add(pfad_data_css + "mygame.css");
			Stage primaryStage = new Stage();
			primaryStage.setTitle(accounts.get(AccNr).getName() + " "
				+ accounts.get(AccNr).getBalance() + " UIG");

			primaryStage.setScene(scene);
			primaryStage.setX(AccNr * ((displayX - 250) / anzahlAccounts));
			primaryStage.setY(25);

			// alteStage des Accounts schliessen, wenn bereits vorhanden
			if (accStages.size() == anzahlAccounts) {
				accStages.get(AccNr).closeStage();
				accStages.get(AccNr).setStage(primaryStage);
			} else {
				accStages.add(new MyStageController(primaryStage));
			}

			if (accStages.size() == 1) {
				accStages.get(0).getStage().show();
			} else {
				accStages.get(AccNr).getStage().show();
			}
		}

		if ((gewonnen) && !(spielChoiceField.getValue() == "Solospiel")) {
			// String musicFile = pfad_data_ton + "congratulations.mp3";
			String musicFile = pfad_data_ton + "congratulations.mp3";
			Media sound = new Media(new File(musicFile).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.play();
			Image image = new Image("file:///" + pfad_data_itemjpg
					+ "pokal.jpeg");
			ImageView imgView = new ImageView(image);
			BigDecimal gameGewinnUIG = gameBalanceUIG
					.subtract(gameBalanceStart);
			Hilfsmethoden.setGewinnDialog(
					"Game-Gewinn: " + gameGewinnUIG.intValue() + " UIG",
					imgView);
			
			
			//Rechteck-Form und Scaling
			Rectangle rectangle = new Rectangle(300, 100, Color.LIGHTGRAY);
			Image img = new Image("file:///" + pfad_data_itemjpg + "pokal.jpeg");
			rectangle.setFill(new ImagePattern(img));
			vbox.getChildren().add(rectangle);
	        
	        // SCALING (Größe ändern)
	        Scale scale = new Scale();
	        // Setting the scaling factor.
	        scale.setX(2);
	        scale.setY(2);
	        // Setting Orgin of new coordinate system
	        scale.setPivotX(100);
	        scale.setPivotY(100);
	         // Adding the transformation to rectangle2
	        rectangle.getTransforms().addAll(scale);
			
	      
		}
	}

	// Account-Items
	private void loadAccountItems(String accid, String datenquelle) {
		accountItemList.clear();
		// System.out.println("1Account Items:" + accid + datenquelle);

		switch (datenquelle) {
		case "Excel":
			File file = new File(pfad_data + "/" + accid + "_Items.xlsx");
			if (file != null) {
				try {
					ExcelReadWriter.readXLSXFileToAccountItemTable(file, accid);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Hilfsmethoden.setHinweisDialog(
							"Datei Account-Items konnten nicht gelesen werden",
							null, 0);
				}
			}
			// showAccountItemsTableView(accid);
			break;
		case "MySQL":
			// System.out.println("2Account Items:" + accid + datenquelle);
			MyGameSQLConnection.fillAccountItems(accid);
			break;
		default:
			break;
		}

	}

	//
	// LoadButton -> Account-Datei einlesen und im TableView darstellen
	//
	// EventHandler<ActionEvent> btnLoadAccountsEventListener = (ActionEvent
	// event) -> {
	private void loadAccount(String name, String pw, String datenquelle) {
		/*
		 * FileChooser fileChooser = new FileChooser();
		 * fileChooser.getExtensionFilters() .addAll(new
		 * FileChooser.ExtensionFilter("XLSX files (*.XLSX)", "*.XLSX"), new
		 * FileChooser.ExtensionFilter("XLSX files (*.xlsx)", "*.xlsx"));
		 * 
		 * fileChooser.setInitialDirectory(pfad_data); File file =
		 * fileChooser.showOpenDialog(null);
		 */
		accounts.clear();

		// Account in accounts(0) speichern
		switch (datenquelle) {
		case "Excel":
			File file = new File(pfad_data + "/Game001_Accounts.xlsx");
			if (file != null) {
				try {
					ExcelReadWriter.readXLSXFileToAccountTable(file, name, pw);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "MySQL":
			// System.out.println("Load Account:" + name + pw);
			boolean dbvorhanden = MyGameSQLConnection.fillUserAccount(name, pw);
			if (!dbvorhanden)
				System.out.println("Fehler bei Load Account:");
			break;
		default:
			break;
		}

		aktBalanceString.setValue("GameToken: " + gameBalanceUIG.intValue()
				+ " UIG");
		gameBalanceStart = gameBalanceUIG;

		// showAccountTableView();
	};

	private void initialisiereTunierAccounts(int anzahlAccounts) {
		BigDecimal spielerBalance = new BigDecimal(anzahlAccounts * 1000);
		gameBalanceUIG = spielerBalance.add(new BigDecimal("1000"));
		aktBalanceString.setValue("GameToken: 1000 UIG");

		MyGame.accountItemList.clear();
		MyGame.trxAccountHistorie.clear();
		for (int i = 0; i < accStages.size(); i++) {
			accStages.get(i).closeStage();
		}
		accStages.clear();
		gewonnen = false;
		for (int i = 0; i < anzahlAccounts; i++) {
			String name = Hilfsmethoden.getTextDialog("Spieler " + (i + 1));

			// Account initialisieren
			MyGame.accounts.add(new Account("ACC" + i, name, "xxx", "xxx",
					"1000"));

			// Account-Transaktionen initialisieren
			MyGame.trxAccountHistorie.add(new AccountTransaktionList("Acc" + 1));

			// Account-ItemList initialisieren
			AccountItemList accountItems = new AccountItemList("");
			accountItems = new AccountItemList("ACC" + i);
			accountItems.addItem("ITEM00000000", "1", false);
			MyGame.accountItemList.add(accountItems);
		}
	}

	private void showAccountItemsTableView(String accid) {
		AccountItemListTableView = new TableView<AccountItemList>();

		AccountItemListTableView = AccountItemTableView
				.getAccountItemTableView(accountItemList, accid);

		// Account-Items-TableView anzeigen
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.autosize();
		VBox.setVgrow(AccountItemListTableView, Priority.ALWAYS);
		vbox.getChildren().addAll(AccountItemListTableView);
		Scene scene = new Scene(vbox, 900, 900);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Account-Items");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void showAccountTableView() {
		accountsTableView = new TableView<Account>();
		accountsTableView = AccountTableView.getAccountTableView(accounts);

		// Account-TableView anzeigen
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.autosize();
		VBox.setVgrow(accountsTableView, Priority.ALWAYS);
		vbox.getChildren().addAll(accountsTableView);
		Scene scene = new Scene(vbox, 900, 900);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Accounts");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void showItemsTableView() {
		itemTableView = new TableView<Item>();
		itemTableView = ItemTableView.getItemTableView(items);

		// Account-TableView anzeigen
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.autosize();
		VBox.setVgrow(itemTableView, Priority.ALWAYS);
		vbox.getChildren().addAll(itemTableView);
		Scene scene = new Scene(vbox, 900, 900);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Items");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
}
