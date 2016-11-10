<%@ tag %>
<%@ attribute name="loggedin" %>
<link rel="stylesheet" href="../styles.css">
<style>
ul[name] {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #333;
	height: 50px;
  }
li a[href="user.jsp"] {
	color: #45B39D;
}
</style>
<ul name="barh">
    <li name="something"><a href="index.jsp">Index</a></li>
	<li name="else"><form action = "search.jsp" method="get">
		<input type="search" name="q">
		</form>
	</li>
	<li name="login"><a href="user.jsp">${loggedin}</a></li>	
  </ul>
