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

  </style>
  <body style="background-color:#E6E6FA">

  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ page import="java.util.*"%>
  <%@ page import="java.io.*" %>
  <%@ page import="java.sql.*"%>
  <%@ page import="java.net.*"%>
  <%@ page import="com.google.gson.*"%>
  <%@ page import="com.google.gson.stream.*"%>

  <ul>
  
    <li><a href="index.jsp">Index</a></li>
  </ul>


  <h1>Welcome to MovieChooser!</h1>
  
  

<% 


	//Gson gson = new GsonBuilder().create();
	//gson.fromJson()
	JsonReader reader;
	
	try {
		URL url = new URL("http://www.canistream.it/services/search?movieName=spongebob");
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
						reader.nextName();
						reader.nextString();
					}
					reader.endObject();
				} else if (name.equals("rating")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextDouble()+"</p>");
				} else if (name.equals("actors")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else if (name.equals("year")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextInt()+"</p>");
				} else if (name.equals("description")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else if (name.equals("title")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else if (name.equals("_id")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else if (name.equals("image")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else if (name.equals("image_last_updated")) {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				} else {
					out.println("<h2>"+name+"</h2>");
					out.println("<p>"+reader.nextString()+"</p>");
				}
				/*if (name.equals("id")) {
					id = reader.nextLong();
				} else if (name.equals("text")) {
					text = reader.nextString();
				} else if (name.equals("geo") && reader.peek() != JsonToken.NULL) {
					geo = readDoublesArray(reader);
				} else if (name.equals("user")) {
					user = readUser(reader);
				} else {
					reader.skipValue();
				}*/
			}
			reader.endObject();
			//reader.beginArray();
		} finally {
			reader.close();
		}
    } catch (Exception e) {
		out.println(e.getMessage());
	} finally {
	   out.println("did something");
    }



%>


  
 
  </body>
</html>
