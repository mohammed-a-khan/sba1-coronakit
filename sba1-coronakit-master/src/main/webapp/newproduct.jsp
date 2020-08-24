<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Add New Product(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<div style="width:375px;border:1px solid grey">
<h3><center>${isNew?"New Item":"Edit Item" }</center></h3>
	<hr/>
	<form action='${isNew?"insertproduct":"updateproduct"}' method="POST">
		<table>
		<% boolean flag = (boolean)request.getAttribute("isNew"); %>	
		<%if(!flag) {%>
			<tr>
				<td><label><strong>Product Id:</strong></label></td>
				<td>&nbsp;&nbsp;&nbsp;<input type="number" name="productId" value="${product.id }" ${isNew?"":"readonly" } required/></td>
			</tr>
		<%} %>
		<tr>
			<td><label><strong>Product Name:</strong></label></td>
			<td>&nbsp;&nbsp;&nbsp;<input type="text" name="productName" value="${product.productName }" required/></td>
		</tr>
		<tr>
			<td><label><strong>Product Cost:</strong></label></td>
			<td>&nbsp;&nbsp;&nbsp;<input type="text" name="cost" value="${product.cost }" required/></td>
		</tr>
		<tr>
			<td><label><strong>Product Description:</strong></label></td>
			<td>&nbsp;&nbsp;&nbsp;<textarea style="width:90%" name="productDesc" value="${product.productDescription }" required>${product.productDescription }</textarea></</td>
		</tr>
		</table>
		<br/>
		<center><button><strong>SAVE</strong></button></center><br/>
	</form>
</div>	
	<jsp:include page="footer.jsp"/>
</body>
</html>