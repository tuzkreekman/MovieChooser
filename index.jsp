<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="bar" tagdir="/WEB-INF/tags" %>
<html>
  <head>
    
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <title>MovieHunter</title>
    
	
  </head>
  <link rel="stylesheet" href="styles.css">
  <body style="background-color:#E6E6FA">

  <%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  
  <%@ page import="uci.zainabk.database.*"%>

  <%
  int id = Login.getUserID();
  String thing;
  if (id!=-1) {
	  thing = ((new UserDatabase((new Database()).getConnection())).getUser(Integer.toString(id),true).name);
  } else thing = ("Login");
  %>
  <bar:horizontal_bar loggedin="<%=thing%>"/>
  
  <h1>Welcome to MovieHunter!</h1>
  
  <h2>How To</h2>
  <p>To search for a movie, search for its title in the search bar above. 
  To keep track of movies seen, and to get personalized recommendations,
  create an account/log in. To find a new movie, select a genre, age group, and/or
  movie seed to get a movie FOR YOU.</p>
  
  <h2>About</h2>
  <p>MovieHunter would like to acknowledge the following resources that made this task easier</p>
  
  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://netflixroulette.net/api/">NetflixRoulette</a></p>
  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://www.omdbapi.com/">OMDB</a></p>
  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.themoviedb.org/">TMDB</a></p>
  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.google.com">Google</a></p>
  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://jsoup.org">Jsoup</a></p>
  
  <p>The following resources had useful/nonexistent information but useless APIs</p>
  
  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.commonsensemedia.org/">CommonSenseMedia</a></p>
  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.themoviedb.org/">TVTropes</a></p>
  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.themoviedb.org/">DBTropes</a></p>
  
  <p>Also, of course, shoutout to Professor Sheu and TA Bryan Chou. Thanks for an interesting class and a busy, productive quarter.</p>
  </body>
</html>
