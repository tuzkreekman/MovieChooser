import java.util.*;
import java.io.*;
import com.google.gson.stream.*";

public class TVTropeLoader {
	public static void loadMovie() {
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
				boolean saved = false;
				String saving = "";
				if (c==(int)'h') {
					saved = true;
					saving = saving + Character.toString((char)c);
					if ((c=is.read()) ==(int)'r') { 
						saving = saving + Character.toString((char)c);
						if ((c=is.read()) ==(int)' ') {
							saving = saving + Character.toString((char)c);
							if ((c=is.read()) == (int)'/')
								break;
							saving = saving + Character.toString((char)c);
						}
						else saving = saving + Character.toString((char)c);
					} else {
						saving = saving + Character.toString((char)c);
					}
				}
				if (saved) out.print(saving);
				else out.print(Character.toString((char)c));
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
   
	}
  
}