package eth;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.jdbc.Connection;



public class MyETHTask extends Application{
	
	public static void main(String[] args) {
		// Anwendung starten
		launch(args);
	}


	@Override
	public void start(Stage arg0) throws Exception {
		
		// GameWalletBalance einlesen falls ETH Blockchain erreichbar
		boolean available = ETHReader.availableTestETHRobsten();
		if (available) {
			ETHReader ropstenETHReader = new ETHReader();
			// ropstenETHReader.startETHReader("ropsten");
			MyGame.gameBalanceUIG = ropstenETHReader.getBalance("ropsten",
					MyGame.gameWallet, MyGame.contract);
			MyGame.aktBalanceString = new SimpleStringProperty("GameToken: "
					+ MyGame.gameBalanceUIG.intValue() + " UIG");
			System.out.println("GameBalance: " + MyGame.gameBalanceUIG);
			
			System.out.println("starte Transaktionstask ...");
			ETHReader.starteTransaktionsTask("ropsten");
		}
		
	}

}
