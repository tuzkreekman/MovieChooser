<%@ page contentType="application/json" %>
  <%@ page import="java.io.*"%>
  <%@ page import="uci.zainabk.html.*"%>
  <%@ page import="uci.zainabk.tvtropes.*"%>
  
  <% 
	//HTMLStandard.opener("HTML JAVA",out);
	//HTMLStandard.startBody(out);
	//HTMLStandard.endBody(out);
	out.print("{\"tropes\":[\"");
	InputStream is = TVTropeLoader.cutFront();
	TVTropeLoader.spitOutTropes(is,out);
	out.print("\"]}");
	//HTMLStandard.ender(out);
	
	%>