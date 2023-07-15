package com.example.moviebappbackend.user;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String firstname;
    private String lastname;
    private Role role;

}

