<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>User Management Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
	
	   <style>
        .white-arrow {
            color: white;
        }
        .q {
            color: white;
        }
         .success-container {
            margin: 20px auto;
            max-width: 400px;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
          button {
            background-color: aquamarine;
            color: #333333;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: coral;
        }
    </style>
</head>
<body>
<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color:  navy;">
			<div class="q">
				<a href="#" onclick="history.back()">
    <i class="fas fa-arrow-left white-arrow"></i> 
</a>
			</ul><a> Customer Details </a>
			</div>

		
		</nav>
	</header>


<c:if test="${empty customer}">
    <p>No customer found with the given ID.</p>
</c:if>

<c:if test="${not empty customer}">
<div class="success-container">
<h3>Customer Details:</h3>
    <div>
        <label>ID:</label>
        <span><c:out value="${customer.id}" /></span>
    </div>
    <div>
        <label>First Name:</label>
        <span><c:out value="${customer.firstName}" /></span>
    </div>
    <div>
        <label>Last Name:</label>
        <span><c:out value="${customer.lastName}" /></span>
    </div>
    <div>
        <label>Street:</label>
        <span><c:out value="${customer.street}" /></span>
    </div>
    <div>
        <label>Address:</label>
        <span><c:out value="${customer.address}" /></span>
    </div>
    <div>
        <label>State:</label>
        <span><c:out value="${customer.state}" /></span>
    </div>
    <div>
        <label>City:</label>
        <span><c:out value="${customer.city}" /></span>
    </div>
    <div>
        <label>Email:</label>
        <span><c:out value="${customer.email}" /></span>
    </div>
    <div>
        <label>Phone:</label>
        <span><c:out value="${customer.phone}" /></span>
    </div>
</c:if>
<div class="container text-right">
<a href="<%=request.getContextPath()%>/list" class="btn btn-success" style="background-color: white; color: black;">Close</a>
			</div>
</div>


</body>
</html>
