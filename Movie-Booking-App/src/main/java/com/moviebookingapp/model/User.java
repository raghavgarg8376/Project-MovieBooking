package com.moviebookingapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Document(collection="userdetails")
public class User {
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@Email
	//@Id
	@Indexed(unique=true)
	@JsonProperty("email")
	private String email;
	
	@Id
	@Indexed(unique=true)
	@JsonProperty("login")
	private String login;
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("contactNumber")
	private String contactNumber;
	@JsonProperty("role")
	private String role="User";
	@JsonProperty("token")
	private String token;
	@JsonProperty("tokenCreationTime")
	private LocalDateTime tokenCreationDate;
	
	
	
	public User(String login, String password) {
		super();
		this.login = login;
		this.password = password;
	}

	public User(String firstName, String lastName, @Email String email, String login, String password,
			String contactNumber, String role, String token) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.login = login;
		this.password = password;
		this.contactNumber = contactNumber;
		this.role = role;
		this.token = token;
	}

	public User() {
		super();
	}

	public User(String firstname, String lastname, @Email String email, String login, String password,
			@Size(min = 10) String contactnumber) {
		super();
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.login = login;
		this.password = password;
		this.contactNumber = contactnumber;
		this.role="user";
		//this.token=UUID.randomUUID().toString();
	}
	
	public User(String firstname, String lastname, @Email String email, String login, String password,
			String contactnumber, String role) {
		super();
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.login = login;
		this.password = password;
		this.contactNumber = contactnumber;
		this.role = role;
		//this.token=UUID.randomUUID().toString();
	}
	
	


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		role = role;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactnumber) {
		this.contactNumber = contactnumber;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getTokenCreationDate() {
		return tokenCreationDate;
	}

	public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
		this.tokenCreationDate = tokenCreationDate;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", login=" + login
				+ ", password=" + password + ", contactNumber=" + contactNumber + ", Role=" + role + ", token=" + token
				+ "]";
	}
	
	
	

}

