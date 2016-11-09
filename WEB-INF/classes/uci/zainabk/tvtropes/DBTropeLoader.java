package uci.zainabk.tvtropes;

import java.util.*;
import java.io.*;
import java.net.*;
//import com.google.gson.stream.JSONWriter;
import javax.servlet.jsp.JspWriter;

public class DBTropeLoader {
	
	
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
					if ((c=is.read())==(int)'t') {
						if ((c=is.read()) ==(int)'a') if ((c=is.read()) ==(int)'b')
							if ((c=is.read()) ==(int)'l') if ((c=is.read()) ==(int)'e'){
							is.read();
							break;
						}
					}
				}
			}
			while ((c = is.read()) != -1) {
				if ((c=is.read())==(int)'t') {
					if ((c=is.read()) ==(int)'a') if ((c=is.read()) ==(int)'b')
						if ((c=is.read()) ==(int)'l') if ((c=is.read()) ==(int)'e'){
							is.read();
							break;
						}
				}
			}
			if (c==-1) 
				return null;
			return is;
		} finally {
			;//System.out.println("Done cutting");		
		}
   
	}
	
	public static void spitOutTropes(InputStream is,JspWriter out) throws IOException {
		try {
			int c;
			String bag = "\"";
			while ((c = is.read()) != -1) {
				
				if (c==(int)'<') {
					if ((c=is.read())=='!') {
						if ((c=is.read())=='-')
							if ((c=is.read())=='-')
								c = waitForCommentEnd(is);
					} else if (c=='d') if ((c=is.read())=='i') if ((c=is.read())=='v') {
							if ((c=is.read())==' ') if ((c=is.read())=='c') if ((c=is.read())=='l') if ((c=is.read())=='a')
								if ((c=is.read())=='s') if ((c=is.read())=='s') if ((c=is.read())=='=')  
									c = waitForDivEnd(is);
					} else if (c=='/') if ((c=is.read())=='l') if ((c=is.read())=='i') if ((c=is.read())=='>') {
						c = ' ';
						bag = bag + "\"";
						out.print(bag);
						//System.out.print(bag);
						bag = ", \"";
					}
						
					while ((c != (int)'>') && c!=-1) {
						if ((c=lookForEnding(is,c))==-2) break;
					}
					if (c==(int)'>') c = ' ';
				} else if (c==(int)':') {
					while (c!=-1) {
						if (finishTrope(is)) break;
					}
					c = ' ';
					bag = bag + "\"";
					out.print(bag);
					//System.out.print(bag);
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

	public static int waitForCommentEnd(InputStream is) throws IOException {
		int c = 0;
		//System.out.println("entered comment waiting");
		while (c!=-1) {
			if ((c=is.read()) ==(int)'-') {
				if ((c=is.read()) ==(int)'-') {
					if ((c=is.read()) ==(int)'>')
						break;
				}
			} //System.out.print(Character.toString((char)c));
		} return c;
	}
	
	public static int waitForDivEnd(InputStream is) throws IOException {
		int c = 0;
		//System.out.println("entered comment waiting");
		while (c!=-1) {
			if ((c=is.read()) ==(int)'/') {
				if ((c=is.read()) ==(int)'d')  if ((c=is.read())=='i') if ((c=is.read())=='v') {
					if ((c=is.read()) ==(int)'>')
						break;
				}
			} //System.out.print(Character.toString((char)c));
		} return c;
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
	
	public static int lookForEnding(InputStream is, int c) throws IOException {
		//System.out.println("Ending hunt"+Character.toString((char)c));
		if ((c) ==(int)'h') {
			if ((c=is.read()) ==(int)'r') 
				if ((c=is.read()) ==(int)' ') 
					if ((c=is.read()) ==(int)'/')
						if ((c=is.read()) ==(int)'>')
							c = -2;
		} else c=is.read();
		return c;
	}
	
	public static boolean finishTrope(InputStream is) throws IOException {
		int c;
		//System.out.println("finish trope hunt");
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