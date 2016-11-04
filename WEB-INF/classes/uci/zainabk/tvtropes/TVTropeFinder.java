package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;

public class TVTropeFinder {
	
	public static String findTropeURL(String searchStr) throws IOException {
		InputStream is = null;
		String inputStr = "http://www.google.com/search?q=trope+"+URLEncoder.encode(searchStr,"utf-8");
		String sURL = "";
		System.out.println(inputStr);
		
		try {
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			is = connection.getInputStream();
			 
			int c = 0;
			matchString(is,"tvtropes.org");
			sURL = getUntilEnd(is);
		} finally {return "http://tvtropes.org"+sURL;}
	}
	
	public static void matchString(InputStream is,String goal) throws IOException {
		int i =0,c =0;
		while (c!=-1) {
			while (i<goal.length() && (c=is.read()) == (int)goal.charAt(i)) {
				i++;
			}
			if (i!=goal.length()) i=0;
			else break;
		}
	}
	
	public static String getUntilEnd(InputStream is) throws IOException {
		int c =0;
		String bag = "";
		while ((c=is.read())!=(int)'&' && c!=-1) {
			bag = bag + Character.toString((char)c);
		} return bag;
	}
	
  
}