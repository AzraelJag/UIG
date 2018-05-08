package eth;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import allgemein.Hilfsmethoden;

public class ReadTrxFromFile extends Application {
	
	public static File pfad_data = new File("c://d/java/data/eth");
//	public static String pfad_data = "file:///c://d/java/data";
	public static List<String> readFileInhalt = new ArrayList<String>();
	public static BigInteger letzterBlock = new BigInteger("0");
	
	private static TableView<Transaktion> table = new TableView<Transaktion>();
	
    public static ObservableList<Transaktion> trxData =
        FXCollections.observableArrayList();
      	
	public static void main(String[] args) {
        launch(args);
    }	
	
	@Override
    public void start(Stage primaryStage) {
        Button btnLoad 		= new Button("Load");
        btnLoad.setOnAction		(btnLoadEventListener);
        
		// Suchstring Eingabefeld
        Label sucheLabel = new Label("Suchfeld:");
        VBox rootBox = new VBox();
        rootBox.getChildren().add(btnLoad);

        Scene scene = new Scene(rootBox, 300, 300);
        primaryStage.setTitle("MyTransaktionReader");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
	
    //  
    // LoadButton -> Datei einlesen   
    //
    EventHandler<ActionEvent> btnLoadEventListener
    = (ActionEvent event) -> {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
            .addAll(
                new FileChooser.ExtensionFilter("TXT files (*.TXT)", "*.TXT"),
                new FileChooser.ExtensionFilter("txt files (*.txt)", "*.txt"));
        
        fileChooser.setInitialDirectory(pfad_data); 
        File file = fileChooser.showOpenDialog(null);
        
        trxData.clear();
        if (file != null) {
        	readFileInhalt = Hilfsmethoden.readLineByLineJava8(file.getPath().toString());
        	//Hilfsmethoden.setHinweisDialog("Datei wurde eingelesen");	
        	
        	//letzerGelesenerBlock
        	letzterBlock = new BigInteger(readFileInhalt.get(0));
        	System.out.println("letzter Block : " + letzterBlock);
        	
        	//Transaktionshistorie aus Contract-Sicht
        	for (int i=1; i<readFileInhalt.size(); i++){
        		Transaktion trx = new Transaktion(readFileInhalt.get(i));
        		trxData.add(trx);
        		System.out.println("-------------------");
        		System.out.println("trxString         : " + trx.getTrxString());
        		System.out.println("removed           : " + trx.getRemoved());
        		System.out.println("logIndex          : " + trx.getlogIndex());
        		System.out.println("transaction Index : " + trx.gettransactionIndex());
        		System.out.println("transaction Hash  : " + trx.getTransactionHash());
        		System.out.println("block Hash        : " + trx.getblockHash());
        		System.out.println("block Number      : " + trx.getBlockNumber());
        		System.out.println("contract address  : " + trx.getaddress());
        		System.out.println("data              : " + trx.getDataAsBigInteger());
        		String tokenName = "xxx";
        		if (trx.getaddress().equals(MyWallet.contract_adress_UIG))
        			tokenName = "UIG";
        		System.out.println("token-wert        : " + trx.getDataAsBigDecimal8() + " " + tokenName );
        		System.out.println("from address      : " + trx.getSendeAdresse());
        	    System.out.println("                  : " + trx.getSendeWalletName());
        		System.out.println("to address        : " + trx.getEmpfangsAdresse());
        		System.out.println("                  : " + trx.getEmpfangsWalletName());
        	}
        }
        
        table = new TableView<Transaktion>();
        table = TransaktionTableView.getTransaktionTableView(trxData);
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.autosize();
		VBox.setVgrow(table, Priority.ALWAYS);
		vbox.getChildren().addAll(table);
        Scene scene = new Scene(vbox, 900, 900);
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Transaktionshistorie");
		primaryStage.setScene(scene);
		primaryStage.show();
        
      };
       

}
