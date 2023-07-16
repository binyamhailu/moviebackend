package com.example.moviebappbackend;

import com.example.moviebappbackend.user.*;
import com.example.moviebappbackend.user.token.TokenRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("John", createdUser.getFirstname());
        assertEquals("Doe", createdUser.getLastname());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertEquals("password", createdUser.getPassword());
        assertEquals(Role.USER, createdUser.getRole());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(userId);
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("John", foundUser.getFirstname());
        assertEquals("Doe", foundUser.getLastname());
        assertEquals("john.doe@example.com", foundUser.getEmail());
        assertEquals("password", foundUser.getPassword());
        assertEquals(Role.USER, foundUser.getRole());

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserByIdNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> allUsers = userService.getAllUsers();
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        UserUpdateDTO updatedUser = new UserUpdateDTO();
        updatedUser.setFirstname("Updated Firstname");
        updatedUser.setLastname("Updated Lastname");
        updatedUser.setRole(Role.ADMIN);

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstname("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("password");
        existingUser.setRole(Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updated = userService.updateUser(userId, updatedUser);

        assertNotNull(updated);
        assertEquals(userId, updated.getId());
        assertEquals("Updated Firstname", updated.getFirstname());
        assertEquals("Updated Lastname", updated.getLastname());
        assertEquals("john.doe@example.com", updated.getEmail());
        assertEquals("password", updated.getPassword());
        assertEquals(Role.ADMIN, updated.getRole());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUserNotFound() {
        Long userId = 1L;
        UserUpdateDTO updatedUser = new UserUpdateDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateUser(userId, updatedUser));

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstname("John");
        existingUser.setLastname("Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("password");
        existingUser.setRole(Role.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(userId);

        verify(tokenRepository, times(1)).deleteAllByUser(existingUser);
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    public void testDeleteUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(tokenRepository, never()).deleteAllByUser(any(User.class));
        verify(userRepository, never()).delete(any(User.class));
    }
}

