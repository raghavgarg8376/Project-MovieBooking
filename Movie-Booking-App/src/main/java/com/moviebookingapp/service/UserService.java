package com.moviebookingapp.service;

import org.springframework.http.ResponseEntity;

import com.moviebookingapp.model.AuthResponse;
import com.moviebookingapp.model.User;

public interface UserService {

	public User savemodel(User user);

//	public List<User> getUsers();
//
//	public List<User> deleteUsers();

	public ResponseEntity<AuthResponse> validate(String token);

	public ResponseEntity<?> forgotuser(String username);

	public ResponseEntity<?> resetPassword(String token, String password);

}
