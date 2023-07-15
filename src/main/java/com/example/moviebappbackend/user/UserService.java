package com.example.moviebappbackend.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + id + " not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, UserUpdateDTO updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + id + " not found"));

        // Update the user attributes from the DTO
        if (updatedUser.getFirstname() != null) {
            user.setFirstname(updatedUser.getFirstname());
        }
        if (updatedUser.getLastname() != null) {
            user.setLastname(updatedUser.getLastname());
        }
        if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

