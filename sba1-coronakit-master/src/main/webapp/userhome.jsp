<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit- User Home</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
	<% String msg = (String) request.getAttribute("msg"); %>	
	<%if(msg!=null) {%>
		<p><strong><%=msg %></strong></p>
	<%} else {%>
	<h2>Welcome ${newUser }</h2>
	<%} %>
	
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>