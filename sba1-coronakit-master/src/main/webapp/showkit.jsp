<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-My Kit(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<% String msg = (String) request.getAttribute("msg"); %>
<% int totalAmount=0; %>	
	<%if(msg!=null) {%>
		<p><strong><%=msg %></strong></p>
<%} %>
<h3>Kit Details</h3>
	<c:choose>
			<c:when test="${kitItemDetails == null || kitItemDetails.isEmpty() }">
				<p>Kit is empty try <a href="showproducts">adding</a> one</p>
			</c:when>
			<c:otherwise>
				<table border="1" cellspacing="5px" cellpadding="5px">
					<tr>
						<th>Product Id</th>
						<th>Product Name</th>
						<th>Quantity</th>
						<th>Amount</th>
						<th>Action</th>
					</tr>
					<c:set var="totalAmount" value="${0}" />
					<c:forEach items="${kitItemDetails }" var="kitItemDetail">
						
						<tr>
							<td>${kitItemDetail.productId }</td>
							<td>${kitItemDetail.productName }</td>
							<td>${kitItemDetail.quantity }</td>
							<td>${kitItemDetail.amount }</td>
							<c:set var="totalAmount" value="${totalAmount + kitItemDetail.amount}" />
							<td>
								<a href="deleteitem?kid=${kitItemDetail.id }">DELETE</a>								
							</td>
						</tr>
					</c:forEach>
					<tr><td colspan="3" style="text-align:right"><strong>Cart Total:</strong></td>
					<td><strong>${totalAmount}</strong></td>
					<td>
						<a href="placeorder?total=${totalAmount}">Check Out</a>								
					</td></tr>
				</table>
			</c:otherwise>
		</c:choose>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>