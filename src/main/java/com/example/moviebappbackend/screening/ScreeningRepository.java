package com.example.moviebappbackend.screening;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening,Long> {
    List<Screening> findByMovie_TitleContainingIgnoreCase(String title);
    List<Screening> findByMovie_GenreContainingIgnoreCase(String genre);
}
