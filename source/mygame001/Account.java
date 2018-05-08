package eth;

import java.math.BigDecimal;
import java.math.BigInteger;

import javafx.beans.property.SimpleStringProperty;

public class Account {
	
	private final SimpleStringProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty pw;
	private final SimpleStringProperty walletAddress;
	private       BigDecimal balance;

	public Account(String id, String name, String pw, String walletAddress, String balance) {
		this.id = new SimpleStringProperty(id);
		this.name = new SimpleStringProperty(name);
		this.pw = new SimpleStringProperty(pw);
		this.walletAddress = new SimpleStringProperty(walletAddress);
		this.balance = new BigDecimal(balance);
		MyGame.gameBalanceUIG = MyGame.gameBalanceUIG.subtract(new BigDecimal(balance));
	}

	public String getId() {
		return this.id.get();
	}

	public String getName() {
		return this.name.get();
	}
	
	public String getPw() {
		return this.pw.get();
	}
	
	public String getWalletAddress() {
		return this.walletAddress.get();
	}
	
	public BigDecimal getBalance() {
		return this.balance;
	}
	
	public void addBalance(BigDecimal wert) {
		this.balance = this.balance.add(wert);
	}

	public boolean subBalance(BigDecimal wert) {
		boolean erfolgreich = false;
		if (this.balance.subtract(wert).longValue() > -1){
			this.balance = this.balance.subtract(wert);
			erfolgreich = true;
		}
		return erfolgreich;
	}


}
