<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="bar" tagdir="/WEB-INF/tags" %>
<html>
  <head>
    <%@ page import="uci.zainabk.database.*"%>
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
  <form method="post">
		<input name="funcID" type="hidden" value="1">
		<input type="submit" value="Edit Username">
		</form>
  <form method="post">
		<input name="funcID" type="hidden" value="2">
		<input type="submit" value="Edit Password">
		</form>
  <form method="post">
		<input name="funcID" type="hidden" value="3">
		<input type="submit" value="Logout">
		</form>
  
  <%
	String funcID = request.getParameter("funcID");
	if (funcID==null) {;
	} else if (funcID.equals("3")) { 
		Login.logout();
		out.println("<meta http-equiv=\"refresh\" content=\"0; URL='login.jsp'\" />");
	} else if (funcID.equals("1")) {
		out.println("<b>Edit:</b>");
		out.println("<form method=\"post\">");
		out.println("<input name=\"funcID\" type=\"hidden\" value=\"4\">");
		out.println("New username: <input name=\"name\" type=\"text\" value=\""+thing+"\" required>");
		out.println("<input type=\"submit\" value=\"Done\"/>");
		out.println("</form>");
	} else if (funcID.equals("2")) {
		out.println("<b>Edit:</b>");
		out.println("<form method=\"post\">"); 
		out.println("<input name=\"funcID\" type=\"hidden\" value=\"5\">");
		out.println("New password: <input name=\"pwd\" type=\"password\" required>");
		out.println("<input type=\"submit\" value=\"Done\"/>");
		out.println("</form>");
	} else if (funcID.equals("4")) {
		int output = udb.editUsername(id,request.getParameter("name"));
		//force refresh?
		if (output>=0) out.println("<p>Success</p>");
		else out.println("<p>Failure</p>");
	} else if (funcID.equals("5")) {
		int output = udb.editPassword(id,request.getParameter("pwd"));
		if (output>=0) out.println("<p>Success</p>");
		else out.println("<p>Failure</p>");
	}
  %>
  
  
  </body>
</html>
