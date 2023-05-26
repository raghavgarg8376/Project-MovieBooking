package com.moviebookingapp.service;

import java.util.List;

import com.moviebookingapp.model.Ticket;

public interface TicketService {

	public List<Ticket> bookTicketByUser(Ticket ticket) throws Exception;

}
