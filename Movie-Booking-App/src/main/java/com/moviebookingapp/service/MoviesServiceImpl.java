package com.moviebookingapp.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.moviebookingapp.model.*;
import com.moviebookingapp.repo.MoviesRepository;

@Service
public class MoviesServiceImpl implements MoviesService {

	final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private MoviesRepository moviesRepository;

	public String deleteMovie(String movieName) throws Exception {
		String response = null;
		// System.out.println(movieDetails.toString());
		try {
			List<Movies> moviesList = moviesRepository.findListOfMovieByMovieName(movieName);
			if (moviesList.size() == 0) {
				return "Not Found";
			}
			logger.debug("Movies Detail Deleted");
			moviesRepository.deleteById(movieName);
			response = "Movie Succesfully Deleted";

		} catch (Exception e) {
			throw new Exception(e);
		}
		return response;
	}

	public List<Movies> allMovie() throws Exception {
		List<Movies> response = null;

		try {

			logger.debug("All Movies Showed");
			response = moviesRepository.findAll();

		} catch (Exception e) {
			throw new Exception(e);
		}
		return response;
	}

	public ResponseEntity<?> addMovie(Movies movieDetails) throws Exception {
		ResponseEntity<?> response = null;
		// System.out.println(movieDetails.toString());
		try {

			logger.debug("Movies Detail Added");
			moviesRepository.save(movieDetails);
			response = new ResponseEntity(movieDetails, HttpStatus.OK);

		} catch (Exception e) {
			throw new Exception(e);
		}
		return response;
	}

	public List<Movies> searchMovie(String moviename) throws Exception {

		List<Movies> response = null;
		// List<Movies> moviesList;
		try {
			// System.out.println("yes");
			logger.debug("Movies Detail Added");
			List<Movies> moviesList = moviesRepository.findListOfMovieByMovieName(moviename);
			// System.out.println(moviesRepository.findListOfMovieByMovieName(moviename));
			if (moviesList.size() != 0) {
				response = moviesList;
			} else {
				// response=new ResponseEntity("No movie found with given name
				// "+moviename,HttpStatus.OK);
				throw new Exception();
			}

		} catch (Exception e) {
			throw new Exception(e);
		}
		return response;
	}

	public List<Movies> update(String moviename, int bookedtickets, String theaterName) throws Exception {
		List<Movies> response = new ArrayList<>();
		try {
			logger.debug("Movies Detail Added");
			Movies moviesList = moviesRepository.findMovieByMovieName(moviename);
			if (moviesList != null) {

				List<Theaters> theaterList = new ArrayList<>();

				System.out.println(bookedtickets);
				System.out.println(moviesList);
				for (Theaters th : moviesList.getTheaterName()) {
					// System.out.println(th.getSeats()+" "+(ticket.getNoOfSeats()));
					// System.out.println(th.getTheaterName()+" "+theaterName);
					if (th.getTheaterName().equals(theaterName)) {
						System.out.println(th);

						List<Integer> list = new ArrayList<>();

						th.setSeats(bookedtickets);

						response.add(moviesList);

					}
				}
			} else {
				// response=new ResponseEntity("No movie found with given name
				// "+moviename,HttpStatus.OK);
				return response;
			}
			// System.out.println(moviesList);
			moviesRepository.deleteById(moviename);
			moviesRepository.save(moviesList);

		} catch (Exception e) {
			throw new Exception();
		}
//		try {
//
//			Ticket ticketdetails;
//			logger.debug("Ticket Booked");
//			response = new ResponseEntity("hehe", HttpStatus.OK);
//
//		} catch (Exception e) {
//			throw new Exception(e);
//		}
		return response;
	}
}
