<%@ page contentType="application/json" %>
<%@ page import="java.io.*"%>
<%@ page import="uci.zainabk.tvtropes.*"%>
<%
	out.print("{\"tropes\":[");
	InputStream is = TVTropeLoader.loadAndCutFront(TVTropeFinder.findTropeURL(request.getParameter("q")));
	TVTropeLoader.spitOutTropes(is,out);
	out.print("]}");
%>