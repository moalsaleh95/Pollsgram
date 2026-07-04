package oth.ics.wtp.pollsgram.controllers;

import oth.ics.wtp.pollsgram.dtos.UserRegisterRequest;
import oth.ics.wtp.pollsgram.dtos.UserResponse;
import oth.ics.wtp.pollsgram.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRegisterRequest request) {
        return userService.register(request);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers();
    }

}