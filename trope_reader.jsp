<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">---->
<html>
  <head>
    
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <title>MovieHunter</title>
    
	
  </head>
  <body>

  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ page import="java.util.*"%>
  <%@ page import="java.io.*" %>
  <%@ page import="java.sql.*"%>
  <%@ page import="java.net.*"%>
  <%@ page import="com.google.gson.*"%>
  <%@ page import="com.google.gson.stream.*"%>
  <%@ page import="com.net.codeusa.*"%>
  <%@ page import="edu.uci.eecs.zainabk.*"%>
	
  
<%  

	InputStream is = null;
	String inputStr = "http://tvtropes.org/pmwiki/pmwiki.php/Film/CarryOnSpying";
	
	try {
		URL url = new URL(inputStr);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			
		is = connection.getInputStream();
         
        int c;
        while ((c = is.read()) != -1) {
			if (c==(int)'h') {
				if ((c=is.read()) ==(int)'2') break;
			}
            //out.println(Character.toString((char)c));
        }
		int a;
		if (c==-1) a = 0/0;
		while ((c = is.read()) != -1) {
				if (c==(int)'<') {
					while ((c != (int)'>') && c!=-1) {
						if ((c=is.read()) ==(int)'h') 
							if ((c=is.read()) ==(int)'r') 
								if ((c=is.read()) ==(int)' ') 
									if ((c=is.read()) ==(int)'/')
										if ((c=is.read()) ==(int)'>') {
											c = -2;
											break;
										}
					}
					if (c==(int)'>') c = ' ';
				} else if (c==(int)':') {
					while (c!=-1) {
						if ((c=is.read()) ==(int)'<')  {
							if ((c=is.read()) ==(int)'/') {
								if ((c=is.read()) == (int)'l')
									if ((c=is.read()) == (int)'i')
										if ((c=is.read()) == (int)'>')
											break;
							} else if (c==(int)'u') {
								out.println("<p>list loop</p>");
								while (c!=-1) {
									if ((c=is.read()) ==(int)'<') 
										if ((c=is.read()) ==(int)'/') 
											if ((c=is.read()) == (int)'u')
												if ((c=is.read()) == (int)'l')
													if ((c=is.read()) == (int)'>')
														break;
								}
							}
						}
					}
					c = ' ';
					out.println("<br>");
				} 
				if (c==-2) break;
				out.print(Character.toString((char)c));
        }
	} finally {
        if (is != null) {
            is.close();
        }
    }
   

  
%>
  </body>
</html>