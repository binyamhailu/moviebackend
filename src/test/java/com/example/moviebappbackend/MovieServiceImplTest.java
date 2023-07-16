package com.example.moviebappbackend;

import com.example.moviebappbackend.movie.Movie;
import com.example.moviebappbackend.movie.MovieRepository;
import com.example.moviebappbackend.movie.MovieServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie());
        movies.add(new Movie());

        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> allMovies = movieService.getAllMovies();
        assertNotNull(allMovies);
        assertEquals(2, allMovies.size());

        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void testGetMovieById() {
        Long movieId = 1L;
        Movie movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("Test Movie");
        movie.setDuration(120);
        movie.setGenre("Action");
        movie.setDescription("A test movie");
        movie.setRating(4.5);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Movie foundMovie = movieService.getMovieById(movieId);
        assertNotNull(foundMovie);
        assertEquals(movieId, foundMovie.getId());
        assertEquals("Test Movie", foundMovie.getTitle());
        assertEquals(120, foundMovie.getDuration());
        assertEquals("Action", foundMovie.getGenre());
        assertEquals("A test movie", foundMovie.getDescription());
        assertEquals(4.5, foundMovie.getRating());

        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    void testGetMovieByIdNotFound() {
        Long movieId = 1L;

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> movieService.getMovieById(movieId));

        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    void testCreateMovie() {
        Movie movie = new Movie();
        movie.setTitle("Test Movie");
        movie.setDuration(120);
        movie.setGenre("Action");
        movie.setDescription("A test movie");
        movie.setRating(4.5);

        when(movieRepository.save(movie)).thenReturn(movie);

        Movie createdMovie = movieService.createMovie(movie);
        assertNotNull(createdMovie);
        assertEquals("Test Movie", createdMovie.getTitle());
        assertEquals(120, createdMovie.getDuration());
        assertEquals("Action", createdMovie.getGenre());
        assertEquals("A test movie", createdMovie.getDescription());
        assertEquals(4.5, createdMovie.getRating());

        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void testUpdateMovie() {
        Long movieId = 1L;
        Movie updatedMovie = new Movie();
        updatedMovie.setTitle("Updated Movie");
        updatedMovie.setDuration(150);
        updatedMovie.setGenre("Adventure");
        updatedMovie.setDescription("An updated movie");
        updatedMovie.setRating(4.8);

        Movie existingMovie = new Movie();
        existingMovie.setId(movieId);
        existingMovie.setTitle("Test Movie");
        existingMovie.setDuration(120);
        existingMovie.setGenre("Action");
        existingMovie.setDescription("A test movie");
        existingMovie.setRating(4.5);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(existingMovie)).thenReturn(existingMovie);

        Movie updated = movieService.updateMovie(movieId, updatedMovie);

        assertNotNull(updated);
        assertEquals(movieId, updated.getId());
        assertEquals("Updated Movie", updated.getTitle());
        assertEquals(150, updated.getDuration());
        assertEquals("Adventure", updated.getGenre());
        assertEquals("An updated movie", updated.getDescription());
        assertEquals(4.8, updated.getRating());

        verify(movieRepository, times(1)).findById(movieId);
        verify(movieRepository, times(1)).save(existingMovie);
    }

    @Test
    public void testUpdateMovieNotFound() {
        Long movieId = 1L;
        Movie updatedMovie = new Movie();

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> movieService.updateMovie(movieId, updatedMovie));

        verify(movieRepository, times(1)).findById(movieId);
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    public void testDeleteMovie() {
        Long movieId = 1L;

        movieService.deleteMovie(movieId);

        verify(movieRepository, times(1)).deleteById(movieId);
    }
}

