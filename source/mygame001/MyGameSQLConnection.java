package eth;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import allgemein.Hilfsmethoden;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class MyGameSQLConnection {

	public static Connection con = null;
	//private static String dbHost = "localhost"; // Hostname
	//private static String dbUser = "root"; // Datenbankuser
	//private static String dbPass = ""; // Datenbankpasswort


	// public static String User_Group;

	private MyGameSQLConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Datenbanktreiber für JDBC
													// Schnittstellen laden.

			// Verbindung zur JDBC-Datenbank herstellen.
			con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
					+ dbPort + "/" + dbName + "?" + "user=" + dbUser + "&"
					+ "password=" + dbPass);
		} catch (ClassNotFoundException e) {
			System.out.println("DB (MySQL-Treiber) nicht gefunden");
		} catch (SQLException e) {
			System.out.println("Verbindung nicht moglich");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}

	private static Connection getInstance() {
		if (con == null)
			new MyGameSQLConnection();
		return con;
	}

	// Speichere letzten User-Login-TS
	public static void putLoginTS(String userid) {
		java.sql.PreparedStatement query;
		String aktTS = Hilfsmethoden.getAktTS();
		try {
			query = con
					.prepareStatement("UPDATE ACCOUNT SET LAST_LOGIN_TS = ? WHERE USER_ID = ?");
			query.setString(1, aktTS);
			query.setString(2, userid);
			query.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Speichere letzten User-Login-TS
	public static void addItemCount(String userid, String itemid) {
		java.sql.PreparedStatement query;
		String aktTS = Hilfsmethoden.getAktTS();
		try {
			query = con
					.prepareStatement("UPDATE USER_ITEMS SET ANZAHL = ANZAHL +1 "
							+ "WHERE USER_ID = ? " 
							+ "AND ITEM_ID = ?");
			query.setString(1, userid);
			query.setString(2, itemid);
			query.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// neues Item in USER_ITEMS eintragen
	public static boolean addItemToUser(String userid, String itemid) {
		boolean erfolgreich = false;
		con = getInstance();
		if (con != null) {
			java.sql.PreparedStatement query;
			try {
				query = con
						.prepareStatement("INSERT INTO USER_ITEMS (USER_ID, ITEM_ID, ANZAHL, AEND_TS) VALUES (?, ?, 1, ?)");
				query.setString(1, userid);
				query.setString(2, itemid);
				query.setString(3, Hilfsmethoden.getAktTS());
				// query.setString(4, text);
				query.executeUpdate();
				erfolgreich = true;
			} catch (SQLException e) {
				System.out.println("Error User_Items " + userid + itemid 
						+ " speichern");
				e.printStackTrace();
				erfolgreich = false;
			}
		}
		return erfolgreich;
	}

	// Lese Account-Daten
	public static boolean fillUserAccount(String name, String pw) {
		boolean dbvorhanden = false;
		Statement query;
		con = getInstance();
		if (con != null) {
			//System.out.println("fillUserAccount: " + name + " " + pw); 
			try {
				query = con.createStatement();
				dbvorhanden = true;
				// Tabelle anzeigen
				String sql = "SELECT USER_ID, USER_NAME, PW, WALLET_ADRESS, EMAIL, SPRACH_CODE, FIRST_TS, AEND_TS, LAST_LOGIN_TS, BALANCE "
						+ "FROM USER "
						+ "WHERE USER_NAME like '" + name + "' AND PW like '" + pw + "' ";
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				//System.out.println("Accounts:");
				//System.out.println("=========");
				while (result.next()) {
					String r_user_id = result.getString("USER_ID");
					String r_user_name = result.getString("USER_NAME");
					String r_pw = result.getString("PW");
					String r_wallet_adress = result.getString("WALLET_ADRESS");
					String r_email = result.getString("EMAIL");
					String r_sprache = result.getString("SPRACH_CODE");
					String r_first_ts = result.getString("FIRST_TS");
					String r_aend_ts = result.getString("AEND_TS");
					String r_last_login_ts = result.getString("LAST_LOGIN_TS");
					String r_balance = result.getString("BALANCE");
					/*
					System.out.println(r_user_id + r_user_name + r_pw + r_wallet_adress
							+ r_email + r_sprache + r_first_ts + r_aend_ts
							+ r_last_login_ts + r_balance);
					*/
					MyGame.accounts.add(new Account(
							r_user_id,
							r_user_name,
							r_pw,
							r_wallet_adress,
							r_balance));
					//System.out.println("neuer Account:" + MyGame.accounts.get(0).getId());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dbvorhanden;
	}

	// Lese ITEM-Daten
	public static boolean fillItem() {
		boolean dbvorhanden = false;
		con = getInstance();
		if (con != null) {
			Statement query;
			try {
				query = con.createStatement();
				dbvorhanden = true;
				// Ergebnisstabelle durchforsten
				String sql = "SELECT ITEM_ID, ITEM_NAME, COST_FIAT, COST_TOKEN, WUERFEL_POS, BILD_POS, LEVEL, THEMA "
						+ "FROM ITEMS " + "ORDER BY ITEM_ID";
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				//System.out.println("Items:");
				//System.out.println("=========");
				while (result.next()) {
					String r_item_id = result.getString("ITEM_ID");
					String r_item_name = result.getString("ITEM_NAME");
					String r_cost_fiat = result.getString("COST_FIAT");
					String r_cost_token = result.getString("COST_TOKEN");
					String r_wuerfel_pos = result.getString("WUERFEL_POS");
					String r_bild_pos = result.getString("BILD_POS");
					String r_level = result.getString("LEVEL");
					String r_thema = result.getString("THEMA");
					MyGame.items.add(new Item(r_item_id, r_item_name,
							r_wuerfel_pos, r_wuerfel_pos, r_level, r_bild_pos,
							r_thema));
					//System.out.println(r_item_id + r_item_name + r_cost_fiat
					//		+ r_cost_token + r_wuerfel_pos + r_bild_pos
					//		+ r_level + r_thema);

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dbvorhanden;
	}

	// Lese ACCOUNT-ITEMS-Daten
	public static void fillAccountItems(String userid) {
		con = getInstance();
		if (con != null) {
			Statement query;
			try {
				query = con.createStatement();
				// Ergebnisstabelle durchforsten
				String sql = "SELECT USER_ID, ITEM_ID, ANZAHL, AEND_TS "
						+ "FROM USER_ITEMS " + "WHERE USER_ID = '" + userid
						+ "' " + "ORDER BY ITEM_ID";
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				//System.out.println("Account-Items:");
				//System.out.println("==============");
				AccountItemList accountItems = new AccountItemList(userid);
				while (result.next()) {
					String r_user_id = result.getString("USER_ID");
					String r_item_id = result.getString("ITEM_ID");
					int r_anzahl = result.getInt("ANZAHL");
					String r_aend_TS = result.getString("AEND_TS");
					//System.out.println(r_user_id + r_item_id + r_anzahl
					//		+ r_aend_TS);
					accountItems.addItem(r_item_id, Integer.toString(r_anzahl), false);
				}
				MyGame.accountItemList.add(accountItems);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// prüfe auf neue ACCOUNT-TRANSAKTIONEN-Daten und schreibe dem Account gut
	public static void fillAccountTransaktionen(String userid, String blockchain) {
		con = getInstance();
		if (con != null) {
			Integer letzterBlockInt = 0;
			String user_wallet = new String();
			Statement query;

			// letzten USER-Transaktion Eintrag lesen
			try {
				query = con.createStatement();
				String sql = "SELECT MAX(BLOCKNUMMER) AS MAXBLOCKNUMMER "
						+ "FROM USER_TRANSAKTIONEN " + "WHERE USER_ID = '"
						+ userid + "' " + "AND BLOCKCHAIN = '" + blockchain
						+ "' ";
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				System.out.println("Account-Transaktionen:");
				System.out.println("======================");
				while (result.next()) {
					letzterBlockInt = result.getInt("MAXBLOCKNUMMER");
					System.out
							.println("letzte Blocknummer: " + letzterBlockInt);
				}
			} catch (SQLException e) {
				System.out.println("keine User Transaktion gefunden");
				e.printStackTrace();
			}

			// aktuelle User Wallet Adresse lesen
			try {
				query = con.createStatement();
				// neue Einträge in der Transaktionshistorie suchen und
				// verarbeiten
				String sql = "SELECT WALLET_ADRESS " + "FROM USER "
						+ "WHERE USER_ID = '" + userid + "' ";
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten

				while (result.next()) {
					user_wallet = result.getString("WALLET_ADRESS");
					System.out.println("User-Wallet: " + user_wallet);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// neue Einträge in der Transaktionshistorie suchen und verarbeiten
			try {
				query = con.createStatement();
				// neue Einträge in der Transaktionshistorie suchen und
				// verarbeiten
				String sql = "SELECT BLOCKCHAIN, BLOCKNUMMER, TRANSAKTION "
						+ "FROM TRANSAKTIONSHISTORIE "
						+ "WHERE BLOCKNUMMER > '" + letzterBlockInt + "' "
						+ "AND BLOCKCHAIN 	 = '" + blockchain + "' ";
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				System.out.println("Account-Transaktionen-Gutschreiben:");
				System.out.println("===================================");
				while (result.next()) {
					String r_transaktion = result.getString("TRANSAKTION");
					String r_blockchain = result.getString("BLOCKCHAIN");
					int r_blocknummer = result.getInt("BLOCKNUMMER");
					// System.out.println("Trx-Historie : " + r_transaktion);
					Transaktion trx = new Transaktion(r_transaktion);
					// System.out.println("User-Adress: " +
					// MyGame.gameWallet.substring(2,MyGame.gameWallet.length()).toUpperCase());
					// System.out.println("SendeAdress: " +
					// trx.getEmpfangsAdresse().toUpperCase());

					// falls neue Transaktion für den user gefunden, dannn
					// gutschreiben
					if ((trx.getSendeAdresse()
							.substring(2, trx.getSendeAdresse().length())
							.toUpperCase().contains(user_wallet.substring(2,
							user_wallet.length()).toUpperCase()))
							&& (trx.getEmpfangsAdresse()
									.substring(2,
											trx.getEmpfangsAdresse().length())
									.toUpperCase().contains(MyGame.gameWallet
									.substring(2, MyGame.gameWallet.length())
									.toUpperCase()))) {
						System.out.println("neueTrx gefunden: "
								+ trx.getDataAsBigDecimal8() + "UIG");

						boolean vorhanden = false;
						try {
							query = con.createStatement();
							// neue Einträge in der Transaktionshistorie suchen
							// und
							// verarbeiten
							String sqlvorhanden = "SELECT COUNT(*) AS ANZAHL "
									+ "FROM USER_TRANSAKTIONEN "
									+ "WHERE BLOCKNUMMER = '" + r_blocknummer + "' " 
									+ "AND BLOCKCHAIN 	 = '" + blockchain + "' ";
							ResultSet resultvorhanden = query
									.executeQuery(sqlvorhanden);
							// Ergebnisstabelle durchforsten
							System.out
									.println("Account-Transaktionen-Gutschreiben:");
							System.out
									.println("===================================");
							while (resultvorhanden.next()) {
								int r_count = resultvorhanden.getInt("ANZAHL");
								if (r_count > 0) {
								vorhanden = true;
								System.out
											.println("User_Transaktion bereits vorhanden: "
													+ r_blocknummer);
								}
							}

						} catch (SQLException e) {
							// System.out.println("Error User_Transaktion " +
							// r_blocknummer
							// + " speichern");
							e.printStackTrace();
						}

						if (!vorhanden) {
							java.sql.PreparedStatement userTrxquery;
							boolean erfolgreich = false;
							try {
								userTrxquery = con
										.prepareStatement("INSERT INTO USER_TRANSAKTIONEN (USER_ID, BLOCKCHAIN, BLOCKNUMMER, WERT, TRANSAKTION, AEND_TS) VALUES (?, ?, ?, ?, ?, ?)");
								userTrxquery.setString(1, userid);
								userTrxquery.setString(2, blockchain);
								userTrxquery.setInt(3, r_blocknummer);
								userTrxquery.setString(4, trx
										.getDataAsBigDecimal8().toString());
								userTrxquery.setString(5, r_transaktion);
								userTrxquery.setString(6,
										Hilfsmethoden.getAktTS());
								// query.setString(4, text);
								userTrxquery.executeUpdate();
								erfolgreich = userBalanceGutschreiben(userid,
										trx.getDataAsBigDecimal8().toString());

							} catch (SQLException e) {
								// System.out.println("Error User_Transaktion "
								// + r_blocknummer
								// + " speichern");
								e.printStackTrace();
								erfolgreich = false;
							}
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean userBalanceGutschreiben(String userid, String wert) {
		boolean erfolgreich = false;
		con = getInstance();
		if (con != null) {
			Statement query;
			try {
				//System.out.println("USERID:" + userid + " Wert: " + wert);
				query = con.createStatement();
				// aktuelle User Balance ermitteln
				String sql = "SELECT BALANCE " + "FROM USER "
						+ "WHERE USER_ID = '" + userid + "' ";
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				while (result.next()) {
					String r_balance = result.getString("BALANCE");
					//System.out.println("USERBALANCE-vorher:" + r_balance);
					BigDecimal aktBalance = new BigDecimal(r_balance);
					BigDecimal addBalance = new BigDecimal(wert);
					aktBalance = aktBalance.add(addBalance);
					//System.out.println("USERBALANCE-nachher:" + aktBalance);
					//neueBalance eintragen
					java.sql.PreparedStatement queryupd;
					try {
						queryupd = con
								.prepareStatement("UPDATE USER SET BALANCE =  '" + aktBalance.toString() + "' " 
													+ "WHERE USER_ID = '" + userid + "' ");
						queryupd.executeUpdate();
						erfolgreich = true;
					} catch (SQLException e) {
						System.out.println("Error User Balance updaten");
						e.printStackTrace();
						erfolgreich = false;
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return erfolgreich;
	}

	// Lese letzten eingelesenen Block
	public static BigInteger letzterGelesenerBlock(String env) {
		BigInteger letzterGelsenerBlockIntBigInt = BigInteger.valueOf(1);
		con = getInstance();
		Statement query;
		try {
			query = con.createStatement();
			// Ergebnisstabelle durchforsten
			String sql = "SELECT MAX(BLOCKNUMMER) AS MAXBLOCKNUMMER "
					+ "FROM TRANSAKTIONSHISTORIE " + "WHERE BLOCKCHAIN = '"
					+ env + "' ";
			ResultSet result = query.executeQuery(sql);
			// Ergebnisstabelle durchforsten
			while (result.next()) {
				Integer letzterGelsenerBlockInt = result
						.getInt("MAXBLOCKNUMMER") + 1;
				letzterGelsenerBlockIntBigInt = BigInteger
						.valueOf(letzterGelsenerBlockInt);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return letzterGelsenerBlockIntBigInt;
	}
	
	// Lese letzten eingelesenen Block
		public static String ermittleUserid(String adresse) {
			String userid = new String();
			String upperadresse = adresse.substring(2,adresse.length()).toUpperCase();
			upperadresse = upperadresse.substring(upperadresse.length()-40, upperadresse.length());
			System.out.println("Upperadresse: " + upperadresse);
			con = getInstance();
			if (con != null) {
			Statement query;
			try {
				query = con.createStatement();
				// Usertabelle durchforsten
				String sql = "SELECT USER_ID "
						+ "FROM USER " 
						+ "WHERE UPPER(WALLET_ADRESS) = '0X" + upperadresse + "' ";
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				while (result.next()) {
					userid = result	.getString("USER_ID");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
			return userid;
		}

	// neue Transaktionen in Transaktionshistorie speichern
	public static boolean putTrxToHistorie(int blocknummer, String transaktion) {
		boolean erfolgreich = false;
		con = getInstance();
		if (con != null) {
			java.sql.PreparedStatement query;
			try {
				query = con
						.prepareStatement("INSERT INTO transaktionshistorie (BLOCKCHAIN,BLOCKNUMMER,TRANSAKTION,AEND_TS) VALUES ('ropsten', ?, ?, ?)");
				query.setInt(1, blocknummer);
				query.setString(2, transaktion);
				query.setString(3, Hilfsmethoden.getAktTS());
				// query.setString(4, text);
				query.executeUpdate();
				erfolgreich = true;
			} catch (SQLException e) {
				System.out.println("Error Transaktion " + blocknummer
						+ " speichern");
				e.printStackTrace();
				erfolgreich = false;
			}
		}
		return erfolgreich;
	}

	// Login prüfen
	public static boolean pruefeLogin(String userid, String pw) {
		//System.out.println("User: " + userid);
		//System.out.println("PW: " + pw);
		boolean erfolgreich = false;
		con = getInstance();
		if (con != null) {
			Statement query;
			try {
				query = con.createStatement();
				// Usertabelle durchforsten
				String sql = "SELECT USER_NAME "
						+ "FROM USER " 
						+ "WHERE USER_NAME = '" + userid + "' "
						+ "AND PW = '" + pw + "' " ;
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				while (result.next()) {
					String r_userid = result.getString("USER_NAME");
					if (r_userid.equals(userid)){
						erfolgreich = true;
					}
				}
			} catch (SQLException e) {
				erfolgreich = false;
				e.printStackTrace();
			}
		}
		return erfolgreich;
	}
	
	// User-Profil lesen
	public static boolean getProfile(MyProfile profile) {
		//System.out.println("User: " + userid);
		//System.out.println("PW: " + pw);
		boolean erfolgreich = false;
		profile.setPW("das Passwort");
		profile.setName("der Username");
		profile.setSprache("de");
		profile.setEmail("xxx@yyy");
		profile.setWallet("ssssssssswer4554353frfgfg54345t3453");
		profile.setBalanceUIG(new BigDecimal("85.0"));
		erfolgreich = true;
		/*
		con = getInstance();
		if (con != null) {
			Statement query;
			try {
				query = con.createStatement();
				// Usertabelle durchforsten
				String sql = "SELECT USER_NAME "
						+ "FROM USER " 
						+ "WHERE USER_NAME = '" + userid + "' "
						+ "AND PW = '" + pw + "' " ;
				ResultSet result = query.executeQuery(sql);
				// Ergebnisstabelle durchforsten
				while (result.next()) {
					String r_userid = result.getString("USER_NAME");
					if (r_userid.equals(userid)){
						erfolgreich = true;
					}
				}
			} catch (SQLException e) {
				erfolgreich = false;
				e.printStackTrace();
			}
		}
		*/
		return erfolgreich;
	}

	

}
