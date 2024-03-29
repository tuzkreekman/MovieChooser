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
			out.println("<p>MPAA: "+mi.getAgeRating()+"</p>");
			out.println("<p>CommonSenseMedia: "+((mi.foundCSRating())?((mi.getCSAgeRating()!=-1)?mi.getCSAgeRating()+"+":"not found"):"not found")+"</p>");
			int temp = mi.getYear();
			out.println("<p>Year: "+((temp==0)?"unknown":temp)+"</p>");
			
			out.println("<h2>Reviews</h2>");
			temp = mi.getMetaScore();
			if (temp!=0) out.println("<p>Metacritic "+temp+"</p>");
			else out.println("<p>Metacritic unavailable</p>");
			double tmp = mi.getIMDBScore();
			out.println("<p>IMDB "+((tmp!=0)?tmp:"unrated")+"</p>");
			
			out.println("<h2>Where to Watch</h2>");
			out.println("<p>Netflix: "+((mi.isOnNetflix())? "available":"unavailable")+"</p>");
			
			out.println("<h2>Genres</h2><ul>");
			for (Object s: mi.getGenres().toArray()) out.println("<li>"+s.toString()+"</li>");
			out.println("</ul>");
			
			if (!mi.foundTropes()) out.println("<p>Tropes failed</p>");
			/*else {
				out.println("<p><a href=\"tropes.jsp?q="+searchStr+"\">See tropes</a></p>");
			}*/
			out.println("<h2>Tags</h2><ul>");
			for (Object s: mi.getTags().toArray()) out.println("<li>"+s.toString()+"</li>");
			out.println("</ul>");
			
			
		}
	}
  %>
  

 
  </body>
</html>
