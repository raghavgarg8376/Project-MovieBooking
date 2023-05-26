package com.moviebookingapp.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.moviebookingapp.Exception.UserNotFound;
import com.moviebookingapp.model.*;
import com.moviebookingapp.repo.UserRepository;
import com.moviebookingapp.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

	final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	@Autowired
	private UserRepository userRespository;

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;

//	public List<User> getUsers(){
//		List<User> users=userRespository.findAll();
//		return  users;
//	}
//	
//	public List<User> deleteUsers(){
//		userRespository.deleteAll();
//		return userRespository.findAll();
//	}

	public ResponseEntity<?> forgotuser(String username) throws UserNotFound {
		ResponseEntity<?> response = null;
		User user = userRespository.findUserByUsername(username);
		if (user == null) {
			response = new ResponseEntity(new UserNotFound("User Not Found"), HttpStatus.FORBIDDEN);
			return response;
			// return new UserNotFound("User Not Found");
		}
		user.setTokenCreationDate(LocalDateTime.now());
		user.setToken(generateToken());
		response = new ResponseEntity<>("http://localhost:8082/reset-password?token=" + user.getToken(),
				HttpStatus.ACCEPTED);
		// sendmail(user.getEmail(),"Password Reset","clcik the following
		// link:"+"http://localhost:8082/reset-password?token=" +user.getToken());
		userRespository.delete(user);
		userRespository.save(user);
		return response;
	}

//	private void sendmail(String email, String string, String string2) {
//		// TODO Auto-generated method stub
//		SimpleMailMessage msg=new SimpleMailMessage();
//		msg.setTo(email);
//		msg.setSubject(string);
//		msg.setText(string2);
//		javaMailSender.send(msg);
//		
//	}
	public ResponseEntity<?> resetPassword(String token, String password) {

		Optional<User> userOptional = Optional.ofNullable(userRespository.findByResetPasswordToken(token));
		ResponseEntity<?> response = null;
		User user = userRespository.findByResetPasswordToken(token);
		if (user == null) {
			response = new ResponseEntity(new UserNotFound("User Not Found"), HttpStatus.FORBIDDEN);
			return response;
			// return new UserNotFound("User Not Found");
		}
		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();
		if (isTokenExpired(tokenCreationDate)) {
			response = new ResponseEntity(new UserNotFound("Token Expired"), HttpStatus.FORBIDDEN);
			return response;

		}
		User user1 = userOptional.get();

		user1.setPassword(password);
		user1.setToken(null);
		user1.setTokenCreationDate(null);

		userRespository.save(user1);
		return response;

	}

	public User savemodel(User user) {
		User response = null;
		String username = user.getLogin();
		User user2 = userRespository.findUserByUsername(username);
		if (user2 != null) {
			// response=new ResponseEntity("Username already exist, Please use new
			// username",HttpStatus.FORBIDDEN);
			return response;
		} else {
			logger.debug("User Saved");
			userRespository.save(user);
			// System.out.println(user);
			// System.out.println(userRespository.findAll());
			response = user;

			return response;
		}
	}

	public ResponseEntity<AuthResponse> validate(String jwt) {
		AuthResponse authenticationResponse = new AuthResponse("Invalid", false);
		ResponseEntity<AuthResponse> response = null;

		// first remove Bearer from Header
		jwt = jwt.substring(7);

		// check token
		logger.info("--------JWT :: " + jwt);

		// check the jwt is proper or not
		UserImpl userDetails = customerUserDetailsService.loadUserByUsername(jwtUtil.extractUsername(jwt));

		// now validating the jwt
		try {
			if (jwtUtil.validateToken(jwt, userDetails)) {
				authenticationResponse.setUid(userDetails.getUsername());
				authenticationResponse.setValid(true);
				response = new ResponseEntity<AuthResponse>(authenticationResponse, HttpStatus.OK);
				logger.info("Successfully validated the jwt and sending response back!");
			} else {
				response = new ResponseEntity<AuthResponse>(authenticationResponse, HttpStatus.OK);
				logger.error("JWT Token validation failed!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response = new ResponseEntity<AuthResponse>(authenticationResponse, HttpStatus.OK);
			logger.error("Exception occured whil validating JWT : Exception info : " + e.getMessage());
		}
		logger.info("-------- Exiting /validate");
		return response;
	}

	/**
	 * Generate unique token. You may add multiple parameters to create a strong
	 * token.
	 * 
	 * @return unique token
	 */
	private String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
	}

	/**
	 * Check whether the created token expired or not.
	 * 
	 * @param tokenCreationDate
	 * @return true or false
	 */

	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}
}
