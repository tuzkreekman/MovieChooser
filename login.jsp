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


  <h1>Welcome to MovieChooser!</h1>
  <h2>Login</h2>
  <form method="post">
		<input name="funcID" type="hidden" value="1">
		Username: <input name="username" type="text" required><br>
		Password: <input name="password" type="password" required><br>
		<input type="submit" value="Login"><br>
		</form>
  
  <%
  String funcID = request.getParameter("funcID");
  try{ 
	if (funcID!=null && funcID.equals("1")) {
		boolean success = false;
		Database db = new Database();
		String u = request.getParameter("username");
		String p = request.getParameter("password");
		success = Login.login(db,u,p);
		db.close();
		if (success) {
		  out.println("<meta http-equiv=\"refresh\" content=\"0; URL='user.jsp'\" />");
		} else out.println("<p>Login failed, look at apache for more info</p>");
	}
  } finally {
	  ;
  }
  %>
  
  </body>
</html>
