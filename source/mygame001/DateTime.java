package allgemein;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateTime {
	
	
	public static String getAktTS()
    {
		StringBuffer buffer = new StringBuffer();
		//DB2 Timestamp
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss.SSSSSS");
   		Timestamp ts2 = new Timestamp(System.currentTimeMillis());
   		String TS = sdf2.format(ts2);
   		return TS;
    }
	
	public static String getAktDateTime()
    {
		//Datum Uhrzeit Deutsch
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String DT = sdf.format(ts);
   		return DT;
    } 
	
	public static String getAktDate()
    {
		//Datum Deutsch
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String date = sdf.format(ts);
   		return date;
    }
	
	public static String getAktTime()
    {
		//Uhrzeit Deutsch
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String time = sdf.format(ts);
   		return time;
    }
	
	public static String getDateTime(String ts, String language)   {
	
		String datetime = new String();
		if ((language.equals("de")) && (ts.length() >15)) {
		//DatumUhrzeit Deutsch
			datetime = 	ts.substring(8,10) +
						"." +
						ts.substring(5,7) + 
						"." +
						ts.substring(0,4) + 
						" " +
						ts.substring(11,13) + 
						":" +
						ts.substring(14,16);
		}
		//DatumUhrzeit Englisch
		if ((language.equals("en")) && (ts.length() >15)) {
			//DatumUhrzeit Deutsch
				datetime = 	ts.substring(0,4) + 
							"/" +
							ts.substring(5,7) + 
							"/" +
							ts.substring(8,10) +
							" " +
							ts.substring(11,13) + 
							":" +
							ts.substring(14,16);
			}
		
   		return datetime;
    }
	

}
