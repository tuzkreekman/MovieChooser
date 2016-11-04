package uci.zainabk.html;

import java.io.*;

public class HTMLStandard {
	public static void opener(String title, 
							javax.servlet.jsp.JspWriter out) throws IOException { 
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"favicon.ico\" />");
		out.print("<title>");
		out.print(title);
		out.println("</title>");
		out.println("</head>");
	}
	
	public static void blah(javax.servlet.jsp.JspWriter out) throws IOException {
		out.println("aha");
	}
	
	public static void startBody(javax.servlet.jsp.JspWriter out) 
	throws IOException { 
	out.println("<body>"); }
	
	public static void startColoredBody(String colorHash,
	javax.servlet.jsp.JspWriter out) throws IOException { 
		out.print("<body background-color=");
		out.print(colorHash);
		out.println(">");
	}
	
	public static void endBody(javax.servlet.jsp.JspWriter out) 
	throws IOException { out.println("</body>"); }
	
	public static void ender(javax.servlet.jsp.JspWriter out) 
	throws IOException { out.println("</html>");}
	
}
