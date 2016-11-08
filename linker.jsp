<%@ page contentType="application/json" %>
<%@ page import="java.io.*"%>
<%@ page import="uci.zainabk.tvtropes.*"%>
<%
	out.print("{\"tropes\":[");
	//System.out.println("p1");
	InputStream is = TVTropeLoader.loadAndCutFront(TVTropeFinder.findTropeURL(request.getParameter("q")));
	//System.out.println("p2");
	TVTropeLoader.spitOutTropes(is,out);
	out.print("]}");
%>