package com.example.moviebappbackend;

import com.example.moviebappbackend.exception.ScreeningFullException;
import com.example.moviebappbackend.movie.Movie;
import com.example.moviebappbackend.movie.MovieRepository;
import com.example.moviebappbackend.screening.CreateScreeningDTO;
import com.example.moviebappbackend.screening.Screening;
import com.example.moviebappbackend.screening.ScreeningRepository;
import com.example.moviebappbackend.screening.ScreeningServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class ScreeningServiceImplTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ScreeningServiceImpl screeningService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testGetAllScreenings() {
        List<Screening> screenings = new ArrayList<>();
        screenings.add(new Screening());
        screenings.add(new Screening());

        when(screeningRepository.findAll()).thenReturn(screenings);

        List<Screening> allScreenings = screeningService.getAllScreenings();
        assertNotNull(allScreenings);
        assertEquals(2, allScreenings.size());

        verify(screeningRepository, times(1)).findAll();
    }

    @Test
     void testGetScreeningById() {
        Long screeningId = 1L;
        Screening screening = new Screening();
        screening.setId(screeningId);
        screening.setMovie(new Movie());
        screening.setScreen("Screen 1");
        screening.setSeatsAvailable(100);

        when(screeningRepository.findById(screeningId)).thenReturn(Optional.of(screening));

        Screening foundScreening = screeningService.getScreeningById(screeningId);
        assertNotNull(foundScreening);
        assertEquals(screeningId, foundScreening.getId());

        verify(screeningRepository, times(1)).findById(screeningId);
    }

    @Test
     void testGetScreeningByIdNotFound() {
        Long screeningId = 1L;

        when(screeningRepository.findById(screeningId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> screeningService.getScreeningById(screeningId));

        verify(screeningRepository, times(1)).findById(screeningId);
    }

    @Test
     void testCreateScreening() {
        CreateScreeningDTO screeningDTO = new CreateScreeningDTO();
        screeningDTO.setMovieTitle("Test Movie");
        screeningDTO.setScreen("Screen 1");
        screeningDTO.setSeatsAvailable(100);
        LocalDateTime dateTime = LocalDateTime.now();

        screeningDTO.setDateAndTime(dateTime);

        Movie movie = new Movie();
        movie.setTitle("Test Movie");

        when(movieRepository.findByTitle("Test Movie")).thenReturn(movie);
        when(screeningRepository.save(any(Screening.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Screening createdScreening = screeningService.createScreening(screeningDTO);
        assertNotNull(createdScreening);
        assertEquals("Test Movie", createdScreening.getMovie().getTitle());
        assertEquals("Screen 1", createdScreening.getScreen());
        assertEquals(100, createdScreening.getSeatsAvailable());

        verify(movieRepository, times(1)).findByTitle("Test Movie");
        verify(screeningRepository, times(1)).save(any(Screening.class));
    }

    @Test
     void testCreateScreeningMovieNotFound() {
        CreateScreeningDTO screeningDTO = new CreateScreeningDTO();
        screeningDTO.setMovieTitle("Test Movie");

        when(movieRepository.findByTitle("Test Movie")).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> screeningService.createScreening(screeningDTO));

        verify(movieRepository, times(1)).findByTitle("Test Movie");
        verify(screeningRepository, never()).save(any(Screening.class));
    }

    @Test
    public void testUpdateScreening() {
        Long screeningId = 1L;
        Screening updatedScreening = new Screening();
        updatedScreening.setMovie(new Movie());
        updatedScreening.setScreen("Updated Screen");
        updatedScreening.setSeatsAvailable(50);

        Screening existingScreening = new Screening();
        existingScreening.setId(screeningId);
        existingScreening.setMovie(new Movie());
        existingScreening.setScreen("Screen 1");
        existingScreening.setSeatsAvailable(100);

        when(screeningRepository.findById(screeningId)).thenReturn(Optional.of(existingScreening));
        when(screeningRepository.save(existingScreening)).thenReturn(existingScreening);

        Screening updated = screeningService.updateScreening(screeningId, updatedScreening);

        assertNotNull(updated);
        assertEquals(screeningId, updated.getId());
        assertEquals("Updated Screen", updated.getScreen());
        assertEquals(50, updated.getSeatsAvailable());

        verify(screeningRepository, times(1)).findById(screeningId);
        verify(screeningRepository, times(1)).save(existingScreening);
    }

    @Test
     void testUpdateScreeningNotFound() {
        Long screeningId = 1L;
        Screening updatedScreening = new Screening();

        when(screeningRepository.findById(screeningId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> screeningService.updateScreening(screeningId, updatedScreening));

        verify(screeningRepository, times(1)).findById(screeningId);
        verify(screeningRepository, never()).save(any(Screening.class));
    }

    @Test
     void testDeleteScreening() {
        Long screeningId = 1L;

        screeningService.deleteScreening(screeningId);

        verify(screeningRepository, times(1)).deleteById(screeningId);
    }

    @Test
     void testSearchScreeningsByMovieTitle() {
        String title = "Test Movie";
        List<Screening> screenings = new ArrayList<>();
        screenings.add(new Screening());
        screenings.add(new Screening());

        when(screeningRepository.findByMovie_TitleContainingIgnoreCase(title)).thenReturn(screenings);

        List<Screening> foundScreenings = screeningService.searchScreeningsByMovieTitle(title);
        assertNotNull(foundScreenings);
        assertEquals(2, foundScreenings.size());

        verify(screeningRepository, times(1)).findByMovie_TitleContainingIgnoreCase(title);
    }

    @Test
     void testSearchScreeningsByGenre() {
        String genre = "Action";
        List<Screening> screenings = new ArrayList<>();
        screenings.add(new Screening());
        screenings.add(new Screening());

        when(screeningRepository.findByMovie_GenreContainingIgnoreCase(genre)).thenReturn(screenings);

        List<Screening> foundScreenings = screeningService.searchScreeningsByGenre(genre);
        assertNotNull(foundScreenings);
        assertEquals(2, foundScreenings.size());

        verify(screeningRepository, times(1)).findByMovie_GenreContainingIgnoreCase(genre);
    }

    @Test
     void testBookTicket() {
        Long screeningId = 1L;
        Screening screening = new Screening();
        screening.setId(screeningId);
        screening.setSeatsAvailable(10);

        when(screeningRepository.findById(screeningId)).thenReturn(Optional.of(screening));
        when(screeningRepository.save(screening)).thenReturn(screening);

        Screening bookedScreening = screeningService.bookTicket(screeningId);
        assertNotNull(bookedScreening);
        assertEquals(9, bookedScreening.getSeatsAvailable());

        verify(screeningRepository, times(1)).findById(screeningId);
        verify(screeningRepository, times(1)).save(screening);
    }

    @Test
     void testBookTicketScreeningFull() {
        Long screeningId = 1L;
        Screening screening = new Screening();
        screening.setId(screeningId);
        screening.setSeatsAvailable(0);

        when(screeningRepository.findById(screeningId)).thenReturn(Optional.of(screening));

        assertThrows(ScreeningFullException.class, () -> screeningService.bookTicket(screeningId));

        verify(screeningRepository, times(1)).findById(screeningId);
        verify(screeningRepository, never()).save(any(Screening.class));
    }
}

