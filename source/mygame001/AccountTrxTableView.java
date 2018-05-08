package eth;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;

	
	public class AccountTrxTableView {
		
		public static TableView getAccountTrxTableView(ObservableList<AccountItemList> accountItemList, String accountId) {
			
			final Label label = new Label(accountId + " Transaktionsliste:");
	        label.setFont(new Font("Arial", 15));
	        
	        TableView table = new TableView<Item>();
	        
	        ObservableList<AccountItemList> accountIdItemList=
	    	        FXCollections.observableArrayList();
	        for (int i=0; i < accountItemList.size(); i++)
	        {
	        	if (accountId.equals(accountItemList.get(i).getId()))
	        	{		
	        		accountIdItemList.add(accountItemList.get(i));
	        	}
			}
	        
	        ObservableList<Item> accountIdItems=
	    	        FXCollections.observableArrayList();
	        
	        for (int i=0; i < accountIdItemList.get(0).getItemList().size(); i++)
	        {
	        	      	 accountIdItems.add(accountIdItemList.get(0).getItemList().get(i));
			}
	        
	        
	        table.setItems(accountIdItems);
	        table.setEditable(true);
	        table.autosize();
	        
			TableColumn itemIdCol = new TableColumn("Item-ID");
			itemIdCol.setMinWidth(60);
			itemIdCol.setCellValueFactory(new PropertyValueFactory("Id"));
	        
			TableColumn nameCol = new TableColumn("Name");
			nameCol.setMinWidth(60);
			nameCol.setCellValueFactory(new PropertyValueFactory("Name"));
			
			TableColumn countCol = new TableColumn("Anzahl");
			countCol.setMinWidth(60);
			countCol.setCellValueFactory(new PropertyValueFactory("Count"));

			table.getColumns().addAll(itemIdCol, nameCol, countCol);

			return table;
			
		}


}
