package com.sunbase.customer.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sunbase.customer.model.Customer;

public class CustomerDao {
	// Method to establish a database connection
	private Connection getConnection() {
	    try {
	        // Load the JDBC driver
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {
	            throw new SQLException("MySQL JDBC driver not found.", e);
	        }

	        // Establish a database connection
	        String url = "jdbc:mysql://137.59.52.212:3306/philm_cgi";
	        String username = "admin";
	        String password = "30yca@Oms#123";
	        return DriverManager.getConnection(url, username, password);
	    } catch (SQLException e) {
	        // Handle any SQL exceptions that may occur during connection
	        e.printStackTrace();
	        throw new RuntimeException("Failed to connect to the database", e);
	    }
	}

	// Method to insert a customer record into the database
	public void insertCustomer(Customer customer) {
	    try (Connection connection = getConnection();
	         // Prepare a SQL statement for inserting a new customer record
	         PreparedStatement statement = connection.prepareStatement(
	                 "INSERT INTO customer (first_name, last_name, street, address, state, city, email, phone, passsword, uuid) " +
	                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

	        // Set parameters in the prepared statement based on the provided Customer object
	        statement.setString(1, customer.getFirstName());
	        statement.setString(2, customer.getLastName());
	        statement.setString(3, customer.getStreet());
	        statement.setString(4, customer.getAddress());
	        statement.setString(5, customer.getState());
	        statement.setString(6, customer.getCity());
	        statement.setString(7, customer.getEmail());
	        statement.setString(8, customer.getPhone());
	        statement.setString(9, customer.getPassword());
	        statement.setString(10, customer.getUuid());

	        // Execute the insert statement
	        int affectedRows = statement.executeUpdate();

	        // Check if the insert operation was successful
	        if (affectedRows == 0) {
	            throw new SQLException("Creating customer failed, no rows affected.");
	        }

	        // Retrieve the generated keys (in this case, the customer ID)
	        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                customer.setId(generatedKeys.getInt(1));
	            } else {
	                throw new SQLException("Creating customer failed, no ID obtained.");
	            }
	        }
	    } catch (SQLException e) {
	        // Handle any SQL exceptions that may occur during the insert operation
	        e.printStackTrace();
	    }
	}

	// Method to retrieve all customer records from the database based on search criteria
	public List<Customer> getAllCustomers(String search, String searchBy) {
	    List<Customer> customers = new ArrayList<>();

	    // Check if search and searchBy parameters are provided
	    if (search != null && !search.isEmpty() && searchBy != null && !searchBy.isEmpty()) {
	        String searchColumn = "";

	        // Map the searchBy parameter to the corresponding database column
	        if (searchBy.equalsIgnoreCase("firstName")) {
	            searchColumn = "first_name";
	        } else if (searchBy.equalsIgnoreCase("city")) {
	            searchColumn = "city";
	        } else if (searchBy.equalsIgnoreCase("email")) {
	            searchColumn = "email";
	        } else if (searchBy.equalsIgnoreCase("phone")) {
	            searchColumn = "phone";
	        }

	        // Execute the query based on the search criteria
	        try (Connection connection = getConnection();
	             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE " + searchColumn + " = ?")) {

	            // Set the search parameter in the prepared statement
	            statement.setString(1, search);

	            // Execute the query and process the result set
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    Customer customer = new Customer();
	                    customer.setId(resultSet.getInt("id"));
	                    customer.setFirstName(resultSet.getString("first_name"));
	                    customer.setLastName(resultSet.getString("last_name"));
	                    customer.setStreet(resultSet.getString("street"));
	                    customer.setAddress(resultSet.getString("address"));
	                    customer.setState(resultSet.getString("state"));
	                    customer.setCity(resultSet.getString("city"));
	                    customer.setEmail(resultSet.getString("email"));
	                    customer.setPassword(resultSet.getString("passsword"));
	                    customer.setPhone(resultSet.getString("phone"));
	                    customers.add(customer);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }

	    } else {
	        // If no search criteria provided, retrieve all customer records
	        try (Connection connection = getConnection();
	             Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery("SELECT * FROM customer")) {

	            while (resultSet.next()) {
	                Customer customer = new Customer();
	                customer.setId(resultSet.getInt("id"));
	                customer.setFirstName(resultSet.getString("first_name"));
	                customer.setLastName(resultSet.getString("last_name"));
	                customer.setStreet(resultSet.getString("street"));
	                customer.setAddress(resultSet.getString("address"));
	                customer.setState(resultSet.getString("state"));
	                customer.setCity(resultSet.getString("city"));
	                customer.setEmail(resultSet.getString("email"));
	                customer.setPhone(resultSet.getString("phone"));
	                customers.add(customer);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return customers;
	}

 // Method to retrieve a customer record from the database based on the provided customerId
    public Customer getCustomerById(int customerId) {
        // Initialize a Customer object to hold the retrieved customer information
        Customer customer = null;

        try (Connection connection = getConnection();
             // Prepare a SQL statement to select a customer based on the customer ID
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE id = ?")) {

            // Set the customer ID parameter in the prepared statement
            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if a customer with the specified ID was found in the database
                if (resultSet.next()) {
                    // Populate the Customer object with information retrieved from the database
                    customer = new Customer();
                    customer.setId(resultSet.getInt("id"));
                    customer.setFirstName(resultSet.getString("first_name"));
                    customer.setLastName(resultSet.getString("last_name"));
                    customer.setStreet(resultSet.getString("street"));
                    customer.setAddress(resultSet.getString("address"));
                    customer.setState(resultSet.getString("state"));
                    customer.setCity(resultSet.getString("city"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setPassword(resultSet.getString("passsword")); // Fixed typo in column name
                    customer.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur during the database query
            e.printStackTrace(); 
        }

        // Return the retrieved customer object (or null if not found)
        return customer;
    }

    // Method to check if a customer with the specified username and password exists in the database
    public Boolean checkUserNamePasswordExist(String userName, String password) {
        // Initialize a Customer object to hold the retrieved customer information
        Customer customer = null;

        try (Connection connection = getConnection();
             // Prepare a SQL statement to select a customer based on the username and password
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE email = ? AND passsword = ?")) {

            // Set parameters in the prepared statement based on the provided username and password
            statement.setString(1, userName);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if a customer with the specified username and password was found in the database
                if (resultSet.next()) {
                    // Populate the Customer object with information retrieved from the database
                    customer = new Customer();
                    customer.setId(resultSet.getInt("id"));
                    customer.setFirstName(resultSet.getString("first_name"));
                    customer.setLastName(resultSet.getString("last_name"));
                    customer.setStreet(resultSet.getString("street"));
                    customer.setAddress(resultSet.getString("address"));
                    customer.setState(resultSet.getString("state"));
                    customer.setCity(resultSet.getString("city"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setPassword(resultSet.getString("passsword"));
                    customer.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur during the database query
            e.printStackTrace();
        }

        // Return true if a customer with the specified username and password was found, otherwise return false
        return (customer != null);
    }

 // Method to check if a customer with a specific UUID exists in the database
    public Boolean checkUuid(String uuid) {
        // Initialize a Customer object to hold the retrieved customer information
        Customer customer = null;

        try (Connection connection = getConnection();
             // Prepare a SQL statement to select a customer based on the UUID
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE uuid = ?")) {

            // Set the UUID parameter in the prepared statement
            statement.setString(1, uuid);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if a customer with the specified UUID was found in the database
                if (resultSet.next()) {
                    // Populate the Customer object with information retrieved from the database
                    customer = new Customer();
                    customer.setId(resultSet.getInt("id"));
                    customer.setFirstName(resultSet.getString("first_name"));
                    customer.setLastName(resultSet.getString("last_name"));
                    customer.setStreet(resultSet.getString("street"));
                    customer.setAddress(resultSet.getString("address"));
                    customer.setState(resultSet.getString("state"));
                    customer.setCity(resultSet.getString("city"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setPassword(resultSet.getString("passsword")); // Fixed typo in column name
                    customer.setPhone(resultSet.getString("phone"));
                }
            }
        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur during the database query
            e.printStackTrace(); 
        }

        // Return true if a customer with the specified UUID was found, otherwise return false
        return (customer != null);
    }

 // Method to update customer information in the database based on the provided Customer object
    public void updateCustomer(Customer customer) {
        try (Connection connection = getConnection();
             // Prepare a SQL statement for updating customer information
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE customer SET first_name=?, last_name=?, street=?, address=?, state=?, city=?, email=?, phone=?, passsword=?, uuid=? " +
                             "WHERE id=?")) {

            // Set parameters in the prepared statement based on the provided Customer object
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getStreet());
            statement.setString(4, customer.getAddress());
            statement.setString(5, customer.getState());
            statement.setString(6, customer.getCity());
            statement.setString(7, customer.getEmail());
            statement.setString(8, customer.getPhone());
            statement.setString(9, customer.getPassword());
            statement.setString(10, customer.getUuid());
            
            // Set the customer ID parameter for the WHERE clause in the SQL statement
            statement.setInt(11, customer.getId());

            // Execute the update statement and get the number of affected rows
            int affectedRows = statement.executeUpdate();

            // Check if no rows were affected, indicating that the customer with the given ID was not found
            if (affectedRows == 0) {
                throw new SQLException("Updating customer failed, no rows affected.");
            }

        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur during the update operation
            e.printStackTrace(); 
        }
    }

 // Method to delete a customer from the database based on the provided customerId
    public void deleteCustomer(int customerId) {
        try (Connection connection = getConnection();
             // Prepare a SQL statement for deleting a customer with the specified ID
             PreparedStatement statement = connection.prepareStatement("DELETE FROM customer WHERE id=?")) {

            // Set the customer ID parameter in the prepared statement
            statement.setInt(1, customerId);

            // Execute the update statement and get the number of affected rows
            int affectedRows = statement.executeUpdate();

            // Check if no rows were affected, indicating that the customer with the given ID was not found
            if (affectedRows == 0) {
                throw new SQLException("Deleting customer failed, no rows affected.");
            }

        } catch (SQLException e) {
            // Handle any SQL exceptions that may occur during the delete operation
            e.printStackTrace();
        }
    }

}

