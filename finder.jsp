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
		<input type="submit" name="button" value="Explore" onclick="show_image()"><br>
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
		boolean seeded = false;
		int m_id = 0;
		if (!genre.equals("-1")) {
			//Genre g = Genre.getGenre(Integer.parseInt(genre));
			params.add("with_genres");
			params.add(genre);
		} if (!age.equals("e")){
			params.add("certification");
			params.add(age);
			params.add("certification_country");
			params.add("US");
		} if (!seed.equals("")) {
			try {
				m_id = MovieRecommender.findTMDBID(seed);
				if (m_id!=0) seeded = true;
			} catch (Exception e) {
				System.out.println("Tried to seed, failed");
				out.println("<p>Sorry, your seed failed. We will ignore it and recommend based on your other parameters.</p>");
			}
		}
		
		try {
			if (seeded) {
				try {
					MovieSuggestion current = MovieRecommender.recommendSimilar(m_id,params.toArray(new String[0]));
					MovieInfo mi = new MovieInfo(current.getTitle(), db);
					out.println("<h1><a href=\"search.jsp?q="+current.getTitle()+"\">"+current.getTitle()+"</a></h1>");
					out.println("<p><a href=\"like.jsp?q="+mi.getMovie().id+"\">Already love</a></p>");
					out.println("<p><a href=\"watch.jsp?q="+mi.getMovie().id+"\">Already watched</a></p>");
					out.println("<p><a href=\"hate.jsp?q="+mi.getMovie().id+"\">Already watched, and hated</a></p>");
				} catch (Exception e) {
					System.out.println("Failed mid-seed");
					out.println("<p>Sorry, your seed failed. We will ignore it and recommend based on your other parameters.</p>");
					seeded = false;
				}
				
			}
			if (!seeded) {
				MovieSuggestion current = MovieRecommender.recommend(params.toArray(new String[0]));
				MovieInfo mi = new MovieInfo(current.getTitle(), db);
				out.println("<h1><a href=\"search.jsp?q="+current.getTitle()+"\">"+current.getTitle()+"</a></h1>");
				out.println("<p><a href=\"like.jsp?q="+mi.getMovie().id+"\">Already love</a></p>");
				out.println("<p><a href=\"watch.jsp?q="+mi.getMovie().id+"\">Already watched</a></p>");
				out.println("<p><a href=\"hate.jsp?q="+mi.getMovie().id+"\">Already watched, and hated</a></p>");
			}
		} catch (Exception e) {
			out.println("<p>Woops, something broke! Please check the terminal for more info</p>");
			System.out.println(e.getClass()+" "+e.getMessage());
		}
	}
  %>
  
  <script>
  function show_image()
  
{
	var img = document.createElement("img");
    img.src = "awkward_smiley.gif";
    

    // This next line will just add it to the <body> tag
    document.body.appendChild(img);
}

  </script>
  


  
 
  </body>
</html>
