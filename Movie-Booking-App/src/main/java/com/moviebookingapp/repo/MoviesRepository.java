package com.moviebookingapp.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.model.Movies;



@Repository
public interface MoviesRepository extends MongoRepository<Movies,String>{

	@Query("{movieName:'?0'}")
	public Movies findMovieByMovieName(String moviename);

//	@Query("{movieName:{theaterName:{theaterName:'?0'}} }")
//	public List<Theaters> findTheaterByTheaterName(String theaterName);

	@Query("{movieName:'?0'}")
	public List<Movies> findListOfMovieByMovieName(String moviename);
}
