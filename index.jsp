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
  
  
  </body>
</html>
