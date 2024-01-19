<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>User Management Application</title>
    <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <style>
        .q {
            color: white;
        }
        /* Add custom styles for better alignment */
        .navbar {
            padding: 10px;
        }
        .add-customer-btn {
            margin-top: 10px;
        }
        .pagination {
    margin-top: 10px;
    text-align: center;
}

.pagination a {
    display: inline-block;
    padding: 5px 8px;
    text-decoration: none;
    background-color: #007bff;
    color: #fff;
    border-radius: 4px;
    margin: 0 2px;
}

.pagination a:hover {
    background-color: #0056b3;
}

.pagination p {
    display: inline-block;
    margin: 0 5px;
    font-weight: bold;
    color: #333;
}
        
        
    </style>
    <script>
        function clearSearchForm() {
            document.getElementById("searchInput").value = "";
            document.getElementById("filterDropdown").selectedIndex = 0;
        }

        if (performance.navigation.type == 1) {
            clearSearchForm();
        }
    </script>
</head>
<body onload="clearSearchForm()">
<%@ page import="java.util.List" %>
    <%@ page import="com.sunbase.customer.model.Customer" %>
<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: navy;">
        <div class="q">
            <a class="navbar-brand">Customer Crud Operation</a>
        </div>
        <ul class="navbar-nav">
            <li class="nav-item">
                <a href="<%=request.getContextPath()%>/list" class="nav-link">Customers</a>
            </li>
        </ul>
    </nav>
</header>
<br>

<div class="container">
    <div class="row">
        <!-- "Add New Customer" button -->
        <div class="col-md-4 text-left add-customer-btn">
            <a href="<%=request.getContextPath()%>/new" class="btn btn-success" style="background-color: navy; color: white;">Add New Customer</a>
        </div>

    
         <div class="row">
          <div class="add-customer-btn">
            <a href="<%=request.getContextPath()%>/sync" class="btn btn-success" style="background-color: navy; color: white;">sync</a>
        </div>
    <div class="form-group mx-8 add-customer-btn ml-2"">
       <form action="<%=request.getContextPath()%>/list" method="get" class="form-inline">
       <select class="form-control" id="filterDropdown" name="searchBy">
            <option value="All" ${empty param.searchBy ? 'selected' : ''}>All</option>
            <option value="firstName" ${param.searchBy == 'firstName' ? 'selected' : ''}>First Name</option>
            <option value="city" ${param.searchBy == 'city' ? 'selected' : ''}>City</option>
            <option value="email" ${param.searchBy == 'email' ? 'selected' : ''}>Email</option>
            <option value="phone" ${param.searchBy == 'phone' ? 'selected' : ''}>Phone</option>
        </select>
    </div>

    <!-- Search form -->
     <div class="form-group add-customer-btn ml-2">
            <input placeholder="search" type="text" class="form-control" id="searchInput" name="search" value="${param.search}">
        </div>
        <div class="form-group add-customer-btn ml-2">
            <button type="submit" class="btn btn-primary" style="background-color: navy; color: white;">Apply Filters</button>
        </div>
    </form>
</div>


    <br>

    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Street</th>
            <th>Address</th>
            <th>City</th>
            <th>State</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        
         <% List<Customer> currentPageItems = (List<Customer>) request.getAttribute("listcustomer");
    for (Customer customer : currentPageItems) { %>
            <tr>
             <td><%= customer.getId() %></td>
                    <td><%= customer.getFirstName() %></td>
                    <td><%= customer.getLastName() %></td>
                    <td><%= customer.getStreet() %></td>
                    <td><%= customer.getAddress() %></td>
                    <td><%= customer.getCity()%></td>
                    <td><%= customer.getState() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td><%= customer.getPhone() %></td>
            
                <td>
                    <a href="edit?id=<%= customer.getId() %>"><i class="fas fa-edit"></i></a>
                    <a href="delete?id=<%= customer.getId() %>"><i class="fas fa-trash-alt"></i></a>
                    <a href="veiw?id=<%= customer.getId() %>"><i class="fas fa-eye"></i></a>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
    <div class="pagination">
    <% int totalPages = (int) request.getAttribute("totalPages");
       int currentPage = (int) request.getAttribute("currentPage"); %>
 <c:if test="${currentPage > 1}">
        <a href="?page=<%= currentPage - 1 %>">&lt;&lt; Previous</a>
    </c:if>
    <p>Page <%= currentPage %> of <%= totalPages %></p>

    <c:if test="${currentPage < totalPages}">
        <a href="?page=<%= currentPage + 1 %>">Next &gt;&gt;</a>
    </c:if>
</div>

</div>

</body>
</html>
