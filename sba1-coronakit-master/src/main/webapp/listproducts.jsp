<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(Admin)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>

	<c:choose>
			<c:when test="${products == null || products.isEmpty() }">
				<p>No Products Found Try <a href="newproduct">adding</a> one</p>
			</c:when>
			<c:otherwise>
				<table border="1" cellspacing="5px" cellpadding="5px">
					<tr>
						<th>Product Id</th>
						<th>Product Name</th>
						<th>Cost</th>
						<th>Description</th>
						<th>Action</th>
					</tr>
					<c:forEach items="${products }" var="product">
						<tr>
							<td>${product.id }</td>
							<td>${product.productName }</td>
							<td>${product.cost }</td>
							<td>${product.productDescription }</td>
							<td>
								<a href="deleteproduct?pid=${product.id }">DELETE</a>
								<span>|</span>
								<a href="editproduct?pid=${product.id }">EDIT</a>
								
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