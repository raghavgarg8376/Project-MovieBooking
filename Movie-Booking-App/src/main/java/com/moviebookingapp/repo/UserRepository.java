package com.moviebookingapp.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.model.AuthResponse;
import com.moviebookingapp.model.User;

@Repository
public interface UserRepository extends MongoRepository<User,String>{


	@Query("{login:'?0'}")
	public User findUserByUsername(String login);
	
	@Query("{token:'?0'}")
	public User findByResetPasswordToken(String token);

	//public ResponseEntity<AuthResponse> validate(String token);

	//User findby(Query query, Class<User> class1);

	//Optional<User> getuserbyname(String username);

	//Optional<User> findByUsername(String login);
}
