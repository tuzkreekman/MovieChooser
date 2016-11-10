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
	  out.println("<meta http-equiv=\"refresh\" content=\"0; URL='login.jsp'\" />");
  } 
  Database db = new Database();
  UserDatabase udb = new UserDatabase(db.getConnection());
  if (id!=-1) thing = (udb.getUser(Integer.toString(id),true).name);
  else thing = ("Login");
  %>
  <bar:horizontal_bar loggedin="<%=thing%>"/>


  <h1>Welcome to MovieChooser!</h1>
  <h2>Login</h2>
  <form method="post">
		<input name="funcID" type="hidden" value="1">
		Username: <input name="username" type="text" required><br>
		Password: <input name="password" type="password" required><br>
		<input type="submit" name="button" value="Login">
		<input type="submit" name="button" value="New User"><br>
		</form>
  
  <%
  String funcID = request.getParameter("funcID");
  try{ 
	if (funcID!=null && funcID.equals("1")) {
		boolean success = false;
		String u = request.getParameter("username");
		String p = request.getParameter("password");
		if (request.getParameter("button").equals("Login")) {
			success = Login.login(db,u,p);
			if (success) {
			  out.println("<meta http-equiv=\"refresh\" content=\"0; URL='user.jsp'\" />");
			} else out.println("<p>Login failed, look at apache for more info</p>");
		} else if (request.getParameter("button").equals("New User")) {
			success = (udb.addUser(u,p)>=0);
			if (success) success = Login.login(db,u,p);
			if (success) {
			  out.println("<meta http-equiv=\"refresh\" content=\"0; URL='user.jsp'\" />");
			} else out.println("<p>Add new user failed, look at apache for more info</p>");
		} else System.out.println("Weird");
	}
  } finally {
	  db.close();
  }
  %>
  
  </body>
</html>
