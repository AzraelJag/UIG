package eth;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TransaktionTableView {
	
	public static TableView getTransaktionTableView(ObservableList<Transaktion> trxData) {
		
		final Label label = new Label("Transaktionshistorie");
        label.setFont(new Font("Arial", 15));
        
        TableView table = new TableView<Transaktion>();
        table.setItems(trxData);
        table.setEditable(true);
        table.autosize();
        
		TableColumn blocknrCol = new TableColumn("Blocknummer");
		blocknrCol.setMinWidth(60);
		blocknrCol.setCellValueFactory(new PropertyValueFactory("BlockNumber"));
        
		TableColumn tsCol = new TableColumn("Datum/Uhrzeit");
		tsCol.setMinWidth(60);
		tsCol.setCellValueFactory(new PropertyValueFactory("DateTime"));
		
		TableColumn trxHashCol = new TableColumn("TransaktionsHash");
		trxHashCol.setMinWidth(60);
		trxHashCol.setCellValueFactory(new PropertyValueFactory("TransactionHash"));

		TableColumn vonCol = new TableColumn("Sende-Wallet");
		vonCol.setMinWidth(60);
		vonCol.setCellValueFactory(new PropertyValueFactory("SendeWalletName"));
		
		TableColumn nachCol = new TableColumn("Empfangs-Wallet");
		nachCol.setMinWidth(60);
		nachCol.setCellValueFactory(new PropertyValueFactory("EmpfangsWalletName"));
		
		TableColumn dataCol = new TableColumn("Anzahl Token");
		dataCol.setMinWidth(60);
		dataCol.setCellValueFactory(new PropertyValueFactory("DataAsBigDecimal8"));
		
		TableColumn tokennameCol = new TableColumn("Token Name");
		tokennameCol.setMinWidth(60);
		tokennameCol.setCellValueFactory(new PropertyValueFactory("TokenName"));

		table.getColumns().addAll(tsCol, blocknrCol, dataCol, tokennameCol, trxHashCol);

		return table;
		
	}

}
