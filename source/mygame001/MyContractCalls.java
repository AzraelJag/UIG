package eth;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Wallet;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.xml.bind.DatatypeConverter;

public class MyContractCalls {
	
	//Konstanten
	public static String testnet_ropsten = "https://ropsten.infura.io/2JLVJ6Dk6Pz23Y9Xi7x3";
	public static String mainnet_ropsten = "https://mainnet.infura.io/2JLVJ6Dk6Pz23Y9Xi7x3";
	public static String contract_adress_UIG = "0x5ab39784b7ab27692dc2748dadfaefe314a712e5";
	public static String wallet_adress_Azrael_UIG = "0x035fd8795675134ceb2de5fb2e18755a49b76cdc";
	public static String wallet_adress_Azrael_TID = "0xc0D283A25c9B318ac9B033fa99a4Da511590b0e0";
	
	
	public static void main(String[] args) {
		
		System.out.println("start ... \n");

		//connect to robsten network
		Web3j web3 = Web3j.build(new HttpService(testnet_ropsten));
//		Web3j web3 = Web3j.build(new HttpService(mainnet_ropsten));
		
		//Funktionen
		
	
		// get GasPrice()
		try {
			System.out.println("get GasPrice ...");
			BigInteger gasprice = web3.ethGasPrice().send().getGasPrice();
			System.out.println("ethGasPrice: " + gasprice);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get Balance ETH()
		EthGetBalance ethGetBalance;
		try {
			System.out.println("get Balance ...");
			ethGetBalance = web3
			  .ethGetBalance(wallet_adress_Azrael_UIG, DefaultBlockParameterName.LATEST)
			  .sendAsync()
			  .get();
		
			BigInteger wei = ethGetBalance.getBalance();
 //			var value = web3.fromWei("21000000000000", "ether");
			Convert.fromWei(wei.toString(), Unit.ETHER); 
			System.out.println("Balance: " + Convert.fromWei(wei.toString(), Unit.ETHER) + " ETH");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// get AnzTrx ETH()
		System.out.println("get AnzTrx ...");
		EthGetTransactionCount anzTrx;
		try {
			anzTrx = web3.ethGetTransactionCount(wallet_adress_Azrael_UIG, DefaultBlockParameterName.LATEST)
					.sendAsync()
					.get();

	    System.out.println("AnzTrx: " + anzTrx.getTransactionCount());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//UIG Balance ermitteln
		
		System.out.println("UIG Funktion Balance of ...");
		org.web3j.abi.datatypes.Function function = balanceOf(wallet_adress_Azrael_UIG);
		String encodedFunction = FunctionEncoder.encode(function);

        try {
			org.web3j.protocol.core.methods.response.EthCall response = web3.ethCall(
			        Transaction.createEthCallTransaction(wallet_adress_Azrael_UIG, contract_adress_UIG, encodedFunction),
			        DefaultBlockParameterName.LATEST)
			        .sendAsync().get();
			
//			System.out.println("Anz UIG (uint256): " + response.getValue().substring(50,response.getValue().length()));
			BigInteger bigInt = new BigInteger(response.getValue().substring(50,response.getValue().length()), 16);		
			BigDecimal bigDecimal = new BigDecimal(bigInt.toString());
			BigDecimal dezimalstellen = new BigDecimal("100000000");
			bigDecimal = bigDecimal.divide(dezimalstellen);
			
			System.out.println("Balance: " + bigDecimal + " UIG");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
        
        
        
        EthFilter ethFilter = null;
		try {
			ethFilter = createFilterForEvent(contract_adress_UIG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        EthLog ethLog;
		try {
			ethLog = web3.ethGetLogs(ethFilter).send();
			System.out.println("AnzEvents: " + ethLog.getLogs().size());
			
			for (int i=0 ; i < ethLog.getLogs().size(); i++){
			   System.out.println("Event: " + ethLog.getLogs().get(i).toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.out.println("Ende ...");
		
/*		
		 // Programm so erweitern, dass Parameter ausgelesen oder uebergeben werden:
	      try {
			transferiereEther( "http://localhost:8545",
			        // Hier die Adressen der beiden Accounts eintragen (Ergebnis von: web3.eth.accounts):
			        "0x4597a26af9991b297b5ccc2a8c0966e9a1a17035",
			        "0x63d7d5b64dc9cc0744dedf87971f8b0777d7e226",
			        // Hier den zu transferierenden Betrag und die Passphrase eintragen:
			        "10", "Meine Ethereum-Test-Passphrase" );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	}
	
	public static void transferiereEther(
			String ethereumUrl, String senderAdresse, String empfaengerAdresse, String betragEther, String passphrase )
	    	throws Exception
	    	   {
	    	      Admin admin = Admin.build( new HttpService( ethereumUrl ) );
	    	      PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount( senderAdresse, passphrase ).send();
	    	      if( !personalUnlockAccount.accountUnlocked() ) {
	    	         System.out.println( "\nFehler: Account-Unlock fehlgeschlagen." );
	    	         return;
	    	      }
	    	      Web3j web3j = Web3j.build( new HttpService( ethereumUrl ) );
	    	      EthGetTransactionCount txCount = web3j.ethGetTransactionCount(
	    	            senderAdresse, DefaultBlockParameterName.LATEST ).sendAsync().get();
	    	      BigInteger nonce = txCount.getTransactionCount();
	    	      BigInteger betrag = Convert.toWei( betragEther, Convert.Unit.ETHER ).toBigInteger();
	    	      Transaction transaction = Transaction.createEtherTransaction(
	    	            senderAdresse, nonce, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, empfaengerAdresse, betrag );
	    	      EthSendTransaction response = web3j.ethSendTransaction( transaction ).sendAsync().get();
	    	      if( response.hasError() ) {
	    	         System.out.println( "\nFehler: " + response.getError().getMessage() );
	    	         return;
	    	      }
	    	      String txHash = response.getTransactionHash();
	    	      System.out.print( "\nTxHash: " + txHash + "\nWarten auf das Mining " );
	    	      for( int i = 0; i < 600; i++ ) {
	    	         Optional<TransactionReceipt> transactionReceipt =
	    	               web3j.ethGetTransactionReceipt( txHash ).send().getTransactionReceipt();
	    	         if( transactionReceipt.isPresent() ) {
	    	            System.out.println( "\nTransfer von " + betragEther + " Ether abgeschlossen, benutzte Gas-Menge: " +
	    	                                transactionReceipt.get().getGasUsed() );
	    	            return;
	    	         }
	    	         System.out.print( "." );
	    	         Thread.sleep( 1000 );
	    	      }
	    	      System.out.println( "\nDer Transfer konnte innerhalb von 10 Minuten nicht abgeschlossen werden. Ist Mining aktiv?" );
	    	   }
	    	   */		
		
	}
	
    private static org.web3j.abi.datatypes.Function balanceOf(String owner) {
        return new org.web3j.abi.datatypes.Function(
                "balanceOf",
                Collections.singletonList(new Address(owner)),
                Collections.singletonList(new TypeReference<Uint256>() {}));
    }
	
    public static String stringToHex(String base)
    {
     StringBuffer buffer = new StringBuffer();
     int intValue;
     for(int x = 0; x < base.length(); x++)
         {
         int cursor = 0;
         intValue = base.charAt(x);
         String binaryChar = new String(Integer.toBinaryString(base.charAt(x)));
         for(int i = 0; i < binaryChar.length(); i++)
             {
             if(binaryChar.charAt(i) == '1')
                 {
                 cursor += 1;
             }
         }
         if((cursor % 2) > 0)
             {
             intValue += 128;
         }
         buffer.append(Integer.toHexString(intValue) + " ");
     }
     return buffer.toString();
}
    
    

    private static EthFilter createFilterForEvent(
        String contractAddress) throws Exception {
        EthFilter ethFilter = new EthFilter(
                DefaultBlockParameterName.EARLIEST,
 //       		DefaultBlockParameterName.valueOf("0x2b6f8e"),
        		DefaultBlockParameterName.LATEST,
                contractAddress
        );

        //ethFilter.addSingleTopic(encodedEventSignature);
        return ethFilter;

}

	
}


