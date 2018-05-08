package eth;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

public class MyToken {
	
	public static int getAnzTrx(Web3j web3, String walletAdress){
		int anzTrxInt = 0;
		EthGetTransactionCount anzTrx;
		try {
			try {
				anzTrx = web3.ethGetTransactionCount(walletAdress, DefaultBlockParameterName.LATEST)
					.sendAsync()
					.get();
				anzTrxInt = Integer.parseInt(anzTrx.toString());
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		return anzTrxInt;
	}
			
	//MyToken Balance ermitteln
	public static BigDecimal getBalance(Web3j web3, String walletAdress, String contractAdress){
		org.web3j.abi.datatypes.Function function = balanceOf(walletAdress);
		String encodedFunction = FunctionEncoder.encode(function);
	
		BigDecimal balance = new BigDecimal("0");
    
		org.web3j.protocol.core.methods.response.EthCall response;
		try {
			response = web3.ethCall(
			        Transaction.createEthCallTransaction(walletAdress, contractAdress, encodedFunction),
			        DefaultBlockParameterName.LATEST)
			        .sendAsync().get();
			BigInteger bigInt = new BigInteger(response.getValue().substring(50,response.getValue().length()), 16);		
			BigDecimal bigDecimal = new BigDecimal(bigInt.toString());
			BigDecimal dezimalstellen = new BigDecimal("100000000");
			balance = bigDecimal.divide(dezimalstellen);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return balance;
	}
	
	// Contract Funktionen
    private static org.web3j.abi.datatypes.Function balanceOf(String owner) {
        return new org.web3j.abi.datatypes.Function(
                "balanceOf",
                Collections.singletonList(new Address(owner)),
                Collections.singletonList(new TypeReference<Uint256>() {}));
    }

}
