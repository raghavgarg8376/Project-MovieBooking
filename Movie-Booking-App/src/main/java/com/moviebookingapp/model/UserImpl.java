package com.moviebookingapp.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserImpl implements UserDetails{

	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String password;
	private String contactnumber;
	private String token;
	
	public UserImpl() {
		
	}
	


	public UserImpl(User user) {
		
		this.firstname = user.getFirstName();
		this.lastname = user.getLastName();
		this.email = user.getEmail();
		this.username = user.getLogin();
		this.password = new BCryptPasswordEncoder(10).encode(user.getPassword());
		this.contactnumber = user.getContactNumber();
		this.token=user.getToken();
	}


	


	public UserImpl(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}



	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return new ArrayList();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void setPassword(String password) {
		this.password = password;
	}

}
