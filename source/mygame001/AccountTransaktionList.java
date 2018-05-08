package eth;

import java.math.BigDecimal;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountTransaktionList {
	
	private final SimpleStringProperty id;
	private final ObservableList<Transaktion> transaktionList = FXCollections.observableArrayList();

	public AccountTransaktionList(String accountId) {
		this.id = new SimpleStringProperty(accountId);
		this.transaktionList.clear();
	}
	
	public String getId() {
		return this.id.get();
	}

	public ObservableList<Transaktion> getItemList() {
		return this.transaktionList;
	}

	public void addTransaktion(Transaktion transaktion){
		boolean vorhanden = false;
		for (int i=0; i < this.transaktionList.size(); i++) {
			if (transaktion.getTransactionHash().equals(this.transaktionList.get(i).getTransactionHash())){
				//System.out.println("Transaktion bereits gutgeschrieben: " + transaktion.getTransactionHash());
				vorhanden = true;
			}
		}
		if (!vorhanden) {
			//Transaktion dem User eintragen
			this.transaktionList.add(transaktion);
			//Balance dem User gutschreiben
			boolean gefunden=false;
			for (int j=0; j < MyGame.accounts.size() && !gefunden; j++){
				if (MyGame.accounts.get(j).getId().equals(this.id.get())){
					MyGame.accounts.get(j).addBalance(transaktion.getDataAsBigDecimal8());
					gefunden = true;
				}
			}
			
		}
	}
	
	

}
