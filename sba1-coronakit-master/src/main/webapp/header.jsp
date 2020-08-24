<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<header>
	<h1>Corona Kit Ordering Application</h1>
	<hr />
	<nav>
	<c:choose>
		<c:when test="${adminUser == null && newUser != null }">
			<a href="userhome.jsp">Home</a> 
			<span>|</span>
			<a href="showproducts">Create Kit</a>
			<span>|</span>
			<a href="showkit">Shopping Cart</a>
			<span>|</span>
			<a href="ordersummary">View Order Summary</a>
			<span>|</span>
			<a href="userlogout">Logout</a>
		</c:when>
		<c:when test="${adminUser != null}">
			<a href="adminHome.jsp">Home</a> 
			<span>|</span>
			<a href="listproducts"> View Products</a>
			<span>|</span>
			<a href="newproduct">Add Products</a>
			<span>|</span>
			<a href="logout">Logout</a>
		</c:when>
		<c:otherwise>
			<a href="index.jsp">Home</a> 
			<span>|</span>
			<a href="newuser"> New User</a>
		</c:otherwise>
	</c:choose>
	</nav>
</header>