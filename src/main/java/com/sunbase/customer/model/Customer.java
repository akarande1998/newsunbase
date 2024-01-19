package com.sunbase.customer.model;

public class Customer {
	private int id;
    private String firstName;
    private String lastName;
    private String street;
    private String address;
    private String state;
    private String city;
    private String email;
    private String password;
    private String phone;
    private String uuid;

	public Customer() {
		super();
		
	}
	public Customer(String firstName, String lastName, String street, String address, String state, String city,
			String email,String password,  String phone,  String uuid) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.address = address;
		this.state = state;
		this.city = city;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.uuid = uuid;
	}
	public Customer(int id, String firstName, String lastName, String street, String address, String state, String city,
			String email,String password, String phone,  String uuid) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.address = address;
		this.state = state;
		this.city = city;
		this.password = password;
        this.email = email;
		this.phone = phone;
		this.uuid = uuid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		this.password = password;
	}
}
