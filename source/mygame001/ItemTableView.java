package eth;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ItemTableView {

	public static TableView getItemTableView(ObservableList<Item> itemList) {
			
			final Label label = new Label("Accounts");
	        label.setFont(new Font("Arial", 15));
	        
	        TableView table = new TableView<Item>();
	        table.setItems(itemList);
	        table.setEditable(true);
	        table.autosize();
	        
			TableColumn itemIdCol = new TableColumn("Item-ID");
			itemIdCol.setMinWidth(60);
			itemIdCol.setCellValueFactory(new PropertyValueFactory("Id"));
	        
			TableColumn nameCol = new TableColumn("Name");
			nameCol.setMinWidth(60);
			nameCol.setCellValueFactory(new PropertyValueFactory("Name"));
			
			TableColumn costFiatCol = new TableColumn("Preis in $");
			costFiatCol.setMinWidth(60);
			costFiatCol.setCellValueFactory(new PropertyValueFactory("CostFiat"));

			TableColumn costTokenCol = new TableColumn("Preis in UIG Token");
			costTokenCol.setMinWidth(60);
			costTokenCol.setCellValueFactory(new PropertyValueFactory("CostUIG"));
			
			TableColumn levelCol = new TableColumn("Level");
			levelCol.setMinWidth(60);
			levelCol.setCellValueFactory(new PropertyValueFactory("Level"));
			
			TableColumn posCol = new TableColumn("Position");
			posCol.setMinWidth(60);
			posCol.setCellValueFactory(new PropertyValueFactory("Position"));
			
			TableColumn wertigkeitCol = new TableColumn("Wertigkeit");
			wertigkeitCol.setMinWidth(60);
			wertigkeitCol.setCellValueFactory(new PropertyValueFactory("Wertigkeit"));
			
			table.getColumns().addAll(itemIdCol, nameCol, costFiatCol, costTokenCol, levelCol, posCol, wertigkeitCol);

			return table;
			
		}
	

}
