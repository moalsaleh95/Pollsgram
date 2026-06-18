package oth.ics.wtp.pollsgram.controllers;

import oth.ics.wtp.pollsgram.dtos.AuthResponse;
import oth.ics.wtp.pollsgram.entities.User;
import oth.ics.wtp.pollsgram.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public AuthResponse me(Principal principal) {
        User user = userService.findEntityByUsername(principal.getName());

        return new AuthResponse(
                user.getUsername(),
                user.getRoles(),
                true
        );
    }
}