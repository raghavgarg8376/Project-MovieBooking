package com.moviebookingapp.model;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tickets")
public class Ticket {
	
	
	private String userName;
	private Date bookedOnDate;
	private String bookingForDate;
	private String movieName;
	private String timeSlot;
	private String theaterName;
	private int noOfSeats;
	private List<Integer> seatNumber;
	
	
	public Ticket() {
		super();
	}
	public Ticket(String userName, Date bookedOnDate, String bookingForDate, String movieName, String timeSlot,
			String theaterName, int noOfSeats, List<Integer> seatNumber) {
		super();
		this.userName = userName;
		this.bookedOnDate = bookedOnDate;
		this.bookingForDate = bookingForDate;
		this.movieName = movieName;
		this.timeSlot = timeSlot;
		this.theaterName = theaterName;
		this.noOfSeats = noOfSeats;
		this.seatNumber = seatNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getBookedOnDate() {
		return bookedOnDate;
	}
	public void setBookedOnDate(Date bookedOnDate) {
		this.bookedOnDate = bookedOnDate;
	}
	public String getBookingForDate() {
		return bookingForDate;
	}
	public void setBookingForDate(String bookingForDate) {
		this.bookingForDate = bookingForDate;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public int getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
	public List<Integer> getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(List<Integer> seatNumber) {
		this.seatNumber = seatNumber;
	}
	@Override
	public String toString() {
		return "Ticket [userName=" + userName + ", bookedOnDate=" + bookedOnDate + ", bookingForDate=" + bookingForDate
				+ ", movieName=" + movieName + ", timeSlot=" + timeSlot + ", theaterName=" + theaterName
				+ ", noOfSeats=" + noOfSeats + ", seatNumber=" + seatNumber + "]";
	}
	
	
	
	
	
}
