package eth;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;

import javafx.concurrent.Task;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.utils.Numeric;

public class ETHBlockchain {

	//getGasPrice
	public static BigInteger getGasPrice(Web3j web3){
		BigInteger gasprice = new BigInteger("0");
		try {
			gasprice = web3.ethGasPrice().send().getGasPrice();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gasprice;
	}
	
	//getBlocknumber
	public static BigInteger getBlocknumber(Web3j web3){
		BigInteger blocknumber = new BigInteger("0");
		try {
			blocknumber = web3.ethBlockNumber().send().getBlockNumber();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blocknumber;
	}
	
	// Transaktionen suchen
	public static EthLog getEthLog(Web3j web3, String contractAdress, BigInteger letzterBlock){
		EthLog ethLog = null;
		EthFilter ethFilter = null;
			try {
				BigInteger blockStart = letzterBlock.subtract(new BigInteger("10000"));
				ethFilter = createFilterForEvent(contractAdress, blockStart, letzterBlock);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ethLog = web3.ethGetLogs(ethFilter).send();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return ethLog;
	}
	

	
	
	// private Proceduren =========================================================================
	// ETh Filter für Transaktionsabfrage im Blockbereich
    private static EthFilter createFilterForEvent(
            String contractAddress, BigInteger blockBeginn, BigInteger blockEnde) throws Exception {

            EthFilter ethFilter = new EthFilter(
    //          DefaultBlockParameterName.EARLIEST,
    //     		DefaultBlockParameterName.LATEST,
            	new DefaultBlockParameterNumber(blockBeginn),
            	new DefaultBlockParameterNumber(blockEnde),
            	contractAddress
            );
            //ethFilter.addSingleTopic(encodedEventSignature);
            return ethFilter;
    }
    
    
	

}
