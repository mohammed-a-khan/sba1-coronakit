<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Home</title>
</head>
<body>
<div>
<jsp:include page="header.jsp"/>
<hr/>
	<% String msg = (String) request.getAttribute("msg"); %>	
	<%if(msg!=null) {%>
		<p><strong><%=msg %></strong></p>
	<%} %>
	<div style="width:350px;border:1px solid black">
	<h3><center>Admin Login</center></h3>
	<hr/>
	<form action="adminlogin" method="post">
		<table border="1px">
			<tr><td style="width:50%"><div><label for="loginid"><Strong>Enter login Id</Strong></label> </div></td>
			<td><div><input type="text" id="loginid" name="loginid"> </div></td></tr>
			<tr><td style="width:50%"><div><label for="password"><strong>Enter password</strong></label> </div></td>
			<td><div><input type="text" id="password" name="password"> </div></td></tr>
		</table><br>
		<div>
			<center><div><input type="submit"></div></center><br/>
		</div>
	</form>
	</div>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html>