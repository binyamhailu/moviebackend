package com.example.moviebappbackend.screening;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }
    @GetMapping
    public List<Screening> getAllScreenings() {
        return screeningService.getAllScreenings();
    }
    @GetMapping("/{id}")
    public Screening getScreeningById(@PathVariable Long id) {
        return screeningService.getScreeningById(id);
    }
    @PostMapping
    public Screening createScreening(@RequestBody @Valid CreateScreeningDTO screening) {
        return screeningService.createScreening(screening);
    }
    @PutMapping("/{id}")
    public Screening updateScreening(@PathVariable Long id, @RequestBody Screening screening) {
        return screeningService.updateScreening(id, screening);
    }
    @DeleteMapping("/{id}")
    public void deleteScreening(@PathVariable Long id) {
        screeningService.deleteScreening(id);
    }
    @GetMapping("/search/title/{title}")
    public List<Screening> searchScreeningsByMovieTitle(@PathVariable String title) {
        return screeningService.searchScreeningsByMovieTitle(title);
    }
    @GetMapping("/search/genre/{genre}")
    public List<Screening> searchScreeningsByGenre(@PathVariable String genre) {
        return screeningService.searchScreeningsByGenre(genre);
    }
    @PostMapping("/{id}/book")
    public Screening bookTicket(@PathVariable Long id) {
        return screeningService.bookTicket(id);
    }
}
