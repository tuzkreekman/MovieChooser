package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;
import org.jsoup.safety.Whitelist;
import org.jsoup.*;
import org.jsoup.nodes.*;

public class DBTropeLoader {

	public static ArrayList<String> getFilmTropes(String urlStr) {
		ArrayList<String> bag = new ArrayList<String>();
		try{
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			Whitelist whitelist = new Whitelist();
			whitelist.addTags("table");
			whitelist.addTags("tr");
			
			String html = br.readLine();
			String clean;
			//String clean = Jsoup.clean(html,whitelist);
			
			while (html!=null) {
				clean = Jsoup.clean(html,whitelist);
				Document doc = Jsoup.parse(clean);
				Element table = doc.select("table").first();
				if (table!=null) {
					System.out.println("found table");
					System.out.println(clean);
				}
				//System.out.println(clean);
				html = br.readLine();
			}
			
			
			//Document doc = Jsoup.parse
			/*Element link = doc.select("a").first();

			String text = doc.body().text(); // "An example link"
			String linkHref = link.attr("href"); // "http://example.com/"
			String linkText = link.text(); // "example""

			String linkOuterH = link.outerHtml(); 
				// "<a href="http://example.com"><b>example</b></a>"
			String linkInnerH = link.html(); // "<b>example</b>"
			System.out.println(html);*/
			
		} catch (Exception e) {
			System.out.println(e.getClass().toString()+e.getMessage());
		} finally {;}
		return bag;
	}
  
}