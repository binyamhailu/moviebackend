package com.example.moviebappbackend.movie;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "title  cannot be blank")
    private String title;
    @NotNull(message = "duration cannot be null")
    private int duration;
    @NotBlank(message = "Genre  cannot be blank")
    private String genre;
    @NotBlank(message = "description  cannot be blank")
    private String description;
    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating should not be less than 1")
    @Max(value = 10, message = "Rating should not be greater than 10")
    private double rating;

}
