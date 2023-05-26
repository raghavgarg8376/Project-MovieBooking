package com.moviebookingapp.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Theaters {
	@Id
	private String theaterName;
	private int seats;
	private String slots;
	private int bookedSeats;
	private String releaseDate;
	public int getBookedSeats() {
		return bookedSeats;
	}
	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}
	
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	public String getSlots() {
		return slots;
	}
	public void setSlots(String slots) {
		this.slots = slots;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
	public Theaters(String theaterName, int seats, String slots, int bookedSeats, String releaseDate) {
		super();
		this.theaterName = theaterName;
		this.seats = seats;
		this.slots = slots;
		this.bookedSeats = bookedSeats;
		this.releaseDate = releaseDate;
	}
	public Theaters() {
		super();
	}
	@Override
	public String toString() {
		return "Theaters [theaterName=" + theaterName + ", seats=" + seats + ", slots=" + slots + ", bookedSeats="
				+ bookedSeats + ", releaseDate=" + releaseDate + "]";
	}
	

	
}
