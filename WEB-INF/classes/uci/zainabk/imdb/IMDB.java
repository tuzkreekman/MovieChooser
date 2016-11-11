package uci.zainabk.imdb;
import java.net.*;
import com.google.gson.*;
import com.google.gson.stream.*;
import java.io.*;

public class IMDB {
	public static String getID(String searchStr) {
		JsonReader reader;
		String inputStr = "", imdb="";
		
		try{
			inputStr = "http://www.omdbapi.com/?t="+URLEncoder.encode(searchStr,"utf-8");
			System.out.println("<p>"+inputStr+"</p>");
		} catch (Exception e) {
			System.out.println(e.getClass().toString()+e.getMessage());
		}
		
		boolean found = true;
		
		try {
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			
			InputStream is = connection.getInputStream();
			reader = new JsonReader(new InputStreamReader(is));
			
			reader.beginObject();
		
			try {
				while (reader.hasNext()) {
					String name = reader.nextName(); 
					if (name.equals("Response")) {
						if (reader.nextString().equals("False")) {
							found = false;
							break;
						}
					} else if (name.equals("imdbID")) {
						imdb = reader.nextString();
					} else {
						reader.nextString();
					}
				}
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			System.out.println("IMDB failed: "+e.getClass().toString()+e.getMessage());
		} finally {
		   ;
		}
		
		if (!found) {
			try {
				inputStr = "http://www.omdbapi.com/?s="+URLEncoder.encode(searchStr,"utf-8");
				System.out.println("<p>"+inputStr+"</p>");
			
			
				URL url = new URL(inputStr);
				URLConnection connection = url.openConnection();
				connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
				
				InputStream is = connection.getInputStream();
				reader = new JsonReader(new InputStreamReader(is));
				
				reader.beginObject();
			
				try {
					while (reader.hasNext()) {
						String name = reader.nextName(); 
						if (name.equals("Response")) {
							if (reader.nextString().equals("False")) {
								found = false;
								break;
							}
						} else if (name.equals("Search")) {
							reader.beginArray();
							reader.beginObject();
							while (reader.hasNext()) {
								name = reader.nextName(); 
								if (name.equals("imdbID")) {
									imdb = reader.nextString();
								} else {
									reader.nextString();
								}
							}
							reader.endObject();
							break;
						} else {
							reader.nextString();
						}
					}
				} finally {
					reader.close();
				}
			} catch (Exception e) {
				System.out.println("IMDB2 failed: "+e.getClass().toString()+e.getMessage());
			} finally {
			   ;
			}
		}
		
		return imdb;
	
	}
	
	public static String getTitle(String searchStr) {
		JsonReader reader;
		String inputStr = "", imdb="";
		
		try{
			inputStr = "http://www.omdbapi.com/?i="+URLEncoder.encode(searchStr,"utf-8");
			System.out.println("<p>"+inputStr+"</p>");
		} catch (Exception e) {
			System.out.println(e.getClass().toString()+e.getMessage());
		}
		
		boolean found = true;
		
		try {
			URL url = new URL(inputStr);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			
			InputStream is = connection.getInputStream();
			reader = new JsonReader(new InputStreamReader(is));
			
			reader.beginObject();
		
			try {
				while (reader.hasNext()) {
					String name = reader.nextName(); 
					if (name.equals("Response")) {
						if (reader.nextString().equals("False")) {
							found = false;
							break;
						}
					} else if (name.equals("Title")) {
						return reader.nextString();
					} else {
						reader.nextString();
					}
				}
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			System.out.println("IMDB failed: "+e.getClass().toString()+e.getMessage());
		} finally {
		   ;
		}
		return null;
	
	}
	
}