package allgemein;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import eth.MyGame;
import eth.WaitSeconds;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class Hilfsmethoden {
	
	private static int maxZeilen = 60000;

	// Hinweisfenster
	public static void setHinweisDialog(String hinweisText, ImageView image, int accNr) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Würfelergebnis");
		alert.setHeaderText("");
		alert.setContentText(hinweisText);
		if (accNr < 99){
			alert.setX( accNr * ((MyGame.displayX-250)/MyGame.anzahlAccounts)  +50);
			alert.setY(100);
		}
		if (!(image == null))
		   alert.setGraphic(image);
		
		alert.show();
		WaitSeconds.waitHere(1);
		alert.close();
	}
	
	// Hinweisfenster
	public static void setGewinnDialog(String hinweisText, ImageView image) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Du hast gewonnen !!!");
		alert.setHeaderText("");
		//alert.setContentText(hinweisText);
		alert.setGraphic(image);
		alert.showAndWait();
	}
	
	// Hinweisfenster
	public static void setkeinGuthabenDialog(String hinweisText, ImageView image) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setWidth(1000);
		alert.setTitle("Du hast leider kein Guthaben mehr");
		alert.setHeaderText("");
		alert.setContentText(hinweisText);
		alert.setGraphic(image);
		alert.showAndWait();
	}
	
	// Hinweisfenster
	public static void setGameWalletDialog(String hinweisText, ImageView image) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(hinweisText);
		alert.setHeaderText("");
		alert.setGraphic(image);
		//alert.setContentText(hinweisText);

		image.onMouseClickedProperty().set(new EventHandler<MouseEvent>()
		        {
	          @Override
	          public void handle(MouseEvent arg0)
	          {
	        	  final Clipboard clipboard = Clipboard.getSystemClipboard();
				    ClipboardContent content = new ClipboardContent();
				    content.putString(MyGame.gameWallet);
				    clipboard.setContent(content);
				    setHinweisDialog("WalletAdress copied to Clipboard", null, 99);
	          }
	        });

		alert.showAndWait();
	}
	
	// Textdialog
	public static String getTextDialog(String hinweisText) {
		TextInputDialog alert = new TextInputDialog();
		alert.setTitle("Bitte eingeben:");
		alert.setHeaderText("");
		alert.setContentText(hinweisText);
		//alert.setGraphic(image);
		Optional<String> result = alert.showAndWait();
		String eingabe = hinweisText;
		if (result.isPresent()) {
			eingabe = result.get();
		}
		return eingabe;
	}	
	

	
	// Auswahlfenster
	public static String setAuswahlDialog(String auswahlText, ImageView image, int level, int accNr) {
		final String[] arrayDataLv1 = { "Position 1/1", "Position 1/2", 
										"Position 2/1", "Position 2/2"};
		final String[] arrayDataLv2 = {	"Position 1/1", "Position 1/2", "Position 1/3", 
										"Position 2/1", "Position 2/2", "Position 2/3",
										"Position 3/1", "Position 3/2", "Position 3/3"};
		final String[] arrayDataLv3 = {	"Position 1/1", "Position 1/2", "Position 1/3","Position 1/4",
										"Position 2/1", "Position 2/2", "Position 2/3","Position 2/4",
										"Position 3/1", "Position 3/2", "Position 3/3","Position 3/4",
										"Position 4/1", "Position 4/2", "Position 4/3","Position 4/4"};
		final String[] arrayDataLv4 = {	"Position 1/1", "Position 1/2", "Position 1/3","Position 1/4","Position 1/5",
										"Position 2/1", "Position 2/2", "Position 2/3","Position 2/4","Position 2/5",
										"Position 3/1", "Position 3/2", "Position 3/3","Position 3/4","Position 3/5",
										"Position 4/1", "Position 4/2", "Position 4/3","Position 4/4","Position 4/5",
										"Position 5/1", "Position 5/2", "Position 5/3","Position 5/4","Position 5/5"};
		final List<String> dialogData;
		switch (level){
			case 1:
				dialogData = Arrays.asList(arrayDataLv1);
				break;
			case 2:
				dialogData = Arrays.asList(arrayDataLv2);
				break;
			case 3:
				dialogData = Arrays.asList(arrayDataLv3);
				break;
			case 4:
				dialogData = Arrays.asList(arrayDataLv4);
				break;
			default:
				dialogData = Arrays.asList(arrayDataLv1);
				break;
		}
		
		ChoiceDialog<String> dialog = new ChoiceDialog<String>(dialogData.get(0), dialogData);
		dialog.setHeaderText(auswahlText);
		dialog.setGraphic(image);
		dialog.setX(accNr * (((MyGame.displayX-250)/MyGame.anzahlAccounts)) + 50);
		dialog.setY(75);
		Optional<String> result = dialog.showAndWait();
			String selected = "cancelled.";
			if (result.isPresent()) {
			    selected = result.get();
			}
			return selected;
	}

	//
	// ASCII Datei in StringListe lesen
	//
	public static List<String> readLineByLineJava8(String filePath) {
		List<String> liste = new ArrayList<String>();
		try (Stream<String> stream = Files.lines(Paths.get(filePath),
				StandardCharsets.ISO_8859_1)) {
			stream.limit(maxZeilen).forEach(s -> liste.add(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return liste;
	}

	//
	// Timestamp in String aufbereiten
	//
	public static String TStoString(String TS) {
		String TSString = new String();
		if (!TS.isEmpty() && TS.length() == 13)
			TSString = TS.substring(7, 9) + '.' + TS.substring(5, 7) + '.'
					+ TS.substring(1, 5) + ' ' + TS.substring(9, 11) + ':'
					+ TS.substring(11, 13);
		return TSString;
	}
	//
	// Timestamp in String aufbereiten
	//
	public static String T14StoString(String TS) {
		String TSString = new String();
		if (!TS.isEmpty() && TS.length() == 14)
			TSString = TS.substring(6, 8) + '.' + TS.substring(4, 6) + '.'
					+ TS.substring(0, 4) + ' ' + TS.substring(8, 10) + ':'
					+ TS.substring(10, 12);
		return TSString;
	}

	// 
	// Ergebnis des WS-Calls in einem eigenen Fester ausgeben
	//
	public static Tab resultWindow(TextArea resultTextArea, String titel) {
		Tab resultTab = new Tab();
		resultTab.setText(titel);
		resultTextArea.setStyle("-fx-font-family: monospace");
		resultTextArea.setMaxSize(
				Toolkit.getDefaultToolkit().getScreenSize().width * 0.5,
				Toolkit.getDefaultToolkit().getScreenSize().height * 0.9);
		resultTextArea.setMinSize(
				Toolkit.getDefaultToolkit().getScreenSize().width * 0.5,
				Toolkit.getDefaultToolkit().getScreenSize().height * 0.9);
		resultTextArea.setEditable(false);

		// FlowPane resultPane = new FlowPane();
		ScrollPane resultPane = new ScrollPane();
		resultPane.autosize();
		resultPane.setContent(resultTextArea);

		resultTab.setContent(resultPane);
		return resultTab;
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

	
	   

}
