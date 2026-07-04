package oth.ics.wtp.pollsgram.services;

import oth.ics.wtp.pollsgram.dtos.UserRegisterRequest;
import oth.ics.wtp.pollsgram.dtos.UserResponse;
import oth.ics.wtp.pollsgram.entities.User;
import oth.ics.wtp.pollsgram.exceptions.BadRequestException;
import oth.ics.wtp.pollsgram.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    // username already exists --> do not save user
    @Test
    void shouldRejectDuplicateUsername() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("mo");
        request.setPassword("1234");

        when(userRepository.existsByUsername("mo")).thenReturn(true);

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.register(request)
        );

        assertEquals("Username already exists", exception.getMessage());

        verify(userRepository, never()).save(any());
    }

    // new username --> password gets encoded --> user is saved --> response contains username and roles
    @Test
    void shouldRegisterUserWithEncodedPassword() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("mo");
        request.setPassword("1234");

        User savedUser = new User(
                "mo",
                "encoded-password",
                List.of("USER")
        );

        when(userRepository.existsByUsername("mo")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponse response = userService.register(request);

        assertEquals("mo", response.getUsername());
        assertEquals(List.of("USER"), response.getRoles());

        verify(passwordEncoder).encode("1234");
        verify(userRepository).save(any(User.class));
    }
}