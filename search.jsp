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
			FavDatabase fdb = new FavDatabase(db.getConnection());
			ArrayList<Fav> favs = fdb.getFavs(id);
			Fav myFav = new Fav(id,m.id,-1);
			boolean found = false;
			for (Fav f : fdb.getFavs(id)) {
				if (f.equals(myFav)) {
					found = true;
					myFav = f;
					break;
				}
			} if (!found) {
				out.println("<a href=\"hate.jsp?q="+Integer.toString(m.id)+"\">Hated</a>");
				out.println("<a href=\"watch.jsp?q="+Integer.toString(m.id)+"\">Watched</a>");
				out.println("<a href=\"like.jsp?q="+Integer.toString(m.id)+"\">Favorite</a>");
			} else {
				switch(myFav.getOpinion()) {
					case 0:
						out.println("<a href=\"unlike.jsp?q="+Integer.toString(m.id)+"\">Un-hate</a>");
						out.println("<a href=\"watch.jsp?q="+Integer.toString(m.id)+"\">Watched</a>");
						out.println("<a href=\"like.jsp?q="+Integer.toString(m.id)+"\">Favorite</a>");
						break;
					case 1:
						out.println("<a href=\"hate.jsp?q="+Integer.toString(m.id)+"\">Hated</a>");
						out.println("<a href=\"unlike.jsp?q="+Integer.toString(m.id)+"\">Remove from Watched</a>");
						out.println("<a href=\"like.jsp?q="+Integer.toString(m.id)+"\">Favorite</a>");
						break;
					case 2:
						out.println("<a href=\"hate.jsp?q="+Integer.toString(m.id)+"\">Hated</a>");
						out.println("<a href=\"watch.jsp?q="+Integer.toString(m.id)+"\">Watched</a>");
						out.println("<a href=\"unlike.jsp?q="+Integer.toString(m.id)+"\">Un-favorite</a>");
						break;
					default: break;
				}
			}
			
			out.println("<h1>"+mi.getTitle()+"</h1>");
			if (!mi.getImage().equals(""))
				out.println("<img src=\""+mi.getImage()+"\" alt=\""+mi.getImage()+"\">");
			
			out.println("<h2>General Info</h2>");
			out.println("<p>Rating "+mi.getAgeRating()+"</p>");
			
			out.println("<h2>Reviews</h2>");
			if (mi.getMetaScore()!=0) out.println("<p>Metacritic "+mi.getMetaScore()+"</p>");
			else out.println("<p>Metacritic unavailable</p>");
			out.println("<p>IMDB "+mi.getIMDBScore()+"</p>");
			
			out.println("<h2>Where to Watch</h2>");
			out.println("<p>Netflix: "+((mi.isOnNetflix())? "available":"unavailable")+"</p>");
			
			if (!mi.foundTropes()) out.println("<p>Tropes failed</p>");
			
			out.println("<h2>Tags</h2><ul>");
			for (String s: mi.getTags()) out.println("<li>"+s+"</li>");
			out.println("len"+mi.getTags().size());
			out.println("</ul>");
			
			out.println("<h2>Genres</h2><ul>");
			for (Object s: mi.getGenres().toArray()) out.println("<li>"+s.toString()+"</li>");
			out.println("</ul>");
		}
	}
  %>
  

 
  </body>
</html>
