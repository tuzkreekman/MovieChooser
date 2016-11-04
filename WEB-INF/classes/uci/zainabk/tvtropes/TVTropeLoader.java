package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;
//import com.google.gson.stream.JSONWriter;
import javax.servlet.jsp.JspWriter;

public class TVTropeLoader {
	
	
	public static InputStream loadAndCutFront(String inputStr) throws IOException {
		InputStream is = null;
		
		try {
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
			is = connection.getInputStream();
			 
			int c;
			while ((c = is.read()) != -1) {
				if (c==(int)'/') {
					if ((c=is.read())==(int)'h') {
						if ((c=is.read()) ==(int)'2')  {
							is.read();
							break;
						}
					}
				}
			}
			while ((c = is.read()) != -1) {
				if (c==(int)'<') {
					if ((c=is.read())==(int)'u') {
						if ((c=is.read()) ==(int)'l') {
							if ((c=is.read()) ==(int)' ') {
								if ((c=is.read()) ==(int)'>')  {
									break;
								}
							}
						}
					}
				}
			}
			int a;
			if (c==-1) 
				return null;
			return is;
		} finally {
			;		
		}
   
	}
	
	public static void spitOutTropes(InputStream is,JspWriter out) throws IOException {
		try {
			int c;
			String bag = "\"";
			while ((c = is.read()) != -1) {
				
				if (c==(int)'<') {
					while ((c != (int)'>') && c!=-1) {
						if ((c=lookForEnding(is))==-2) break;
					}
					if (c==(int)'>') c = ' ';
				} else if (c==(int)':') {
					while (c!=-1) {
						if (finishTrope(is)) break;
					}
					c = ' ';
					bag = bag + "\"";
					out.print(bag);
					bag = ", \"";
				} 
				if (c==-2) break;
				else if (c==(int)'\"') c = '\'';
				bag = bag + Character.toString((char)c);
			}
		} finally {
			;		
		}
   
	}
	
	public static void waitForListEnd(InputStream is) throws IOException {
		int c = 0;
		while (c!=-1) {
			if ((c=is.read()) ==(int)'<') {
				if ((c=is.read()) ==(int)'u') {
					waitForListEnd(is);
				}
				else if ((c) ==(int)'/') {
					if ((c=is.read()) ==(int)'u')
						if ((c=is.read()) ==(int)'l')
							if ((c=is.read()) ==(int)'>') break;
				}
			}
		}
	}
	
	public static int lookForEnding(InputStream is) throws IOException {
		int c;
		if ((c=is.read()) ==(int)'h') 
			if ((c=is.read()) ==(int)'r') 
				if ((c=is.read()) ==(int)' ') 
					if ((c=is.read()) ==(int)'/')
						if ((c=is.read()) ==(int)'>')
							c = -2;
		return c;
	}
	
	public static boolean finishTrope(InputStream is) throws IOException {
		int c;
		if ((c=is.read()) ==(int)'<')  {
			if ((c=is.read()) ==(int)'/') {
				if ((c=is.read()) == (int)'l')
					if ((c=is.read()) == (int)'i')
						if ((c=is.read()) == (int)'>')
							return true;
			} else if (c==(int)'u') {
				waitForListEnd(is);
			}
		}
		return false;
	}
	
  
}