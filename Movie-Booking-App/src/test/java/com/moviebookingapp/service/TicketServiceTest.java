package com.moviebookingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;

import com.moviebookingapp.model.Movies;
import com.moviebookingapp.model.Theaters;
import com.moviebookingapp.model.Ticket;
import com.moviebookingapp.repo.MoviesRepository;
import com.moviebookingapp.repo.TicketRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

	@Mock
	MoviesRepository repo;
	@Mock
	TicketRepository ticketrepo;

	@InjectMocks
	TicketServiceImpl ticketservice;

	@InjectMocks
	MoviesServiceImpl service;

	@Test
	public void bookTicketByUser() throws Exception {

		List<Movies> movielist = new ArrayList<>();
		Movies m1 = new Movies();
		Theaters t1 = new Theaters();
		List<Theaters> theatersList = new ArrayList<>();

		t1.setBookedSeats(10);
		t1.setReleaseDate("22-09-2022");
		t1.setSeats(22);
		t1.setSlots("4-7");
		t1.setTheaterName("PVR");

		theatersList.add(t1);

		m1.setMovieName("John");
		m1.setTheaterName(theatersList);
		movielist.add(m1);
		service.addMovie(m1);
		// System.out.println(repo.findAll());
		when(repo.findAll()).thenReturn(movielist);
		assertEquals(service.allMovie(), movielist);
		// System.out.println(repo.findAll());
		Ticket ticket = new Ticket();
		ticket.setUserName("raghav123");
		ticket.setBookingForDate("22-05-2022");
		ticket.setTheaterName("PVR");
		ticket.setTimeSlot("4-7");
		ticket.setNoOfSeats(2);
		ticket.setMovieName("John");
		List<Ticket> tickets = new ArrayList<>();
		tickets.add(ticket);
		when(repo.findMovieByMovieName("John")).thenReturn(m1);
		assertEquals(tickets, ticketservice.bookTicketByUser(ticket));
		assertEquals("raghav123", ticketservice.bookTicketByUser(ticket).get(0).getUserName());
		assertEquals(new Date(), ticketservice.bookTicketByUser(ticket).get(0).getBookedOnDate());
		assertEquals("4-7", ticketservice.bookTicketByUser(ticket).get(0).getTimeSlot());
		assertEquals("[18, 19]", ticketservice.bookTicketByUser(ticket).get(0).getSeatNumber().toString());
		List<Integer> seats = new ArrayList<>();
		seats.add(8);
		seats.add(9);
		assertEquals("22-05-2022", ticketservice.bookTicketByUser(ticket).get(0).getBookingForDate());
		Ticket ticket1 = new Ticket("raghav123", new Date(), "22-02-2023", "John", "4-7", "PVR", 2, seats);
		assertEquals("Ticket [userName=raghav123, bookedOnDate=" + new Date() + ", "
				+ "bookingForDate=22-02-2023, movieName=John, timeSlot=4-7, theaterName=PVR, "
				+ "noOfSeats=2, seatNumber=[8, 9]]", ticket1.toString());
	}

}
