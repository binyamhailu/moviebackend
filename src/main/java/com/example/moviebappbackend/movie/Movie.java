package com.example.moviebappbackend.movie;


import com.example.moviebappbackend.screening.Screening;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int duration;
    private String genre;
    private String description;
    private double rating;


}
