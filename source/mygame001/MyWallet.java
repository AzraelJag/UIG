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

public class MyWallet {
	
	//Konstanten
		public static String testnet_ropsten = "https://ropsten.infura.io/2JLVJ6Dk6Pz23Y9Xi7x3";
		public static String mainnet_ropsten = "https://mainnet.infura.io/2JLVJ6Dk6Pz23Y9Xi7x3";
		public static String contract_adress_UIG = "0x5ab39784b7ab27692dc2748dadfaefe314a712e5";
		public static String wallet_adress_Azrael_UIG = "0x035fd8795675134ceb2de5fb2e18755a49b76cdc";
		public static String wallet_adress_Azrael_TID = "0xc0d283a25c9b318ac9b033fa99a4da511590b0e0";
		                                                   
		
		
		public static void main(String[] args) {
			
			System.out.println("Start ... \n");

			//Connect to ETH Blockchain Network-Service
			Web3j web3t = Web3j.build(new HttpService(testnet_ropsten)); 
			Web3j web3m = Web3j.build(new HttpService(mainnet_ropsten));
			
			//TESTNet
			BigInteger gasPrice     = ETHBlockchain.getGasPrice(web3t);
			BigInteger blocknumbert = ETHBlockchain.getBlocknumber(web3t);
			
			System.out.println("Testnet (Robsten) \nETHBlockchain gasPrice    : " + gasPrice);
			System.out.println("ETHBlockchain blocknumber : " + blocknumbert);
			
			EthLog ethLogt = ETHBlockchain.getEthLog(web3t, contract_adress_UIG, blocknumbert);
			if (ethLogt != null) {
				for (int i=0 ; i < ethLogt.getLogs().size(); i++){
					System.out.println("TrxLog: " + ethLogt.getLogs().get(i).toString());
				}
			}else{
					System.out.println("Testnet (Robsten) \nTrxLog: nicht gefunden !");
			}
			
			//MAINNet
			BigInteger blocknumberm = ETHBlockchain.getBlocknumber(web3m);
			gasPrice     = ETHBlockchain.getGasPrice(web3m);
			
			System.out.println("MainNet \nETHBlockchain gasPrice    : " + gasPrice);
			System.out.println("ETHBlockchain blocknumber : " + blocknumberm);
			
			EthLog ethLogm = ETHBlockchain.getEthLog(web3m, contract_adress_UIG, blocknumberm);
			if (ethLogm != null) {
				for (int i=0 ; i < ethLogm.getLogs().size(); i++){
					System.out.println("TrxLog: " + ethLogm.getLogs().get(i).toString());
				}
			}else{
					System.out.println("MainNet \nTrxLog: nicht gefunden !");
			}
			
			System.out.println("Ende ...");
			
		}
		

}
