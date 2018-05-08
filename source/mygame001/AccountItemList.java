package eth;

import java.math.BigDecimal;
import java.math.BigInteger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountItemList {
	
	private final SimpleStringProperty id;
	private ObservableList<Item> itemList = FXCollections.observableArrayList();

	public AccountItemList(String accountId) {
		this.id = new SimpleStringProperty(accountId);
		this.itemList.clear();
	}

	public String getId() {
		return this.id.get();
	}

	public ObservableList<Item> getItemList() {
		return this.itemList;
	}
	
	public void setItemList(ObservableList<Item> itemList) {
		this.itemList = itemList;
	}

	public void addItem(String itemId, String count, boolean dbsave){
		boolean vorhanden = false;
		
		for (int i=0; i < this.itemList.size(); i++) {
			if (this.itemList.get(i).getId().equals(itemId)){
				this.itemList.get(i).addCount(new BigDecimal(count));
				if (dbsave)
					MyGameSQLConnection.addItemCount(this.id.get(),itemId);
				vorhanden = true;
			}
		}
		
		if (!vorhanden) {
			String name = "Itemname";
			for (int i=0; i < MyGame.items.size(); i++){
				if (itemId.equals(MyGame.items.get(i).getId())){
					name = MyGame.items.get(i).getName();
					Item item = new Item(itemId, name, "0", "0", "0", "0", "0");
					item.setCount(new BigDecimal(count));
					this.itemList.add(item);
					if (dbsave)
						MyGameSQLConnection.addItemToUser(this.id.get(),item.getId());
					//System.out.println("ItemNichtVorhanden: " + MyGame.items.get(i).getName());
				}
			}
		}
	}
	
	
}
