package com.moviebookingapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.moviebookingapp.model.Movies;


public interface MoviesService {

	public ResponseEntity<?> addMovie(Movies movieDetails) throws Exception;

	public String deleteMovie(String moviename) throws Exception;

	public List<Movies> allMovie() throws Exception;

	public List<Movies> searchMovie(String moviename) throws Exception;

	public List<Movies> update(String movieName,int bookedTicket, String theaterName) throws Exception;

}
