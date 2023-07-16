package com.example.moviebappbackend.auth;


import com.example.moviebappbackend.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

  @NotBlank(message = "firstname name cannot be blank")
  private String firstname;
  @NotBlank(message = "lastname name cannot be blank")
  private String lastname;

  @NotBlank(message = "email name cannot be blank")
  @Email(message = "email must be in a valid email format")
  private String email;
  @Size(min = 6, max = 512, message = "password  must be between 6-512 characters")
  private String password;

  private Role role;
}
