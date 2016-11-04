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

  <ul>
  
    <li><a href="index.jsp">Index</a></li>
  </ul>
  
  <form action = "search.jsp" method="get" style="float:right">
		<input type="search" name="q">
  </form>
  <br><br><br>


  <h1>Welcome to MovieChooser!</h1>
  
  

<% 


	JsonReader reader;
	
	try {
		URL url = new URL("http://localhost:8080/movie_chooser/linker.jsp");
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		
		InputStream is = connection.getInputStream();
		reader = new JsonReader(new InputStreamReader(is));
	
		try {
			reader.beginObject();
			reader.nextName();
			reader.beginArray();
			out.println(reader.nextString());
			reader.endArray();
			reader.endObject();
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
