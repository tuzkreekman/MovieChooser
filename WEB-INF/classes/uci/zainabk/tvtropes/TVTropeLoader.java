package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;
import org.jsoup.safety.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class TVTropeLoader {
	public static ArrayList<String> getTropes(String urlStr) {
		ArrayList<String> bag = new ArrayList<String>();
		try{
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			String baseUri = "tvtropes.org";
			InputStream is = connection.getInputStream();
			Document doc = Jsoup.parse(is,null,baseUri);
			
			Whitelist whitelist = new Whitelist();
			whitelist.addTags("ul");
			whitelist.addTags("li");
			whitelist.addTags("h1");
			whitelist.addTags("h2");
			whitelist.addTags("hr");
			whitelist.addTags("title");
			whitelist.addTags("a");
			Cleaner cleaner = new Cleaner(whitelist);
			
			doc = cleaner.clean(doc);
			
			String html = doc.html();
			int start = html.indexOf("<hr>");
			html = html.substring(start);
			int ending = html.lastIndexOf("<hr>");
			html = html.substring(0,ending);
			
			doc = Jsoup.parse(html);
			
			for (Element e : doc.select("body >ul > li")) {
				bag.add(e.select("a").first().ownText());
			}		
			
		} catch (Exception e) {
				System.out.println(e.getClass().toString()+e.getMessage());
		} finally {return bag;}
	}
}