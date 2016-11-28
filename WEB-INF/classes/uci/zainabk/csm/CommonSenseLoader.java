package uci.zainabk.csm;

import java.util.*;
import java.io.*;
import java.net.*;
import org.jsoup.safety.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class CommonSenseLoader {
	public static int getAgeRating(String urlStr) {
		try{
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			String baseUri = "www.commonsensemedia.org";
			InputStream is = connection.getInputStream();
			Document doc = Jsoup.parse(is,null,baseUri);
			
			Whitelist whitelist = new Whitelist();
			whitelist.addTags("div");
			whitelist.addAttributes("div","class");
			Cleaner cleaner = new Cleaner(whitelist);
			
			doc = cleaner.clean(doc);
			
			Element age = doc.select("div[class=\"csm-green-age\"]").first();
			String rating = age.text();
			return Integer.parseInt(rating.split(" |\\+")[1]);
			
			
		} catch (Exception e) {
			System.out.println(e.getClass().toString()+e.getMessage());
			return -1;
		} 
	}
  
}