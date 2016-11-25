package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;


public class DBTropeLoader {

	public static ArrayList<String> getFilmTropes(String urlStr) {
		ArrayList<String> bag = new ArrayList<String>();
		try{
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String html = br.readLine();
			/*Document doc = Jsoup.parse(html);
			Element link = doc.select("a").first();

			String text = doc.body().text(); // "An example link"
			String linkHref = link.attr("href"); // "http://example.com/"
			String linkText = link.text(); // "example""

			String linkOuterH = link.outerHtml(); 
				// "<a href="http://example.com"><b>example</b></a>"
			String linkInnerH = link.html(); // "<b>example</b>"*/
			System.out.println(html);
		} catch (Exception e) {
			System.out.println(e.getClass().toString()+e.getMessage());
		} finally {;}
		return bag;
	}
  
}