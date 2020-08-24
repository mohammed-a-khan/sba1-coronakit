<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Place Order(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<% String msg = (String) request.getAttribute("msg"); %>
<% int total = (int) request.getAttribute("total"); %>	
	<%if(msg!=null) {%>
		<p><strong><%=msg %></strong></p>
<%} %>
<div style="width:375px;border:1px solid grey">
<h3><center>Provide additional details to place order</center></h3>
<hr/>
	<form action="saveorder" method="POST">
	<table>
		<tr>
			<td><label><strong>Delivery Address:</strong></label></td>
			<td>&nbsp;&nbsp;&nbsp;<textarea style="width:95%" name="deliveryAddress" required></textarea></td>
		</tr>
		<tr><td><br/></td></tr>
		<tr>
			<td><label><strong>Total Amount: </strong></label></td>
			<td><input type="text" name="cTotalAmount" value="<%=total %>" readonly required/></td>
		</tr>
		</table></br>
		<center><button><strong>Confirm Order</strong></button></center><br/>
	</form>
</div>
	<jsp:include page="footer.jsp"/>
</body>
</html>