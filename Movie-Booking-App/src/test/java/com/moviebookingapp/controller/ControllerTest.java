package com.moviebookingapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.moviebookingapp.model.Movies;
import com.moviebookingapp.model.Theaters;
import com.moviebookingapp.model.User;
import com.moviebookingapp.model.UserImpl;
import com.moviebookingapp.repo.UserRepository;
import com.moviebookingapp.service.*;
import com.moviebookingapp.util.JwtUtil;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ControllerTest {

	@InjectMocks
	Controller controller;

	@MockBean
	UserService service;

	@Mock
	MoviesService moviesService;

	@Mock
	TicketService ticketService;

	@Mock
	CustomerUserDetailsService customerUserDeatilsService;

	@Mock
	JwtUtil jwtUtil;

	@Test
	void registerIndexTest() throws Exception {
		User model1;
		User model = new User("Raghav", "Garg", "raghavgarg8376@gmail.com", "raghav123", "password", "5738275915");
		service.savemodel(model);
//		model1=controller.adduser(model);
//		assertEquals(model1, model);
	}

	@Test
	void testSearchMovieByMovieName() throws Exception {
		String movieName = "John 1";
		Theaters t1 = new Theaters("PVR", 22, "4-7", 10, "22-09-2022");
		List<Theaters> theatersList = new ArrayList<>();

		theatersList.add(t1);
		List<Movies> expectedMovies = new ArrayList<>();
		expectedMovies.add(new Movies("John 1", theatersList));
		expectedMovies.add(new Movies("John 2", theatersList));
		when(moviesService.searchMovie(movieName)).thenReturn(expectedMovies);
		List<Movies> result = controller.searchmoviebyname(movieName);

		assertEquals(expectedMovies, result);
		verify(moviesService).searchMovie(movieName);
	}

	@Test
	void registerindexError() {
		when(service.savemodel(any(User.class))).thenThrow(new RuntimeException());
		assertThrows(Exception.class, () -> controller.adduser(new User()));
	}

	@Test
	void testVerifyLoginSuccess() {
		User jwtRequest = new User("raghav", "raghav");
		UserImpl userDetails = new UserImpl(jwtRequest.getLogin(), jwtRequest.getPassword());
		String jwttoken = "token";
		ResponseEntity<String> expectedResponse = ResponseEntity.ok(jwttoken);
		// when(customerUserDeatilsService.authenticate(jwtRequest.getLogin(),jwtRequest.getPassword()));
		when(customerUserDeatilsService.loadUserByUsername(jwtRequest.getLogin())).thenReturn(userDetails);
		when(jwtUtil.generateToken(userDetails)).thenReturn(jwttoken);

		ResponseEntity<?> result = controller.verifylogin(jwtRequest);

		assertEquals(expectedResponse, result);
		verify(customerUserDeatilsService).loadUserByUsername(jwtRequest.getLogin());
		verify(jwtUtil).generateToken(userDetails);

	}

}
