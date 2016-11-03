<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <title>MovieHunter</title>
    
	
  </head>
  <style>
  ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #333;
  }

  li {
    float: left;
  }

  li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-family: 'Corben', Georgia, Times, serif;
  }

  /* Change the link color to #111 (black) on hover */
  li a:hover {
    background-color: #111;
  }

  body {
    font-size: 100%;
    font-family: 'Corben', Georgia, Times, serif;
  }
  h1 {
    font-family: 'Corben', Georgia, Times, serif;
    font-size: 1.5em;
  }
  h2 {
    font-family: 'Corben', Georgia, Times, serif;
    font-size: 1.1em;
  }
  p {
    font-size: 1em;
    font-family: 'Corben', Georgia, Times, serif;
  }
  input[type=submit] {
    font-family: 'Corben', Georgia, Times, serif;
  }
  input[type=text] {
    font-family: 'Corben', Georgia, Times, serif;
  }
  input[type=select] {
    font-family: 'Corben', Georgia, Times, serif;
  }
  input[type=search] {
    width: 130px;
    box-sizing: border-box;
    border: 2px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    background-color: white;
    background-image: url('searchicon.gif');
	background-size: 30px 30px;
    background-position: 0px 0px;
    background-repeat: no-repeat;
    padding: 14px 0px 0px 30px;
    -webkit-transition: width 0.4s ease-in-out;
    transition: width 0.4s ease-in-out;
	font-family: 'Corben', Georgia, Times, serif;
  }

  input[type=search]:focus {
    width: 100%;
  }

  </style>
  <body style="background-color:#E6E6FA">

  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ page import="java.util.*"%>
  <%@ page import="java.io.*" %>
  <%@ page import="java.sql.*"%>
  <%@ page import="java.net.*"%>
  <%@ page import="com.google.gson.*"%>
  <%@ page import="com.google.gson.stream.*"%>
  <%@ page import="com.net.codeusa.*"%>
  

  <ul>
    <li><a href="index.jsp">Index</a></li>
  </ul>

  <form method="get" style="float:right">
		<input type="search" name="q">
  </form>
  <br><br><br>
	
  
  
  

<% 
	


	JsonReader reader;
	NetflixRoulette flxr = new NetflixRoulette();
	String searchStr = request.getParameter("q");
	String inputStr = "http://www.canistream.it/services/search?movieName="+URLEncoder.encode(searchStr,"UTF-8");
	out.println(inputStr);
	
	String imdb = "";
	
	String img = "",title = "";
	int year = 0;

	/*
	try {
		URL url = new URL(inputStr);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		
		InputStream is = connection.getInputStream();
		reader = new JsonReader(new InputStreamReader(is));
		
		reader.beginArray();
		
		
		
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("links")) {
					reader.beginObject();
					while (reader.hasNext()) {
						if (reader.nextName().equals("imdb")) 
							imdb = reader.nextString();
						else reader.nextString();
					}
					reader.endObject();
				} else if (name.equals("image")) {
					img = reader.nextString();

				} else if (name.equals("year")) {
					year = reader.nextInt();
				} else if (name.equals("title")) {
					title = reader.nextString();
				} else {
					reader.nextString();
				} 
				
				
			}
			reader.endObject();
			//reader.beginArray();
		} finally {
			reader.close();
		}
    } catch (Exception e) {
		out.println("<p>CISI Failed: "+e.getMessage()+"</p>");
	} finally {
	   //out.println("<p>CISI succeed</p>");
    }*/

	if (!imdb.equals("")) imdb = imdb.substring(imdb.indexOf("title")+6,imdb.indexOf("title")+15);
	out.println(imdb);
	//inputStr = "http://www.omdbapi.com/?i="+URLEncoder.encode(imdb);
	inputStr = "http://www.omdbapi.com/?t="+URLEncoder.encode(searchStr);
	out.println(inputStr);
	
	String r3 = "";
	int r2 = 0; 
	double r1 = 0;
	
	try {
		URL url = new URL(inputStr);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		
		InputStream is = connection.getInputStream();
		reader = new JsonReader(new InputStreamReader(is));
		
		reader.beginObject();
	
		try {
			//reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("Poster")) {
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
				} else if (name.equals("Rating")) {
					r3 = reader.nextString();
				} else if (true==false) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else {
					reader.nextString();
				}
			}
			reader.endObject();
			//reader.beginArray();
		} finally {
			reader.close();
		}
    } catch (Exception e) {
		out.println("<p>IMDB failed: "+e.getMessage()+"</p>");
	} finally {
	   //out.println("<p>IMDB succeed</p>");
    }
	/*
	inputStr = "https://api.commonsensemedia.org/api/v2/reviews/browse?api_key=__&channel=movie"+URLEncoder.encode(searchStr);
	out.println(inputStr);
	
	try {
		URL url = new URL(inputStr);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		
		InputStream is = connection.getInputStream();
		reader = new JsonReader(new InputStreamReader(is));
		
		reader.beginObject();
	
		try {
			//reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("Poster")) {
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
				} else if (name.equals("Rating")) {
					r3 = reader.nextString();
				} else if (true==false) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else {
					reader.nextString();
				}
			}
			reader.endObject();
			//reader.beginArray();
		} finally {
			reader.close();
		}
    } catch (Exception e) {
		out.println("<p>IMDB failed: "+e.getMessage()+"</p>");
	} finally {
	   //out.println("<p>IMDB succeed</p>");
    }
	*/
	
	//https://api.commonsensemedia.org/api/v2
	
	if (!title.equals("")) out.println("<h1>"+title+"</h1>");
	if (!img.equals("")) out.println("<img src=\""+img+"\" alt=\""+img+"\">");
	out.println("<p>Rating "+r3+"</p>");
	out.println("<p>Metacritic "+Integer.toString(r2)+"</p>");
	out.println("<p>IMDB "+Double.toString(r1)+"</p>");
	//if (year!=0) out.println("<p>"+year+"</p>");

%>


  
 
  </body>
</html>
