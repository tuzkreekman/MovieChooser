package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;
import org.jsoup.safety.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class DBTropeLoader {

	public static ArrayList<Trope> getFilmTropes(String urlStr) {
		ArrayList<Trope> bag = new ArrayList<Trope>();
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
			whitelist.addTags("a");
			whitelist.addAttributes("a","href");
			Cleaner cleaner = new Cleaner(whitelist);
			
			doc = cleaner.clean(doc);
			
			Element table = doc.select("table").first();
			Elements rows = table.select("tr");
			
			for (Element row : table.select("tr")) {
				Elements objs = row.select("td");
				Element obj = objs.first();
				obj = obj.nextElementSibling();
				obj = obj.nextElementSibling();
				if (obj.ownText().equals("type")) {
					Element link = obj.lastElementSibling().select("a").first();
					String ur = link.attr("href");
					if (ur.equals("http://dbtropes.org/ont/TVTItem")) continue;
					else if (ur.equals("http://skipforward.net/skipforward/resource/seeder/skipinions/ItemName")) continue;
					ur = convertDBURI(ur);
					if (ur!=null) {
						bag.add(new Trope(ur));
						//bag.add(new Trope(ur,ur));
					}
				}
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getClass().toString()+e.getMessage());
		} finally {;}
		return bag;
	}
	
	public static String convertDBURI(String str) {
		String ignore = "http://dbtropes.org/resource/";
		String beginning = "http://tvtropes.org/pmwiki/pmwiki.php/";
		return beginning + str.substring(ignore.length());
	}
	
	public static String convertURIDB(String str) {
		String beginning = "http://dbtropes.org/resource/";
		String ignore = "http://tvtropes.org/pmwiki/pmwiki.php/";
		return beginning + str.substring(ignore.length());
	}
  
}