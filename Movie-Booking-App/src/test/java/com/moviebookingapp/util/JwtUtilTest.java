package com.moviebookingapp.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.moviebookingapp.model.User;
import com.moviebookingapp.model.UserImpl;
import com.moviebookingapp.repo.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtUtilTest {

	UserImpl userdetails;
	@InjectMocks
	JwtUtil jwtUtil;

	@Mock
	UserRepository repo;

	@Test
	public void generateTokenTest() {
		userdetails = new UserImpl("admin", "admin");
		String generateToken = jwtUtil.generateToken(userdetails);
		assertNotNull(generateToken);

	}

	@Test
	public void validateTokenTest() {

		userdetails = new UserImpl("admin", "admin");
		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.isTokenExpired(generateToken);
		assertEquals(false, validateToken);

	}

	@Test
	public void validateTokenWithNameTest() {

		userdetails = new UserImpl("admin", "admin");

		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.validateToken(generateToken, userdetails);
		assertEquals(true, validateToken);

	}

	@Test
	public void validateTokenWithNameFalseTest() {

		userdetails = new UserImpl("admin", "admin");
		UserImpl user1 = new UserImpl("admin1", "admin1");
		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.validateToken(generateToken, user1);
		assertEquals(false, validateToken);
	}
}
