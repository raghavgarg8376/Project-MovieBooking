package com.moviebookingapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.moviebookingapp.model.Movies;
import com.moviebookingapp.model.Theaters;
import com.moviebookingapp.repo.MoviesRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MoviesServiceTest {

	@Mock
	MoviesRepository repo;

	@InjectMocks
	MoviesServiceImpl service;

	@Test
	public void testGetAllMovies() throws Exception {

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
		assertEquals(t1.getReleaseDate(), "22-09-2022");
		assertEquals(t1.getSlots(), "4-7");
	}

	@Test
	public void searchMovie() throws Exception {
		List<Movies> movielist = new ArrayList<>();

		Theaters t1 = new Theaters("PVR", 22, "4-7", 10, "22-09-2022");
		List<Theaters> theatersList = new ArrayList<>();

//		t1.setBookedSeats(10);
//		t1.setReleaseDate("22-09-2022");
//		t1.setSeats(22);
//		t1.setSlots("4-7");
//		t1.setTheaterName("PVR");

		theatersList.add(t1);
		Movies m1 = new Movies("John", theatersList);
		movielist.add(m1);
		service.addMovie(m1);

		t1.setBookedSeats(10);
		t1.setReleaseDate("22-09-2022");
		t1.setSeats(22);
		t1.setSlots("4-7");
		t1.setTheaterName("PVR");

		theatersList.add(t1);

		m1.setMovieName("Johny");
		m1.setTheaterName(theatersList);
		movielist.add(m1);
		service.addMovie(m1);
		when(repo.findListOfMovieByMovieName("John")).thenReturn(movielist);
		assertEquals(service.searchMovie("John"), movielist);

	}

	@Test
	public void DeleteMovie() throws Exception {
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
		// service.deleteMovie("John", "admin");
		// when(repo.findListOfMovieByMovieName("John")).thenReturn(movielist);
		assertEquals(service.deleteMovie("John"), "Not Found");
		// assertEquals(service.searchMovie("John"), null);

	}

	@Test
	public void DeleteMovieByUser() throws Exception {
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
		// service.deleteMovie("John", "admin");
		// Mockito.when(repo.delete(m1)).thenReturn(movielist);
		assertEquals(service.deleteMovie("John"), "Not Found");
		// assertEquals(service.searchMovie("John"), null);

	}

	@Test
	public void UpdateMoviewhichIsNotinDatabase() throws Exception {
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
		// service.deleteMovie("John", "admin");
		// when(repo.findListOfMovieByMovieName("John")).thenReturn(movielist);
		assertEquals(service.deleteMovie("John"), "Not Found");
		// assertEquals(service.searchMovie("John"), null);

	}

	@Test
	public void UpdateMovie() throws Exception {
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
		// service.deleteMovie("John", "admin");
		// when(repo.saveAll(movielist)).thenReturn(movielist);
		when(repo.findMovieByMovieName(m1.getMovieName())).thenReturn(m1);
		// when(repo.findAll()).thenReturn(movielist);
		// assertEquals(service.update(null, 0, null),movielist);

		assertEquals(service.update("John", 4, "PVR"), movielist);
		// assertEquals(service.searchMovie("John"), null);

	}

}
