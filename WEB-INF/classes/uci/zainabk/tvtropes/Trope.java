package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;
import org.jsoup.safety.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Trope {
	public final String URL;
	public final String title;
	
	public Trope(String urlStr) {
		URL = urlStr;
		String title = "";
		
		try{
			URL url = new URL(urlStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String html;
			Document doc;
			while ((html=br.readLine())!=null) {
				doc = Jsoup.parse(html);
				Element t = doc.select("title").first();
				if (t==null) continue;
				title = t.text();
				title = title.substring(0,title.length()-11);
				break;
			}
			br.close();
			
		} catch (Exception e) {;}
		if (title.equals("")) {
			String[] parts = urlStr.split("/");
			title = (parts[parts.length-1]);
		}
		this.title = title;
	}
	
	public Trope(String url, String t) {
		title = t;
		URL = url;
	}
}