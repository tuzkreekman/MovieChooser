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
  <%@ page import="uci.zainabk.database.*"%>
  <%@ page import="uci.zainabk.imdb.*"%>
  <%@ page import="uci.zainabk.movies.*"%>
  <%@ page import="uci.zainabk.tvtropes.*"%>  

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

  
  <%
  	String searchStr = request.getParameter("q");
	if (searchStr==null) out.println("<meta http-equiv=\"refresh\" content=\"0; URL='index.jsp'\" />");
	else {
		System.out.println("Search str: "+searchStr);
		MovieInfo mi = new MovieInfo(searchStr,db,true);
		String iMDB = mi.getIMDBID();
		if (iMDB==null) {
			out.println("<h1>Movie not found</h1>");
		} else {
			Movie m = mi.getMovie();
			if (!mi.foundTropes()) out.println("<p>Tropes failed</p>");
			out.println("<h2>Tropes</h2><ul>");
			for (String s: mi.getTags()) out.println("<li>"+s+"</li>");
			out.println("</ul>");
		}
	}
  %>
  

 
  </body>
</html>
