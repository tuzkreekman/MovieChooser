<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

  
  <%@ page import="java.util.*"%>
  <%@ page import="java.io.*"%>
  <%@ page import="java.net.*"%>
  <%@ page import="org.jsoup.*"%>
  <%@ page import="org.jsoup.nodes.*"%>
  <%@ page import="org.jsoup.select.*"%>
  <%@ page import="org.jsoup.safety.*"%>

<%	try{
			String urlStr = "http://tvtropes.org/pmwiki/pmwiki.php/Film/FantasticBeastsAndWhereToFindThem";
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
			//whitelist.addAttributes("div","class");
			Cleaner cleaner = new Cleaner(whitelist);
			
			doc = cleaner.clean(doc);
			
			String html = doc.html();
			int start = html.indexOf("<hr>");
			html = html.substring(start);
			int ending = html.lastIndexOf("<hr>");
			html = html.substring(0,ending);
			
			doc = Jsoup.parse(html);
			
			for (Element e : doc.select("body >ul > li")) {
				out.println("<p>"+e.select("a").first().ownText()+"</p>");
			}
			
			//out.print(doc.html());			
			
	} catch (Exception e) {
			System.out.println(e.getClass().toString()+e.getMessage());
	} finally {;}
	
  
%>