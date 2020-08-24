<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-New User(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<% String msg = (String) request.getAttribute("msg"); %>	
	<%if(msg!=null) {%>
		<p><strong><%=msg %></strong></p>
<%} %>
<div style="width:375px;border:1px solid grey">
<h3><center>New User Registration</center></h3>
<hr/><br/>
	<form action="insertuser" method="POST">
		<table>
		<tr>
			<td><label><strong>Name:</strong></label></td>
			<td><input type="text" name="pname" required/></td>
		</tr>
		<tr>
			<td><label><strong>Email:</strong></label></td>
			<td><input type="text" name="pemail" required/></td>
		</tr>
		<tr>
			<td><label><strong>Contact Number:</strong></label></td>
			<td><input type="number" name="pcontact" min="0" required/></td>
		</tr>
		</table><br/>
		<center><button><strong>CREATE</strong></button></center><br/>
	</form>
</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>