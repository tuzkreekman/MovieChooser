<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <title>MovieHunter</title>
    
	
  </head>
  <link rel="stylesheet" href="styles.css">
  
  <body style="background-color:#E6E6FA">

  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ page import="java.util.*"%>
  <%@ page import="java.io.*" %>
  <%@ page import="java.sql.*"%>
  <%@ page import="java.net.*"%>
  <%@ page import="com.google.gson.*"%>
  <%@ page import="com.google.gson.stream.*"%>
  <%@ page import="com.net.codeusa.*"%>
  <%@ page import="edu.uci.eecs.zainabk.*"%>
  

  <ul>
    <li name="something"><a href="index.jsp">Index</a></li>
  </ul>

  <form method="get" style="float:right">
		<input type="search" name="q">
  </form>
  <br><br><br>
	

  

<% 
	//Hello h = new Hello();
	JsonReader reader;
	String searchStr = request.getParameter("q");
	String inputStr = "";
	
	String img = "",title = "";
	int year = 0;
	//inputStr = "http://www.omdbapi.com/?i="+URLEncoder.encode(imdb);
	inputStr = "http://www.omdbapi.com/?t="+URLEncoder.encode(searchStr);
	out.println("<p>"+inputStr+"</p>");
	
	String r3 = "";
	int r2 = 0; 
	double r1 = 0;
	ArrayList<String> tag = new ArrayList<String>();
	boolean netflix = true;
	boolean found = true;
	boolean tropes = true;
	
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
				} else if (name.equals("Poster")) {
					String pic = reader.nextString();
					if (img.equals("")) img = pic;
				} else if (name.equals("Title")) {
					String t = reader.nextString();
					if (title.equals("")) title = t;
				} else if (name.equals("Metascore")) {
					String meta = reader.nextString();
					if (!meta.contains("N"))
						r2 = Integer.parseInt(meta);
				} else if (name.equals("imdbRating")) {
					r1 = reader.nextDouble();
				} else if (name.equals("Rated")) {
					r3 = reader.nextString();
				} else if (true==false) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else {
					reader.nextString();
				}
			}
			//reader.endObject();
		} finally {
			reader.close();
		}
    } catch (Exception e) {
		out.println("<p>IMDB failed: "+e.getMessage()+"</p>");
	} finally {
	   ;
    }
	
	if (!found) {
		inputStr = "http://www.omdbapi.com/?s="+URLEncoder.encode(searchStr);
		out.println("<p>"+inputStr+"</p>");
		String imdb = "";
		
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
			out.println("<p>IMDB2 failed: "+e.getMessage()+"</p>");
		} finally {
		   ;
		}
		
		if (!imdb.equals("")) {
			inputStr = "http://www.omdbapi.com/?i="+URLEncoder.encode(imdb);
			out.println("<p>"+inputStr+"</p>");
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
						} else if (name.equals("Poster")) {
							String pic = reader.nextString();
							if (img.equals("")&&!pic.equals("N/A")) img = pic;
						} else if (name.equals("Title")) {
							String t = reader.nextString();
							if (title.equals("")) title = t;
						} else if (name.equals("Metascore")) {
							String meta = reader.nextString();
							if (!meta.contains("N"))
								r2 = Integer.parseInt(meta);
						} else if (name.equals("imdbRating")) {
							r1 = reader.nextDouble();
						} else if (name.equals("Rated")) {
							r3 = reader.nextString();
						} else if (true==false) {
							out.println("<h2>"+name+"</h2>");
							out.println("<p>"+reader.nextString()+"</p>");
						} else {
							reader.nextString();
						}
					}
					reader.endObject();
				} finally {
					reader.close();
				}
			} catch (Exception e) {
				out.println("<p>IMDB3 failed: "+e.getMessage()+"</p>");
			} finally {
			   ;
			}
		}
	}
	
	inputStr = "http://netflixroulette.net/api/api.php?title="+URLEncoder.encode(title);
	out.println("<p>"+inputStr+"</p>");
	
	try {
		int a = 0/0;
		URL url = new URL(inputStr);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		
		InputStream is = connection.getInputStream();
		reader = new JsonReader(new InputStreamReader(is));
		
		reader.beginObject();
	
		try {
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("errorcode")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else if (name.equals("message")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else if (name.equals("poster")) {
					String pic = reader.nextString();
					if (img.equals("")) img = pic;
				} else if (name.equals("category")) {
					String category = reader.nextString();
					if (!category.equals("N/A")) tag.add(category);
				} else if (true==false) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else reader.nextString();
			}
			reader.endObject();
		} catch (Exception e) {
			netflix = false;
		} finally {
			reader.close();
		}
    } catch (Exception e) {
		netflix = false;
	} finally {
	   ;
    }
	
	
	inputStr = "http://localhost:8080/movie_chooser/linker.jsp?q="+URLEncoder.encode(title);
	out.println("<p>"+inputStr+"</p>");
	
	try {
		URL url = new URL(inputStr);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		
		InputStream is = connection.getInputStream();
		reader = new JsonReader(new InputStreamReader(is));
		
		reader.beginObject();
		reader.nextName();
		reader.beginArray();
		while (reader.hasNext()) tag.add(reader.nextString());
		reader.endArray();
		reader.endObject();
		reader.close();
    } catch (Exception e) {
		out.print(e.getMessage());
		tropes = false;
	} finally {
	   ;
    }
	
	
	if (!title.equals("")) out.println("<h1>"+title+"</h1>");
	if (!img.equals("")) out.println("<img src=\""+img+"\" alt=\""+img+"\">");
	
	out.println("<h2>General Info</h2>");
	out.println("<p>Rating "+r3+"</p>");
	
	out.println("<h2>Reviews</h2>");
	if (r2!=0) out.println("<p>Metacritic "+Integer.toString(r2)+"</p>");
	else out.println("<p>Metacritic unavailable</p>");
	out.println("<p>IMDB "+Double.toString(r1)+"</p>");
	
	out.println("<h2>Where to Watch</h2>");
	out.println("<p>Netflix: "+((netflix)? "available":"unavailable")+"</p>");
	
	if (!tropes) out.println("<p>Tropes failed</p>");
	
	out.println("<h2>Tags</h2><ul>");
	for (Object s: tag.toArray()) out.println("<li>"+s.toString()+"</li>");
	out.println("</ul>");

%>


  
 
  </body>
</html>
