package oth.ics.wtp.pollsgram.services;

import oth.ics.wtp.pollsgram.dtos.UserRegisterRequest;
import oth.ics.wtp.pollsgram.dtos.UserResponse;
import oth.ics.wtp.pollsgram.entities.User;
import oth.ics.wtp.pollsgram.exceptions.BadRequestException;
import oth.ics.wtp.pollsgram.exceptions.ResourceNotFoundException;
import oth.ics.wtp.pollsgram.mappers.UserMapper;
import oth.ics.wtp.pollsgram.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(UserRegisterRequest request) {
        validateRegistrationRequest(request);

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                List.of("USER")
        );

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    public User findEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponse findByUsername(String username) {
        User user = findEntityByUsername(username);
        return UserMapper.toResponse(user);
    }

    private void validateRegistrationRequest(UserRegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new BadRequestException("Username is required");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Password is required");
        }

        if (request.getPassword().length() < 4) {
            throw new BadRequestException("Password must contain at least 4 characters");
        }
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}