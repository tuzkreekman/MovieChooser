package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;
import org.jsoup.safety.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class DBTropeLoader {

	public static ArrayList<String> getFilmTropes(String urlStr) {
		ArrayList<String> bag = new ArrayList<String>();
		try{
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			String baseUri = "dbtropes.org";
			InputStream is = connection.getInputStream();
			Document doc = Jsoup.parse(is,null,baseUri);
			
			Whitelist whitelist = new Whitelist();
			whitelist.addTags("table");
			whitelist.addTags("tr");
			whitelist.addTags("td");
			Cleaner cleaner = new Cleaner(whitelist);
			
			doc = cleaner.clean(doc);
			
			Element table = doc.select("table").first();
			Iterator<Element> rows = table.select("tr").iterator();
			
			for (Element row : table.select("tr")) {
				Elements objs = row.select("td");
				Element obj = objs.first();
				obj = obj.nextElementSibling();
				obj = obj.nextElementSibling();
				if (obj.ownText().equals("type")) 
					System.out.println(obj.lastElementSibling().ownText());
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getClass().toString()+e.getMessage());
		} finally {;}
		return bag;
	}
  
}