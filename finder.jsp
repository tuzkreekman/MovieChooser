<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="bar" tagdir="/WEB-INF/tags" %>
<html>
  <head>
    
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <title>MovieHunter</title>
    
	
  </head>
  
  
  <body style="background-color:#E6E6FA">

  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ page import="java.util.*"%>
  <%@ page import="java.io.*" %>
  <%@ page import="java.sql.*"%>
  <%@ page import="java.net.*"%>
  <%@ page import="com.google.gson.*"%>
  <%@ page import="com.google.gson.stream.*"%>
  <%@ page import="uci.zainabk.database.*"%>
  <%@ page import="uci.zainabk.imdb.*"%>
  

  <%
  int id = Login.getUserID();
  Database db = new Database();
  UserDatabase udb = new UserDatabase(db.getConnection());
  
  String thing;
  if (id!=-1) {
	  thing = (udb.getUser(Integer.toString(id),true).name);
  } else thing = ("Login");
  %>
  <bar:horizontal_bar loggedin="<%=thing%>"/>
  <link rel="stylesheet" href="styles.css">

  <form method="post">
		<input name="funcID" type="hidden" value="1">
		Choose your movie genre: <input name="genre" type="text" required><br>
		Choose your age rating:  <select name="age">
			<option value="a">G</option>
			<option value="b">PG</option>
			<option value="c">PG-13</option>
			<option value="d">R</option>
		    </select><br>
		Choose a movie seed:     <input name="seed" type="text"><br>
		<input type="submit" name="button" value="Explore"><br>
		</form>
  
  <%
  %>


  
 
  </body>
</html>
