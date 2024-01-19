package com.sunbase.customer.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunbase.customer.dao.CustomerDao;
import com.sunbase.customer.model.Customer;
import com.sunbase.customer.model.JwtUtils;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private CustomerDao customerdao; 
 
	
	
	
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {

    this.customerdao=new CustomerDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String action=request.getServletPath();
switch(action) {
case "/new":
	showNewForm(request, response);
	break;
case "/insert":
	inserCustomer(request, response);
	break;
case "/delete":
	deleteCustomer(request, response);
	break;
case "/edit":
	showEditForm(request, response);
	break;
case "/update":
	updateCustomer(request, response);
	break;
case "/veiw":
	veiwCustomer(request, response);
	break;
	
case "/list":
	listCustomer(request, response);
	break;
case "/sync":
	try {
		apicall(request, response);
	} catch (IOException e) {
		e.printStackTrace();
	} catch (JSONException e) {
		e.printStackTrace();
	}
	break;
case "/login":
	loginCustomer(request, response);
	break;
default:
showloginForm(request, response);
}
	}

	private void apicall(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
		 String apiUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
	     String requestBody = "{\r\n"
	     		+ "\"login_id\" : \"test@sunbasedata.com\",\r\n"
	     		+ "\"password\" :\"Test@123\"\r\n"
	     		+ "}";  
		URL url = new URL(apiUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

	        // Set the request method to POST 
	        connection.setRequestMethod("POST");


	        // Enable input/output streams
	        connection.setDoOutput(true);
	        connection.setDoInput(true);

	        // Write the request body
	        connection.getOutputStream().write(requestBody.getBytes("UTF-8"));

	        // Get the API response
	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        StringBuilder response1 = new StringBuilder();
	        String line;

	        while ((line = reader.readLine()) != null) {
	            response1.append(line);
	        }

	        // Close resources
	        reader.close();
	        connection.disconnect();
	     // Parse JSON string
	        JSONObject json = new JSONObject(response1.toString());

	        // Extract the value of the 'access_token'
	        String accessToken = json.getString("access_token");
	        System.out.println(accessToken);
	       
	        try {
	      

	            // API URL with parameter
	           String apiUrl1 = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";

	          


	            // Set up the HTTP connection
	            URL url1 = new URL(apiUrl1);
	            HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();

	            connection1.setRequestMethod("GET");
	            // Set authentication token
	            connection1.setRequestProperty("Authorization", "Bearer " + accessToken);

	            // Set other necessary headers

	            // Get the API response
	            BufferedReader reader1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
	            StringBuilder response2 = new StringBuilder();
	            String line1;

	            while ((line1 = reader1.readLine()) != null) {
	                response2.append(line1);
	            }

	            // Close resources
	            reader1.close();
	            connection1.disconnect();


	            // Create an ObjectMapper
	            ObjectMapper objectMapper = new ObjectMapper();

	            // Parse the JSON array
	            JsonNode jsonArray = objectMapper.readTree(response2.toString().trim());

	            // Iterate through each element in the array
	            for (JsonNode jsonNode : jsonArray) {
	       
	            	// Access individual fields
	            	String firstName="";
	            	String phone="";
	            	String lastName="";
	            	String street="";
	            	String address="";
	            	String state="";
	            	String city="";
	            	String email="";
	            	String uuid="";
	            	// set individual fields
	        if(jsonNode.get("first_name")!=null) {
	            firstName=jsonNode.get("first_name").asText();
	        }
	        if(jsonNode.get("uuid")!=null) {
	            uuid=jsonNode.get("uuid").asText();
	        }
	        if(jsonNode.get("first_name")!=null) {
	        	 lastName=jsonNode.get("last_name").asText();
	        }
	        if(jsonNode.get("street")!=null) {
	        	 street=jsonNode.get("street").asText();
	        }
	        if(jsonNode.get("address")!=null) {
	        	 address=jsonNode.get("address").asText();
	        }
	        if(jsonNode.get("state")!=null) {
	        	 state=jsonNode.get("state").asText();
	        }
		    
	        if(jsonNode.get("city")!=null) {
	        	 city=jsonNode.get("city").asText();
	        }
	        if(jsonNode.get("email")!=null) {
	        	 email=jsonNode.get("email").asText();
	        }
		     String password="123456";
		     if(jsonNode.get("phone")!=null) {
		    	  phone=jsonNode.get("phone").asText();
		     }
		     //load values in constructor
		     Customer customer=new Customer(firstName, lastName, street, address, state, city, email,password, phone,uuid);
		   //check uuid is exists or not
		     if(!customerdao.checkUuid(uuid)) {
		    	 //if not not exists insert record
		    	 customerdao.insertCustomer(customer);
		     }
	            }
	            //redirect to list
			     response.sendRedirect("list");

	       } catch (Exception e) {
	            e.printStackTrace();
	            // Handle exceptions
	            System.out.println("Error making API call: " + e.getMessage());
	        }
	        
		}

	private void loginCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    // Retrieve username and password from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate the username and password (replace this with your authentication logic)
        if (customerdao.checkUserNamePasswordExist(username, password)) {
            // Authentication successful

            // Generate JWT token
            String jwtToken = JwtUtils.generateToken(username);

            // Add the token to the response or use it as needed
            response.setHeader("Authorization", "Bearer " + jwtToken);

            // Redirect to the list
            response.sendRedirect("list");
            } else {
            // Authentication failed
            response.sendRedirect("login.jsp?error=true");
        }
    }
		
	

	private void showloginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// show login form
		RequestDispatcher dispatcher=request.getRequestDispatcher("login.jsp");
		// forward request and response to jsp page
		dispatcher.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//call doGet method
		doGet(request, response);
	}
	
	private void showNewForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		// show signup form
		RequestDispatcher dispatcher=request.getRequestDispatcher("customer-form.jsp");
		// forward request and response to jsp page
         dispatcher.forward(request, response);
	}
	private void showEditForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		//get Id from request
		int id=Integer.parseInt(request.getParameter("id"));
		//check customer is exist or not
		Customer existCustomer=customerdao.getCustomerById(id);
		//open customer-form page
		RequestDispatcher dispatcher=request.getRequestDispatcher("customer-form.jsp");
		//set customer object to request
		request.setAttribute("customer", existCustomer);
		// forward request and response to jsp page
        dispatcher.forward(request, response);
	}
	private void deleteCustomer(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	{
		int id=Integer.parseInt(request.getParameter("id"));
		customerdao.deleteCustomer(id);
		response.sendRedirect("list");
	}
	private void inserCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//get values form request
		String firstName=request.getParameter("firstName");
	     String lastName=request.getParameter("lastName");
	     String street=request.getParameter("street");
	     String address=request.getParameter("address");
	     String state=request.getParameter("state");
	     String city=request.getParameter("city");
	     String email=request.getParameter("email");
	     String password=request.getParameter("password");
	     String phone=request.getParameter("phone");
	     //load values in constructor
	     Customer customer=new Customer(firstName, lastName, street, address, state, city, email,password, phone,"");
	   
	    //insert values in database
	     customerdao.insertCustomer(customer);
	     //redirect to list
	     response.sendRedirect("list");

	}
	
	private void updateCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//get id from request
		int id=Integer.parseInt(request.getParameter("id"));
		//get other parameters from request
         String firstName=request.getParameter("firstName");
	     String lastName=request.getParameter("lastName");
	     String street=request.getParameter("street");
	     String address=request.getParameter("address");
	     String state=request.getParameter("state");
	     String city=request.getParameter("city");
	     String email=request.getParameter("email");
	     String password=request.getParameter("password");
	     String phone=request.getParameter("phone");
	     //load values in constructor 
	     Customer customer=new Customer(firstName, lastName, street, address, state, city, email,password, phone,"");
	     //update record by id
	     customerdao.updateCustomer(customer);
	     //redirect list
	     response.sendRedirect("list");

	}
	private void listCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		//get search parameter from request 
		String search = request.getParameter("search");
		//get searchBy parameter from request 
     String searchBy = request.getParameter("searchBy");
     //get record by search and searchBy parameter
	List<Customer> listCustomer=customerdao.getAllCustomers(search,searchBy);
	 // Set the total number of items
    int totalItems = listCustomer.size();
    request.setAttribute("totalItems", totalItems);

    // Calculate pagination parameters
    int itemsPerPage = 10;
    int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
    request.setAttribute("totalPages", totalPages);

    // Get the requested page parameter, default to page 1
    int currentPage = 1;
    String pageParam = request.getParameter("page");
    if (pageParam != null && !pageParam.isEmpty()) {
        currentPage = Integer.parseInt(pageParam);
        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPages) {
            currentPage = totalPages;
        }
    }
    request.setAttribute("currentPage", currentPage);

    // Calculate the start and end indices for the current page
    int startIndex = (currentPage - 1) * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

    // Extract the sublist for the current page
    List<Customer> currentPageItems = listCustomer.subList(startIndex, endIndex);
    request.setAttribute("listcustomer", currentPageItems);

 // Obtain a RequestDispatcher object by specifying the target resource (in this case, "list-customer.jsp")
RequestDispatcher dispatcher=request.getRequestDispatcher("list-customer.jsp");
// forward request and response to jsp page
dispatcher.forward(request, response);

	}
	private void veiwCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
	//get id from request
		int id=Integer.parseInt(request.getParameter("id"));
		//get record from id
	Customer customer=customerdao.getCustomerById(id);
	//set in request parameter
	request.setAttribute("customer", customer);
	 // Obtain a RequestDispatcher object by specifying the target resource (in this case, "list-customer.jsp")

	RequestDispatcher dispatcher=request.getRequestDispatcher("customer_by_id.jsp");
	// forward request and response to jsp page
    dispatcher.forward(request, response);

	}
}
