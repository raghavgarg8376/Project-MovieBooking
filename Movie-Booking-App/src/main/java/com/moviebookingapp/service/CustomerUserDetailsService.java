package com.moviebookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Exception.UserNotFound;
import com.moviebookingapp.model.*;
import com.moviebookingapp.repo.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository repository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService user;

	@Override
	public UserImpl loadUserByUsername(String username) throws UsernameNotFoundException {
		UserImpl user = null;
		User user1 = null;
		user1 = userData(username);
		if (user1 != null) {
			user = new UserImpl(userData(username));
		}

		return user;

	}

	public void authenticate(String userName, String password) throws BadCredentialsException, DisabledException {
		// TODO Auto-generated method stub
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		} catch (DisabledException e) {
			throw new DisabledException("user is disabled");
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Invalid Credentials");
		}

	}

	public User userData(String username) throws UserNotFound {
		User user = null;
		user = repository.findUserByUsername(username);
		if (user == null) {
			throw new UserNotFound("GIVEN USERNAME DOES NOT EXIST IN DATABASE");
		}
		return user;
	}

	public boolean getValidity(String token) {
		AuthResponse authenticationResponse = user.validate(token).getBody();
		System.out.println(authenticationResponse.getUid());
		return authenticationResponse.isValid();
	}

}
