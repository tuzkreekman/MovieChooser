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
  <%@ page import="uci.zainabk.movies.*"%>
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
		Choose your movie genre: <select name="genre">
			<option value="-1"></option>
		<%
			for (Genre g : Genre.values())
				out.println("<option value=\""+g.getID()+"\">"+g.getName()+"</option>");
		%>
		    </select><br>
		Choose your age rating:  <select name="age">
			<option value="e"></option>
			<option value="G">G</option>
			<option value="PG">PG</option>
			<option value="PG-13">PG-13</option>
			<option value="R">R</option>
		    </select><br>
		Choose a movie seed:     <input name="seed" type="text"><br>
		<input type="submit" name="button" value="Explore"><br>
		</form>
  
  <%
	String funcID = request.getParameter("funcID");
	if (funcID==null) {;}
	else if (funcID.equals("1")) {
		String genre = request.getParameter("genre");
		String age = request.getParameter("age");
		String seed = request.getParameter("seed");
		MovieList ml = new MovieList();
		ArrayList<String> params = new ArrayList<String>();
		if (!genre.equals("-1")) {
			//Genre g = Genre.getGenre(Integer.parseInt(genre));
			params.add("with_genres");
			params.add(genre);
		} if (!age.equals("e")){
			params.add("certification");
			params.add(age);
			params.add("certification_country");
			params.add("US");
		} /*if (!seed.equals("")) {
			
		}*/
		ArrayList<MovieSuggestion> movies = new MovieList().getMovies(params.toArray(new String[0]));
		for (int i = 0; i <movies.size(); i++) {
			MovieSuggestion current = movies.get(i);
			MovieInfo mi = new MovieInfo(current.getTitle());
			Movie m = mi.getMovie();
			FavDatabase fdb = new FavDatabase(db.getConnection());
			if (fdb.hasFav(id,m.id)) {
				movies.remove(i);
				out.println("<p>beentheredonethat</p>");
			} else if (i > 10) movies.remove(i);
		}
		for (MovieSuggestion ms : movies) {
			String title = ms.getTitle();
			out.println("<p><a href=\"search.jsp?q="+title+"\">"+title+"</a></p>");
			//out.println("<p>"+ms.getTitle()+"</p>");
		}
	}
  %>


  
 
  </body>
</html>
