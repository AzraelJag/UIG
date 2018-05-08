package eth;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AccountTableView {

	public static TableView getAccountTableView(ObservableList<Account> accountList) {
			
			final Label label = new Label("Accounts");
	        label.setFont(new Font("Arial", 15));
	        
	        TableView table = new TableView<Account>();
	        table.setItems(accountList);
	        table.setEditable(true);
	        table.autosize();
	        
			TableColumn accIdCol = new TableColumn("Account-ID");
			accIdCol.setMinWidth(60);
			accIdCol.setCellValueFactory(new PropertyValueFactory("Id"));
	        
			TableColumn nameCol = new TableColumn("Name");
			nameCol.setMinWidth(60);
			nameCol.setCellValueFactory(new PropertyValueFactory("Name"));
			
			TableColumn walletCol = new TableColumn("Wallet-Adresse");
			walletCol.setMinWidth(60);
			walletCol.setCellValueFactory(new PropertyValueFactory("WalletAddress"));

			TableColumn tokenCol = new TableColumn("UID Token");
			tokenCol.setMinWidth(60);
			tokenCol.setCellValueFactory(new PropertyValueFactory("Balance"));
			
			table.getColumns().addAll(accIdCol, nameCol, walletCol, tokenCol);

			return table;
			
		}


}
