package eth;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;

public class ETHReader {
	
	private static BigInteger letzterGelesenerBlock = new BigInteger("0");
	private static BigInteger aktuellerETHBlock     = new BigInteger("0");
	private File pathToTempFile = new File("e://java/data/mygame");
	private File pathToHistFile = new File("e://java/data/mygame");
	private static String testnet_ropsten = "https://ropsten.infura.io/2JLVJ6Dk6Pz23Y9Xi7x3";
	private static String contract_adress_UIG = "0x5ab39784b7ab27692dc2748dadfaefe314a712e5";
	
	public ETHReader() {
		this.letzterGelesenerBlock = new BigInteger("0");
		this.aktuellerETHBlock = new BigInteger("0");

	}
	
	public static void startETHReader (String env){
		//connect to env network
		Web3j web3 = null;
		if (env.equals("ropsten")){
			web3 = Web3j.build(new HttpService(testnet_ropsten));
		}
		
		//aktuelle Blocknummer ermitteln
		aktuellerETHBlock = ETHBlockchain.getBlocknumber(web3);
		
        EthFilter ethFilter = null;
		try {
			ethFilter = createFilterForEvent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        EthLog ethLog;
		try {
			ethLog = web3.ethGetLogs(ethFilter).send();
			System.out.println("AnzGefTrx: " + ethLog.getLogs().size());
			
			for (int i=0 ; i < ethLog.getLogs().size(); i++){
			    System.out.println(ethLog.getLogs().get(i).toString());
				String[] trxData = ethLog.getLogs().get(i).toString().split(",");
				String blockNumberHex = trxData[5];
				String data[] = blockNumberHex.split("'");
				BigInteger blockNumberBigInt = new BigInteger(data[1].substring(2,data[1].length()), 16);
				int blockNumberInt = blockNumberBigInt.intValue();
				Transaktion trx = new Transaktion(ethLog.getLogs().get(i).toString());
				boolean erfolgreich = MyGameSQLConnection.putTrxToHistorie(blockNumberInt, trx.getTrxString());
				if (erfolgreich){
					//UserID aus Sendeadresse ermitteln
					String userid = MyGameSQLConnection.ermittleUserid(trx.getSendeAdresse());
					System.out.println("UserID" + userid);
					//Transaktion in User_Transaktion eintragen und Wert dem User gutschreiben
					if (!userid.isEmpty())
						MyGameSQLConnection.fillAccountTransaktionen(userid, env);
				}
				//MyGame.trxHistorie.add(trx);
			   //MyGame.trxHistorieTableView.refresh();
			   //MyGame.transaktionGutschreiben(MyGame.trxHistorie.get(MyGame.trxHistorie.size()-1));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean availableTestETHRobsten(){
		//connect to env network
		boolean available = false;
			Web3j web3 = null;
		try{
			web3 = Web3j.build(new HttpService(testnet_ropsten));
			available = true;
			try{
				BigDecimal balance = getBalance("ropsten", MyGame.gameWallet, MyGame.contract);
				if (balance == BigDecimal.ZERO)
					available = false;
			}catch (Exception e){
				available = false;
				e.printStackTrace();
			}
		}catch (Exception e){
			available = false;
			e.printStackTrace();
		}
		return available;
	}
	
	public static BigDecimal getBalance(String env, String wallet, String contract){
		//connect to env network
		Web3j web3 = null;
		if (env.equals("ropsten")){
				web3 = Web3j.build(new HttpService(testnet_ropsten));
		}
		BigDecimal balance = new BigDecimal("0.0");
		org.web3j.abi.datatypes.Function function = balanceOf(wallet);
		String encodedFunction = FunctionEncoder.encode(function);

        try {
			org.web3j.protocol.core.methods.response.EthCall response = web3.ethCall(
			        Transaction.createEthCallTransaction(wallet, contract, encodedFunction),
			        DefaultBlockParameterName.LATEST)
			        .sendAsync().get();
			
//			System.out.println("Anz UIG (uint256): " + response.getValue().substring(50,response.getValue().length()));
			BigInteger bigInt = new BigInteger(response.getValue().substring(50,response.getValue().length()), 16);		
			BigDecimal bigDecimal = new BigDecimal(bigInt.toString());
			BigDecimal dezimalstellen = new BigDecimal("100000000");
			bigDecimal = bigDecimal.divide(dezimalstellen);
			balance = bigDecimal;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("ETH Blockchain ist nicht verfügbar ...");
			balance = BigDecimal.ZERO;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("ETH Blockchain ist nicht verfügbar ...");
			balance = BigDecimal.ZERO;
		}
		return balance;
	}
	
	
	// Hintergrundprozess Blockchain auf neue Transaktionen prüfen und eintragen
	public static void starteTransaktionsTask (String env) {
		Web3j web3 = null;
		if (env.equals("ropsten")){
			web3 = Web3j.build(new HttpService(testnet_ropsten));
		}
		letzterGelesenerBlock = MyGameSQLConnection.letzterGelesenerBlock(env);
		System.out.println("Startblock: " + letzterGelesenerBlock);
		if (web3 != null && letzterGelesenerBlock != BigInteger.ZERO){
			Task<Integer> task = new Task<Integer>() {
	         @Override protected Integer call() throws Exception {
	             int iterations;
	             boolean ende = false;
	             for (iterations = 0; iterations < 10 && !ende; iterations++) {
	                 if (isCancelled()) {
	                     break;
	                 }
	                //long aktTimeS = System.nanoTime() / 1000 / 1000 / 1000;
	 		        //String aktZeit = new DecimalFormat("#,##0.00") + " s";
	                //System.out.println("Startblock: " + letzterGelesenerBlock);
	                startETHReader(env);
	                //System.out.println("EndeBlock : " + aktuellerETHBlock);
	                //System.out.println("---------------------------------");
	                letzterGelesenerBlock = aktuellerETHBlock;
	                Thread.sleep(30000);
	             }
	             return iterations;
	         }
			};
			Thread ETHThread = new Thread(task);
			ETHThread.setDaemon(true);
			ETHThread.start();
		}
	}
	
    private static EthFilter createFilterForEvent() throws Exception {
            EthFilter ethFilter = new EthFilter(
                	new DefaultBlockParameterNumber(letzterGelesenerBlock),
                	new DefaultBlockParameterNumber(aktuellerETHBlock),
                    contract_adress_UIG 
            );

            //ethFilter.addSingleTopic(encodedEventSignature);
            return ethFilter;

    }
    
    private static org.web3j.abi.datatypes.Function balanceOf(String owner) {
        return new org.web3j.abi.datatypes.Function(
                "balanceOf",
                Collections.singletonList(new Address(owner)),
                Collections.singletonList(new TypeReference<Uint256>() {}));
    }

}
