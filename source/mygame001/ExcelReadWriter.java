package eth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReadWriter {
	
	public static void readXLSXFileToAccountTable(File file, String username, String userpw) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
		//System.out.println(dateiname);
		InputStream ExcelFileToRead = new FileInputStream(dateiname);

		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell =  null;

		String id = new String();
		String name = new String();
		String pw = new String();
		String wallet = new String();
		String balance  = new String();
		String zellInhalt = new String();
		
		MyGame.accounts.clear();
		Iterator rows = sheet.rowIterator();
		int i = 0; //Spalte
		int j = 0; //Zeile
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			Boolean zeilenende = false;
			i=0;
//			while (cells.hasNext()){
			while (i<100 && !zeilenende){
//				System.out.println("i:" + i);
				if (j>0){						//erste Zeile überlesen (Überschriften)
//					System.out.println("j:" + j);
					// leere Zelle auf leer setzen
					if (sheet.getRow(j).getCell(i) == null) {
						cell = sheet.getRow(0).getCell(0);
						cell.setCellValue(" ");
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						if (i>3) {
							zeilenende = true;
						}
					}else{
						cell=(XSSFCell) cells.next();
						}
					
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						zellInhalt=cell.getStringCellValue().trim();
					}
					else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						zellInhalt=(new Double(cell.getNumericCellValue()).toString());
					}
						else{
						//U Can Handel Boolean, Formula, Errors
					}
				
					switch (i) {
						case 0:
							id=zellInhalt.trim();						
							break;
						case 1:
							name=zellInhalt.trim();		
							break;
						case 2:
							pw=zellInhalt.trim();		
							break;
						case 3:
							wallet=zellInhalt.trim();		
							break;
						case 4:
							balance=zellInhalt.trim();		
							break;
						default:
//							System.out.println("Inhalt:"+zellInhalt+":");
						 	if (zeilenende) {
						 		//Accounts füllen
						 		if (name.equals(username) && pw.equals(pw)){
						 			MyGame.accounts.add(new Account(
					               		id,
					               		name,
					               		pw,
					               		wallet,
					               		balance));
						 			//transaktionAccounts initialisieren
						 			MyGame.trxAcountHistorie.add(new AccountTransaktionList(id));
						 		}
						 	} 
						 	break;
					}
				}
				i++;
			}
			j++;
		}
		wb.close();
	}
	
	public static void writeAccountTableToXLSXFile(File file) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
		//System.out.println(dateiname);
		
		String sheetName = "Accounts";

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating i number of rows
	    Account ausg;

	    String id = new String();
		String name = new String();
		String pw = new String();
		String wallet = new String();
		String balance  = new String();

		XSSFCell cell;
		
		// Spaltenüberschriften
	    XSSFRow row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("ID");
		cell = row.createCell(1);
		cell.setCellValue("Name");
		cell = row.createCell(2);
		cell.setCellValue("Passwort");
		cell = row.createCell(3);
		cell.setCellValue("Walletadresse");
		cell = row.createCell(4);
		cell.setCellValue("Anzahl Token");
	
		for (int i=0; i<MyGame.accounts.size() ; i++){
			ausg 	= MyGame.accounts.get(i);
			
        	id 		= 	ausg.getId();
        	name 	= 	ausg.getName();
        	pw 		= 	ausg.getPw();
         	wallet 	= 	ausg.getWalletAddress();
         	balance = 	ausg.getBalance().toString();
         	
			row = sheet.createRow(i+1);

			cell = row.createCell(0);
			cell.setCellValue(id);
			cell = row.createCell(1);
			cell.setCellValue(name);
			cell = row.createCell(2);
			cell.setCellValue(pw);
			cell = row.createCell(3);
			cell.setCellValue(wallet);
			cell = row.createCell(4);
			cell.setCellValue(balance);
		}

		FileOutputStream fileOut = new FileOutputStream(dateiname);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		wb.close();
		fileOut.flush();
		fileOut.close();
	}
		

	
	public static void readXLSXFileToItemTable(File file) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
//		System.out.println(dateiname);
		InputStream ExcelFileToRead = new FileInputStream(dateiname);

		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell =  null;

		String id = new String();
		String name = new String();
		String costFiat = new String();
		String costUIG  = new String();
		String level  = new String();
		String position  = new String();
		String thema  = new String();
		String zellInhalt = new String();
		
		MyGame.items.clear();
		Iterator rows = sheet.rowIterator();
		int i = 0; //Spalte
		int j = 0; //Zeile
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			Boolean zeilenende = false;
			i=0;
//			while (cells.hasNext()){
			while (i<100 && !zeilenende){
//				System.out.println("i:" + i);
				if (j>0){						//erste Zeile überlesen (Überschriften)
//					System.out.println("j:" + j);
					// leere Zelle auf leer setzen
					if (sheet.getRow(j).getCell(i) == null) {
						cell = sheet.getRow(0).getCell(0);
						cell.setCellValue(" ");
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						if (i>6) {
							zeilenende = true;
						}
					}else{
						cell=(XSSFCell) cells.next();
						}
					
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						zellInhalt=cell.getStringCellValue().trim();
					}
					else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						zellInhalt=(new Double(cell.getNumericCellValue()).toString());
					}
						else{
						//You Can Handel Boolean, Formula, Errors
					}
				
					switch (i) {
						case 0:
							id=zellInhalt.trim();						
							break;
						case 1:
							name=zellInhalt.trim();		
							break;
						case 2:
							costFiat=zellInhalt.trim();		
							break;
						case 3:
							costUIG=zellInhalt.trim();		
							break;
						case 4:
							level=zellInhalt.trim();		
							break;
						case 5:
							position=zellInhalt.trim();		
							break;
						case 6:
							thema=zellInhalt.trim();		
							break;
						default:
//							System.out.println("Inhalt:"+zellInhalt+":");
						 	if (zeilenende) {
/*						 		System.out.println("Name:" + name);
						 		System.out.println("CostFiat:" + costFiat);
						 		System.out.println("CostUIG:" + costUIG);
						 		System.out.println("Level:" + level);
						 		System.out.println("Position:" + position);
						 		System.out.println("Würfelpos:" + position+1);
						 		System.out.println("Thema:" + wertigkeit);
						 		System.out.println("-----------------------------");
*/						 		MyGame.items.add(new Item(
					               		id,
					               		name,
					               		costFiat,
					               		costUIG,
					               		level,
					               		position,
					               		thema));
						 	} 
						 	break;
					}
				}
				i++;
			}
			j++;
		}
		wb.close();
	}
	
	public static void readXLSXFileToAccountItemTable(File file, String useraccid) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
		InputStream ExcelFileToRead = new FileInputStream(dateiname);

		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell =  null;

		String altAccId = new String(" ");
		String accId = new String();
		String itemId = new String();
		String count = new String();
		String zellInhalt = new String();
		
		ObservableList<Item> items =
			        FXCollections.observableArrayList();
		
		AccountItemList accountItems = new AccountItemList("");
		
		MyGame.accountItemList.clear();
		Iterator rows = sheet.rowIterator();
		int i = 0; //Spalte
		int j = 0; //Zeile
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			Boolean zeilenende = false;
			i=0;
//			while (cells.hasNext()){
			while (i<100 && !zeilenende){
//				System.out.println("i:" + i);
				if (j>0){						//erste Zeile überlesen (Überschriften)
//					System.out.println("j:" + j);
					// leere Zelle auf leer setzen
					if (sheet.getRow(j).getCell(i) == null) {
						cell = sheet.getRow(0).getCell(0);
						cell.setCellValue(" ");
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						if (i>2) {
							zeilenende = true;
						}
					}else{
						cell=(XSSFCell) cells.next();
						}
					
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						zellInhalt=cell.getStringCellValue().trim();
					}
					else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						zellInhalt=(new Double(cell.getNumericCellValue()).toString());
					}
						else{
						//U Can Handel Boolean, Formula, Errors
					}
				
					switch (i) {
						case 0:
							accId=zellInhalt.trim();						
							break;
						case 1:
							itemId=zellInhalt.trim();		
							break;
						case 2:
							count=zellInhalt.trim();		
							break;
						default:
							//System.out.println("Inhalt:" + zellInhalt );
						 	if (zeilenende) {
						 		if (!altAccId.equals(accId)){
						 		//neuer Account = altes Objekt ablegen
						 			if (j>1)
						 				MyGame.accountItemList.add(accountItems);
						 		    //neues Objekt anlegen
						 			accountItems = new AccountItemList(accId);
						 			accountItems.addItem(itemId, count, false);
						 			altAccId = accId;
						 		}else{
						 			//System.out.println("neues item: " + itemId);
						 			//gleicher Account = Item hinzufügen
						 			accountItems.addItem(itemId, count, false);
						 		}
						 		MyGame.accountItemList.add(accountItems);
						 	} 
						 	break;
					}
				}
				i++;
			}
			j++;
		}
		wb.close();
	}
	
	public static void writeAccountItemTableToXLSXFile(File file) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
		//System.out.println(dateiname);
		
		String sheetName = "AccountItems";

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//Excel-Spalten
	    String accId  = new String();
		String itemId = new String();
		String count = new String();

		XSSFCell cell;
		
		// Spaltenüberschriften
	    XSSFRow row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("Account-ID");
		cell = row.createCell(1);
		cell.setCellValue("Item-ID");
		cell = row.createCell(2);
		cell.setCellValue("Anzahl");
		int rowNr=1;
		ObservableList<Item> itemList = FXCollections.observableArrayList();
		for (int i=0; i<MyGame.accountItemList.size() ; i++){
			accId 	= MyGame.accountItemList.get(i).getId();
			itemList.clear();
			itemList = MyGame.accountItemList.get(i).getItemList();
			
/*			boolean gefunden=false;
			ObservableList<Item> itemList = FXCollections.observableArrayList();
			for (int z=0; z < MyGame.accountItemList.size() && !gefunden; z++){
				if (MyGame.accountItemList.get(z).getId().equals(accId)){
					itemList = MyGame.accountItemList.get(z).getItemList();
					gefunden = true;
				}
			}
*/
			
			for (int j=0; j<itemList.size(); j++ ){
				itemId 	= 	itemList.get(j).getId();
				count	= 	itemList.get(j).getCount().toString();
         	
				row = sheet.createRow(rowNr);
				cell = row.createCell(0);
				cell.setCellValue(accId);
				cell = row.createCell(1);
				cell.setCellValue(itemId);
				cell = row.createCell(2);
				cell.setCellValue(count);
				rowNr++;
			}
		}
		FileOutputStream fileOut = new FileOutputStream(dateiname);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		wb.close();
		fileOut.flush();
		fileOut.close();
	}
	
	
	public static void readXLSXFileToAccountTransaktionTable(File file) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
		//System.out.println(dateiname);
		InputStream ExcelFileToRead = new FileInputStream(dateiname);

		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell =  null;
		
		String altAccId = new String(" ");
		String accId = new String();

		String id = new String();
		String transaktionString = new String();
		String zellInhalt = new String();
		
		AccountTransaktionList accountTransaktionen = new AccountTransaktionList("");
		
		MyGame.trxAcountHistorie.clear();
		Iterator rows = sheet.rowIterator();
		int i = 0; //Spalte
		int j = 0; //Zeile
		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			Boolean zeilenende = false;
			i=0;
//			while (cells.hasNext()){
			while (i<100 && !zeilenende){
//				System.out.println("i:" + i);
				if (j>0){						//erste Zeile überlesen (Überschriften)
//					System.out.println("j:" + j);
					// leere Zelle auf leer setzen
					if (sheet.getRow(j).getCell(i) == null) {
						cell = sheet.getRow(0).getCell(0);
						cell.setCellValue(" ");
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						if (i>1) {
							zeilenende = true;
						}
					}else{
						cell=(XSSFCell) cells.next();
						}
					
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						zellInhalt=cell.getStringCellValue().trim();
					}
					else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						zellInhalt=(new Double(cell.getNumericCellValue()).toString());
					}
						else{
						//U Can Handel Boolean, Formula, Errors
					}
				
					switch (i) {
						case 0:
							accId=zellInhalt.trim();						
							break;
						case 1:
							transaktionString=zellInhalt.trim();		
							break;
						default:
//							System.out.println("Inhalt:"+zellInhalt+":");
						 	if (zeilenende) {
						 		if (!altAccId.equals(accId)){
							 		//neuer Account = altes Objekt ablegen
							 		if (j>1){
							 			// altes Objekt ablegen
						 				MyGame.trxAcountHistorie.add(accountTransaktionen);
							 		}
							 		//neues Objekt anlegen
							 		accountTransaktionen = new AccountTransaktionList(accId);
							 		Transaktion trx = new Transaktion(transaktionString);
							 		accountTransaktionen.addTransaktion(trx);
							 		altAccId = accId;
							 	}else{
							 		//gleicher Account = Transaktion hinzufügen
							 		Transaktion trx = new Transaktion(transaktionString);
							 		accountTransaktionen.addTransaktion(trx);
							 	}
						 			//MyGame.trxAcountHistorie.add(accountTransaktionen);
						 	} 
						 	break;
					}
				}
				i++;
			}
			j++;
		}
		MyGame.trxAcountHistorie.add(accountTransaktionen);
		wb.close();
	}
	
	public static void writeAccountTransaktionTableToXLSXFile(File file) throws IOException
	{
		String dateiname = file.toString().replace("\\", "/");
		//System.out.println(dateiname);
		
		String sheetName = "AccountTransaktionen";

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//Excel-Spalten
	    String accId  = new String();
		String trxString = new String();

		XSSFCell cell;
		
		// Spaltenüberschriften
	    XSSFRow row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("Account-ID");
		cell = row.createCell(1);
		cell.setCellValue("Transaktion");
		int rowNr=1;
		ObservableList<Transaktion> trxList = FXCollections.observableArrayList();
		for (int i=0; i<MyGame.trxAcountHistorie.size() ; i++){
			accId 	= MyGame.trxAcountHistorie.get(i).getId();
			trxList.clear();
			trxList = MyGame.trxAcountHistorie.get(i).getItemList();

			
			for (int j=0; j<trxList.size(); j++ ){
				trxString = trxList.get(j).getTrxString();
         	
				row = sheet.createRow(rowNr);
				cell = row.createCell(0);
				cell.setCellValue(accId);
				cell = row.createCell(1);
				cell.setCellValue(trxString);
				rowNr++;
			}
		}
		FileOutputStream fileOut = new FileOutputStream(dateiname);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		wb.close();
		fileOut.flush();
		fileOut.close();
	}

}
