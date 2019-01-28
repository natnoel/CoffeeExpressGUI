/*
 CSCI213 Assignment 2
 ----------------------------------------------------------- 
 File name: Customer.java
 Author: Tan Shi Terng Leon
 Registration Number: 4000602
 Description: Customer class: A class to store a customer's information
 */

/**
 * @author Leon
 *
 */
public class Customer {

	private String name, username, password, address, contact;
	
	public Customer() {
		name = null;
		username = null;
		password = null;
		address = null;
		contact = null;
	}
	
	public Customer(String name, String username, String password, String address, String contact) {
		this.setName(name);
		this.setUsername(username);
		this.setPassword(password);
		this.setAddress(address);
		this.setContact(contact);
	}
	
	public Customer(Customer c) {
		name = c.name;
		username = c.username;
		password = c.password;
		address = c.address;
		contact = c.contact;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void reset() {
		name = null;
		username = null;
		password = null;
		address = null;
		contact = null;
	}

}
