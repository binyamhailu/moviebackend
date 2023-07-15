package com.example.moviebappbackend.screening;

import com.example.moviebappbackend.movie.Movie;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Movie movie;

    private String screen;
    private int seatsAvailable;
    private LocalDateTime dateAndTime;

}

