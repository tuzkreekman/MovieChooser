<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ page import="java.io.*"%>
  <%@ page import="uci.zainabk.html.*"%>
  <%@ page import="uci.zainabk.tvtropes.*"%>
  
  <% 
	HTMLStandard.opener("HTML JAVA",out);
	HTMLStandard.startBody(out);
	HTMLStandard.endBody(out);
	InputStream is = TVTropeLoader.cutFront();
	TVTropeLoader.spitOutTropes(is,out);
	HTMLStandard.ender(out);
	
	%>