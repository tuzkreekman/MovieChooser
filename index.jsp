<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <title>MovieHunter</title>
    
	
  </head>
  <link rel="stylesheet" href="styles.css">
  <body style="background-color:#E6E6FA">

  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  
  <%@ page import="uci.zainabk.database.*"%>

  <ul>
  
    <li><a href="index.jsp">Index</a></li>
  <li style="float:right">
  
  <form action = "search.jsp" method="get" style="float:right">
		<input type="search" name="q">
  </form></li></ul>
  <br><br><br>


  <h1>Welcome to MovieChooser!</h1>
  <%
  int id = Login.getUserID();
  if (id!=-1) {
	  out.print("<p>");
	  out.print((new UserDatabase((new Database()).getConnection())).getUser(Integer.toString(id),true).name);
	  out.println("</p>");
  } 
  %>
  
  </body>
</html>
