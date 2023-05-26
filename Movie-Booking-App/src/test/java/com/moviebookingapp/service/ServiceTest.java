package com.moviebookingapp.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.moviebookingapp.model.AuthResponse;
import com.moviebookingapp.model.User;
import com.moviebookingapp.model.UserImpl;
import com.moviebookingapp.repo.UserRepository;
import com.moviebookingapp.util.JwtUtil;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

	@Mock
	UserRepository repo;

	@InjectMocks
	UserServiceImpl service;

	@MockBean
	JwtUtil jwtUtil;
	@InjectMocks
	CustomerUserDetailsService customerservice;
	@Mock
	CustomerUserDetailsService customersservice;

	@Test
	public void SaveModelTest() {
		User userModel = new User("raghav", "garg", "raghav@gmail.com", "raghav8376", "raghav123", "8490948453",
				"admin");
		User model2 = userModel;
		when(repo.save(userModel)).thenReturn(userModel);
		assertEquals(service.savemodel(userModel), model2);
	}
//	
//	@Test
//	public void validateTestInValidtoken() {
//		when(jwtUtil.isTokenExpired("token")).thenReturn(false);
//		ResponseEntity<?> validity = service.validate("bearer token");
//		assertEquals( false ,  validity.getBody().toString().contains("false") );
//	}

	@Test // for '/validate'
	public void validateJwt() {
		// instances req.
		String jwtTokenHeader = "Bearer jj.ww.tt";
		AuthResponse authenticationResponse = null;
		ResponseEntity<AuthResponse> response = null;
		UserImpl projectManagerDetails = null;
		User projectManager = null;

		// making User
		projectManager = new User();
		projectManager.setFirstName("raghav");
		projectManager.setLastName("garg");
		projectManager.setEmail("raghav@gmail.com");
		projectManager.setLogin("raghav8376");
		projectManager.setContactNumber("4817489173");
		projectManager.setRole("admin");
		projectManager.setPassword("raghav123");

		// making ProjectManagerDetails
		projectManagerDetails = new UserImpl(projectManager);

		// making authentication-response
		authenticationResponse = new AuthResponse("raghav8376", true);

		// first remove Bearer from Header
		String jwtToken = jwtTokenHeader.substring(7);
		String username = "raghav8376";
		response = new ResponseEntity<AuthResponse>(authenticationResponse, HttpStatus.OK);

		// check the jwt is proper or not - success
		when(jwtUtil.extractUsername(jwtToken)).thenReturn(username);
		// when(customerservice.loadUserByUsername(username))
		// .thenReturn(projectManagerDetails);
		when(jwtUtil.validateToken(jwtToken, projectManagerDetails)).thenReturn(true); // correct
		// assertEquals(response, service.validate(jwtTokenHeader));

		// now for wrong
		jwtTokenHeader = "Bearer jj.wrong.tt";

		// making projectManager
		projectManager = null;

		// making ProjectManagerDetails
		projectManagerDetails = null;

		// making authentication-response
		authenticationResponse = new AuthResponse("Invalid", false);
		username = "";

		// first remove Bearer from Header
		jwtToken = jwtTokenHeader.substring(7);
		response = new ResponseEntity<AuthResponse>(authenticationResponse, HttpStatus.OK);

		// check the jwt is proper or not - success
		when(jwtUtil.extractUsername(jwtToken)).thenReturn(username);
		// when(customerservice.loadUserByUsername(username)).thenReturn(projectManagerDetails);
		when(jwtUtil.validateToken(jwtToken, projectManagerDetails)).thenReturn(false); // wrong
		// assertEquals(response, service.validate(jwtTokenHeader));
	}

	@Test
	void testValidate() throws Exception {
		String jwt = "Bearer Token";
		String username = "raghav";
		AuthResponse expectedResponse = new AuthResponse(username, true);
		when(jwtUtil.extractUsername("Token")).thenReturn(username);
		when(jwtUtil.validateToken("Token", customersservice.loadUserByUsername(username))).thenReturn(true);

		ResponseEntity<AuthResponse> result = service.validate(jwt);

		assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	@Test
	void testLoadUserByUsername() {
		String username = "raghav";
		UserImpl expectedUser = new UserImpl(new User(username, "password"));

		when(repo.findUserByUsername(username)).thenReturn(new User(username, "password"));

		UserImpl result = customerservice.loadUserByUsername(username);

		assertEquals(expectedUser.getUsername(), result.getUsername());
	}
}
