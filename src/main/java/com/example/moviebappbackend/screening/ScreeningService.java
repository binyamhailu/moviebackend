package com.example.moviebappbackend.screening;

import java.util.List;

public interface ScreeningService {
    List<Screening> getAllScreenings();
    Screening getScreeningById(Long id);
    Screening createScreening(CreateScreeningDTO screeningDTO);
    Screening updateScreening(Long id, Screening screening);
    void deleteScreening(Long id);
    List<Screening> searchScreeningsByMovieTitle(String title);
    Screening bookTicket(Long screeningId);
    List<Screening> searchScreeningsByGenre(String genre);
}
