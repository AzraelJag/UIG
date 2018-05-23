package eth;

import java.math.BigDecimal;
import java.math.BigInteger;

import allgemein.DateTime;
import javafx.beans.property.SimpleStringProperty;

public class Transaktion {

	private final SimpleStringProperty trxString;
	private final SimpleStringProperty removed;
	private final SimpleStringProperty logIndex;
	private final SimpleStringProperty transactionIndex;
	private final SimpleStringProperty transactionHash;
	private final SimpleStringProperty blockHash;
	private final SimpleStringProperty blockNumber;
	private final SimpleStringProperty address;
	private final SimpleStringProperty data;
	private final SimpleStringProperty type;
	private final SimpleStringProperty topics1;
	private final SimpleStringProperty topics2;
	private final SimpleStringProperty topics3;
	private final SimpleStringProperty timestamp;

	public Transaktion(String trxString) {
		this.trxString = new SimpleStringProperty(trxString);
		String[] trxData = trxString.split(",");
		this.removed = new SimpleStringProperty(trxData[0]);
		this.logIndex = new SimpleStringProperty(trxData[1]);
		this.transactionIndex = new SimpleStringProperty(trxData[2]);
		this.transactionHash = new SimpleStringProperty(trxData[3]);
		this.blockHash = new SimpleStringProperty(trxData[4]);
		this.blockNumber = new SimpleStringProperty(trxData[5]);
		this.address = new SimpleStringProperty(trxData[6]);
		this.data = new SimpleStringProperty(trxData[7]);
		this.type = new SimpleStringProperty(trxData[8]);
		this.topics1 = new SimpleStringProperty(trxData[9]);
		this.topics2 = new SimpleStringProperty(trxData[10]);
		this.topics3 = new SimpleStringProperty(trxData[11]);
		this.timestamp = new SimpleStringProperty("00.00.0000 00:00");
	}

	public String getTimestamp() {
		if (MyGame.sprache == "de"){
			return this.timestamp.get();
		}else{
			return this.timestamp.get();
		}
	}
	
	public String getDateTime() {
		return DateTime.getDateTime(this.timestamp.get(), MyGame.sprache);
	}	
	
	public void setTimestamp(String timestamp) {
		this.timestamp.set(timestamp);
	}
	
	public String getTrxString() {
		return this.trxString.get();
	}

	public void setTrxString(String trxString) {
		this.trxString.set(trxString);
	}
	
	public boolean getRemoved() {
		if (this.removed.toString().contains("false")){
			return false;
		}else
			return true;
	}
	
	
	public BigInteger getlogIndex() {
		String data[] = this.logIndex.toString().split("'");
		return new BigInteger(data[1].substring(2,data[1].length()), 16);
	}

	public BigInteger gettransactionIndex() {
		String data[] = this.transactionIndex.toString().split("'");
		return new BigInteger(data[1].substring(2,data[1].length()), 16);
	}
	
	public String getTransactionHash() {
		String data[] = this.transactionHash.toString().split("'");
		return data[1];
	}
	
	public String getblockHash() {
		String data[] = this.blockHash.toString().split("'");
		return data[1];
	}
	
	public BigInteger getBlockNumber() {
		String data[] = this.blockNumber.toString().split("'");
		return new BigInteger(data[1].substring(2,data[1].length()), 16);
	}
	
	public String getaddress() {
		String data[] = this.address.toString().split("'");
		return data[1];
	}
	
	public String getTokenName() {
	String data[] = this.address.toString().split("'");
	String tokenName = "xxx";
	if (data[1].equals(MyWallet.contract_adress_UIG))
		tokenName = "UIG";
	else {
		tokenName = data[1];
	}
	return tokenName;
	}
	
	public BigInteger getDataAsBigInteger() {
		String data[] = this.data.toString().split("'");
		return new BigInteger(data[1].substring(2,data[1].length()), 16);
	}
	
	public BigDecimal getDataAsBigDecimal8() {
		String data[] = this.data.toString().split("'");
		BigInteger anzTokenInteger = new BigInteger(data[1].substring(2,data[1].length()), 16);
		BigDecimal anzTokenDecimal = new BigDecimal(anzTokenInteger.toString());
		return anzTokenDecimal.divide(new BigDecimal("100000000"));
	}
		
	public String getSendeAdresse() {
		String von = this.topics2.toString();
		return von.substring( von.length()-67, von.length()-1);
	}
	
	public String getSendeWalletName() {
		String von = this.topics2.toString();
		String vonAdresse = von.substring( von.length()-67, von.length()-1);
		String walletName = "Unbekannt";
		if (vonAdresse.substring(vonAdresse.length()-40,vonAdresse.length() )
				.equals(MyWallet.wallet_adress_Azrael_UIG.substring(MyWallet.wallet_adress_Azrael_UIG.length()-40, MyWallet.wallet_adress_Azrael_UIG.length())))
			walletName = "Azrael_UIG";
		else {
		if (vonAdresse.substring(vonAdresse.length()-40, vonAdresse.length() )
				.equals(MyWallet.wallet_adress_Azrael_TID.substring(MyWallet.wallet_adress_Azrael_TID.length()-40, MyWallet.wallet_adress_Azrael_TID.length())))
		    walletName = "Azrael_TID";
	
		else walletName = vonAdresse;
				}
		return walletName;
	}
	
	public String getEmpfangsAdresse() {
		String nach = this.topics3.toString();
		return nach.toString().substring( nach.length()-69, nach.length()-3);
	}
	
	public String getEmpfangsWalletName() {
		String nach = this.topics3.toString();
		String nachAdresse = nach.substring( nach.length()-69, nach.length()-3);
		String walletName = nachAdresse;
		if (nachAdresse.substring(nachAdresse.length()-40,nachAdresse.length() )
				.equals(MyWallet.wallet_adress_Azrael_UIG.substring(MyWallet.wallet_adress_Azrael_UIG.length()-40, MyWallet.wallet_adress_Azrael_UIG.length())))
			walletName = "Azrael_UIG";
	
		if (nachAdresse.substring(nachAdresse.length()-40, nachAdresse.length() )
				.equals(MyWallet.wallet_adress_Azrael_TID.substring(MyWallet.wallet_adress_Azrael_TID.length()-40, MyWallet.wallet_adress_Azrael_TID.length())))
		    walletName = "Azrael_TID";	

		if (nachAdresse.substring(nachAdresse.length()-40, nachAdresse.length()).toUpperCase() 
					.equals(MyGame.gameWallet.substring(MyGame.gameWallet.length()-40, MyGame.gameWallet.length()).toUpperCase()))
		    walletName = "MYGAME";	

		return walletName;
	}
}
