<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title> Customer crud Operation </title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
	  <style>
	  .q {
            color: white;
        }
          </style>
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: navy">
			<div class="q">
				<a> Customer crud Operation </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Customers</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${customer != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${customer == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${customer != null}">
            			Edit customer
            		</c:if>
						<c:if test="${customer == null}">
            			Add New customer
            		</c:if>
					</h2>
				</caption>

				<c:if test="${customer != null}">
					<input type="hidden" name="id" value="<c:out value='${customer.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>customer first Name</label> <input type="text"
						value="<c:out value='${customer.firstName}' />" class="form-control"
						name="firstName" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>customer Last Name</label> <input type="text"
						value="<c:out value='${customer.lastName}' />" class="form-control"
						name="lastName" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>customer Email</label> <input type="text"
						value="<c:out value='${customer.email}' />" class="form-control"
						name="email">
				</fieldset>
				
				<fieldset class="form-group">
					<label>customer Password</label> <input type="text"
						value="<c:out value='${customer.password}' />" class="form-control"
						name="password">
				</fieldset>

				<fieldset class="form-group">
					<label>customer state</label> <input type="text"
						value="<c:out value='${customer.state}' />" class="form-control"
						name="state">
				</fieldset>
				
				<fieldset class="form-group">
					<label>customer street</label> <input type="text"
						value="<c:out value='${customer.street}' />" class="form-control"
						name="street">
				</fieldset>
				
				<fieldset class="form-group">
					<label>customer address</label> <input type="text"
						value="<c:out value='${customer.address}' />" class="form-control"
						name="address">
				</fieldset>
				<fieldset class="form-group">
					<label>customer city</label> <input type="text"
						value="<c:out value='${customer.city}' />" class="form-control"
						name="city">
				</fieldset>

               	<fieldset class="form-group">
					<label>customer phone</label> <input type="text"
						value="<c:out value='${customer.phone}' />" class="form-control"
						name="phone">
				</fieldset>
				<button type="submit" class="btn btn-success" style="background-color: navy; color: white;">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>