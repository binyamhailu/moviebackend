package com.example.moviebappbackend.movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Movie findByTitle(String title);
}
