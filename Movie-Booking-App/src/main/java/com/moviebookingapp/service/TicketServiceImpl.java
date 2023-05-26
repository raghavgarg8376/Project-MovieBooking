package com.moviebookingapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.moviebookingapp.model.*;
import com.moviebookingapp.repo.MoviesRepository;
import com.moviebookingapp.repo.TicketRepository;

@Service
public class TicketServiceImpl implements TicketService {

	final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private MoviesRepository moviesRepository;

	public List<Ticket> bookTicketByUser(Ticket ticket) throws Exception {
		List<Ticket> ticketbooked = new ArrayList<>();
		List<Ticket> response = new ArrayList<Ticket>();
		ticket.setBookedOnDate(new Date());
		try {
			logger.debug("Movies Detail Added");

			Movies moviesList = moviesRepository.findMovieByMovieName(ticket.getMovieName());

			if (moviesList != null) {

				List<Theaters> theaterList = new ArrayList<>();

				for (Theaters th : moviesList.getTheaterName()) {

					if (th.getTheaterName().equals(ticket.getTheaterName())
							&& (th.getSeats() - th.getBookedSeats()) >= ticket.getNoOfSeats()) {

						List<Integer> list = new ArrayList<>();

						for (int i = th.getBookedSeats(); i < th.getBookedSeats() + ticket.getNoOfSeats(); i++) {
							list.add(i);
						}
						th.setBookedSeats(th.getBookedSeats() + ticket.getNoOfSeats());
						ticket.setSeatNumber(list);
						response.add(ticket);
						ticketRepository.save(ticket);
					}
				}
			}
			moviesRepository.deleteById(ticket.getMovieName());
			moviesRepository.save(moviesList);

		} catch (Exception e) {
			throw new Exception(e);
		}
		System.out.println(response);
		return response;
	}
}
