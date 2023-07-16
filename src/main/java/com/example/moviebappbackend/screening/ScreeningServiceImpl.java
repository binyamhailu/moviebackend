package com.example.moviebappbackend.screening;

import com.example.moviebappbackend.exception.ScreeningFullException;
import com.example.moviebappbackend.movie.Movie;
import com.example.moviebappbackend.movie.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    @Override
    public Screening getScreeningById(Long id) {
        return screeningRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Screening not found"));
    }
    public Screening createScreening(CreateScreeningDTO screeningDTO) {
        Movie movie = movieRepository.findByTitle(screeningDTO.getMovieTitle());

        if (movie == null) {
            // Throw EntityNotFoundException when the Movie is not found
            throw new EntityNotFoundException("Movie with title '" + screeningDTO.getMovieTitle() + "' not found.");
        }
        Screening newScreening = new Screening();
        newScreening.setMovie(movie);
        newScreening.setScreen(screeningDTO.getScreen());
        newScreening.setSeatsAvailable(screeningDTO.getSeatsAvailable());
        newScreening.setDateAndTime(screeningDTO.getDateAndTime());

        return screeningRepository.save(newScreening);
    }


    @Override
    public Screening updateScreening(Long id, Screening screening) {
        Screening existingScreening = getScreeningById(id);

        existingScreening.setMovie(screening.getMovie());
        existingScreening.setScreen(screening.getScreen());
        existingScreening.setSeatsAvailable(screening.getSeatsAvailable());
        existingScreening.setDateAndTime(screening.getDateAndTime());

        return screeningRepository.save(existingScreening);
    }

    @Override
    public void deleteScreening(Long id) {
        screeningRepository.deleteById(id);
    }

    @Override
    public List<Screening> searchScreeningsByMovieTitle(String title) {
        return screeningRepository.findByMovie_TitleContainingIgnoreCase(title);
    }

    @Override
    public List<Screening> searchScreeningsByGenre(String genre) {
        return screeningRepository.findByMovie_GenreContainingIgnoreCase(genre);
    }
    @Override
    public Screening bookTicket(Long screeningId) {
        Screening screening = getScreeningById(screeningId);

        if (screening.getSeatsAvailable() > 0) {
            screening.setSeatsAvailable(screening.getSeatsAvailable() - 1);
        } else {
            throw new ScreeningFullException("No seats available for booking. Please Try again Later!");
        }
        return screeningRepository.save(screening);
    }
}
