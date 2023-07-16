package com.example.moviebappbackend.screening;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateScreeningDTO {
    @NotBlank(message = "movieTitle  cannot be blank")
    private String movieTitle;
    @NotBlank(message = "seatsAvailable  cannot be blank")
    private String screen;
    @NotNull(message = "seatsAvailable cannot be null")
    @Min(value = 10 , message = "seatsAvailable should not be less than 1")
    private int seatsAvailable;
    @NotNull(message = "dateAndTime cannot be null")
    private LocalDateTime dateAndTime;

    // Getters and setters (or lombok annotations)
}

