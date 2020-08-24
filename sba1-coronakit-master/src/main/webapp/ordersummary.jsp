<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Order Summary(user)</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<hr/>
<% String msg = (String) request.getAttribute("msg"); %>	
	<%if(msg!=null) {%>
		<p><strong><%=msg %></strong></p>
	<%} else {%>
	<h3>Order Summary</h3>
	<%} %>
	<c:choose>
			<c:when test="${orderSummary == null }">
				<p>No Order was placed</p>
			</c:when>
			<c:otherwise>
			<div style="width:375px;border:1px solid grey">
				<table border="1" cellspacing="1px" cellpadding="1px" style="width:100%">
					
						<tr><th style='text-align:left;width:50%'>Customer Name: </th><td>${orderSummary.coronaKit.personName }</td></tr>
						<tr><th style='text-align:left;width:50%'>Email: </th><td>${orderSummary.coronaKit.email }</td></tr>
						<tr><th style='text-align:left;width:50%'>Contact #: </th><td>${orderSummary.coronaKit.contactNumber }</td></tr>
						<tr><th style='text-align:left;width:50%'>Date of Order: </th><td>${orderSummary.coronaKit.orderDate }</td></tr>
						<tr><th style='text-align:left;width:50%'>Delivery Address: </th><td>${orderSummary.coronaKit.deliveryAddress }</td></tr>
						<tr><th style='text-align:left;width:50%'>Order Status: </th><td>${orderSummary.coronaKit.orderFinalized?"Confirmed":"Not Confirmed" }</td></tr>
					
					<tr>
					<table border="1" style="width:100%">
					<tr><th colspan='4'><strong><center>Order Details</center></strong></th></tr>
					<tr>
						<th>Product Id</th>
						<th>Product Name</th>
						<th>Quantity</th>
						<th>Amount</th>
					</tr>
					<c:forEach items="${orderSummary.getKitDetails() }" var="kitItemDetail">
						
						<tr>
							<td>${kitItemDetail.productId }</td>
							<td>${kitItemDetail.productName }</td>
							<td>${kitItemDetail.quantity }</td>
							<td>${kitItemDetail.amount }</td>
						</tr>
					</c:forEach>
					<tr><td colspan="3" style="text-align:right"><strong>Order Total:</strong></td>
					<td><strong>${orderSummary.coronaKit.totalAmount }</strong></td>
					</tr></table>
					</tr>
				</table>
				</div>
			</c:otherwise>
		</c:choose>
<hr/>	
	<jsp:include page="footer.jsp"/>
</body>
</html>