package com.moviebookingapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.model.*;
import com.moviebookingapp.service.*;
import com.moviebookingapp.util.JwtUtil;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class Controller {

	@Autowired
	// @Lazy
	private UserService service;

	@Autowired
	// @Lazy
	private MoviesService moviesService;

	@Autowired
	// @Lazy
	private TicketService ticketService;

	@Autowired
	private CustomerUserDetailsService userservice;

	final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private JwtUtil jwtUtil;

	// User Related API

	@PostMapping("/register")
	public ResponseEntity<?> adduser(@RequestBody User user) throws Exception {

		ResponseEntity<?> response = null;
		try {
			User us = service.savemodel(user);
			if (us == null) {
				return response = new ResponseEntity<String>("Username is already registered", HttpStatus.OK);
			}
			return ResponseEntity.ok(us);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	@GetMapping("/{username}/forgot")
	public ResponseEntity<?> forgotpassword(@PathVariable(required = true) String username) {

		ResponseEntity<?> response = service.forgotuser(username);

		return response;
	}

	@PutMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String password) {

		return service.resetPassword(token, password);
	}

//	@GetMapping("/users")
//	public List<User> usersAll(){
//		return service.getUsers();
//	}
//	
//	@DeleteMapping("/users")
//	public List<User> delete(){
//		return service.deleteUsers();
//	}

	@PostMapping("/login")
	public ResponseEntity<String> verifylogin(@RequestBody User jwtRequest) {

		ResponseEntity<String> response = null;
		try {
			userservice.authenticate(jwtRequest.getLogin(), jwtRequest.getPassword());
			final UserImpl userdetails = userservice.loadUserByUsername(jwtRequest.getLogin());

			String jwt = jwtUtil.generateToken(userdetails);
			logger.info("Authenticated User :: " + userdetails);
			response = new ResponseEntity<String>(jwt, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage() + "!! info about request-body : " + jwtRequest);
			response = new ResponseEntity<String>("Not Authorized", HttpStatus.OK);

		}
		logger.info("-------- Exiting /authenticate");
		return response;
	}

	// Movie Related API
	@PostMapping("/addmovie")
	public ResponseEntity<?> addMovie(@RequestHeader("Authorization") String token, @RequestBody Movies movieDetails)
			throws Exception {

		try {
			return moviesService.addMovie(movieDetails);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}

	@DeleteMapping("/{moviename}/delete")
	public String deletemovie(@RequestHeader("Authorization") String token,
			@PathVariable(required = true) String moviename) throws Exception {

		try {
			if (null != moviename) {
				return moviesService.deleteMovie(moviename);
			} else {
				throw new Exception("Error in Movie Name");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}

	@GetMapping("/all")
	public List<Movies> movieall() throws Exception {
		try {
			return moviesService.allMovie();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}

	@GetMapping("/movies/search/{moviename}")
	public List<Movies> searchmoviebyname(@PathVariable(required = true) String moviename) throws Exception {

		try {
			return moviesService.searchMovie(moviename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}

	@PostMapping("/add")
	public boolean bookticket(@RequestHeader("Authorization") String token, @RequestBody Ticket ticket)
			throws Exception {
		ResponseEntity<?> response = null;
		String moviename = ticket.getMovieName();

		List<Ticket> Bookedticket = new ArrayList<>();
		try {

			if (token.length() > 0 && userservice.getValidity(token)) {

				ticket.setUserName(service.validate(token).getBody().getUid());
				ticket.setMovieName(moviename);
				Bookedticket = ticketService.bookTicketByUser(ticket);

				response = new ResponseEntity<List<Ticket>>(Bookedticket, HttpStatus.OK);
			} else {
				logger.error("Failed to validate the JWT :: " + token);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
		return true;
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateticketstatus(@RequestHeader("Authorization") String token,
			@RequestBody Ticket ticket) throws Exception {
		ResponseEntity<?> response = null;
		try {

			if (token.length() > 0 && userservice.getValidity(token)) {
				response = ResponseEntity.ok(this.moviesService.update(ticket.getMovieName(), ticket.getNoOfSeats(),
						ticket.getTheaterName()));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
		return response;
	}

}
