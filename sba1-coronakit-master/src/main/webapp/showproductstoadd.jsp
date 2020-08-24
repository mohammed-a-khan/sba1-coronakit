<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<% String msg = (String) request.getAttribute("msg"); %>	
	<%if(msg!=null) {%>
		<p><strong><%=msg %></strong></p>
<%} %>
<h3>Add products to Cart</h3>
	<c:choose>
			<c:when test="${products == null || products.isEmpty() }">
				<p>No Products to display</p>
			</c:when>
			<c:otherwise>
				<table border="1" cellspacing="5px" cellpadding="5px">
					<tr>
						<th>Product Id</th>
						<th>Product Name</th>
						<th>Cost</th>
						<th>Description</th>
						<th style="width: 200px">Action</th>
					</tr>
					<c:forEach items="${products }" var="product">
						<tr>
							<td>${product.id }</td>
							<td>${product.productName }</td>
							<td>${product.cost }</td>
							<td>${product.productDescription }</td>
							<td>
								<form action="addnewitem" method="POST"><input type="number" min="1" name="pQuantity" style="width: 35%" placeholder="Quantity" required>
								<input name="pId" hidden value="${product.id }">
								<input name="pCost" hidden value="${product.cost }">
								<span>|</span>&nbsp;<Button>Add to Kit</Button></form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:otherwise>
		</c:choose>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>