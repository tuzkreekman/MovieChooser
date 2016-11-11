<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="bar" tagdir="/WEB-INF/tags" %>
<html>
  <head>
    <%@ page import="uci.zainabk.database.*"%>
	<%@ page import="uci.zainabk.imdb.*"%>
	<%
  int id = Login.getUserID();
  String thing;
  if (id==-1) {
	  out.println("<meta http-equiv=\"refresh\" content=\"0; URL='login.jsp'\" />");
  } 
  %>
	
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <title>MovieHunter</title>
    
	
  </head>
  <link rel="stylesheet" href="styles.css">
  <body style="background-color:#E6E6FA">

  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  
  

  <%
  Database db = new Database();
  UserDatabase udb = new UserDatabase(db.getConnection());
  if (id!=-1) thing = (udb.getUser(Integer.toString(id),true).name);
  else thing="Login";
  %>
  <bar:horizontal_bar loggedin="<%=thing%>"/>
  
  


  <h1>Welcome, <%=thing%>!</h1>
  Please wait while we favorite this movie...
  <%
  String mID = request.getParameter("q");
  if (mID==null) out.println("<meta http-equiv=\"refresh\" content=\"0; URL='index.jsp'\" />");

  int mid = Integer.parseInt(mID);
  FavDatabase fdb = new FavDatabase(db.getConnection());
  MovieDatabase mdb = new MovieDatabase(db.getConnection());
  Movie m = mdb.getMovie(mid,null);
  fdb.addFav(id,mid);
  out.println("<meta http-equiv=\"refresh\" content=\"0; URL='search.jsp?q="+IMDB.getTitle(m.imdb)+"'\" />");
  %>
  
  
  </body>
</html>
