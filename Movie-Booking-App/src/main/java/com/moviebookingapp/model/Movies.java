package com.moviebookingapp.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="movies")
public class Movies {
	@Id
	private String movieName;
	private List<Theaters> theaterName;
	
	
	
	public Movies() {
		super();
	}



	public String getMovieName() {
		return movieName;
	}



	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}



	public List<Theaters> getTheaterName() {
		return theaterName;
	}



	public void setTheaterName(List<Theaters> theaterName) {
		this.theaterName = theaterName;
	}



	public Movies(String movieName, List<Theaters> theaterName) {
		super();
		this.movieName = movieName;
		this.theaterName = theaterName;
	}



	@Override
	public String toString() {
		return "Movies [movieName=" + movieName + ", theaterName=" + theaterName + "]";
	}



	
	
	
	

}
