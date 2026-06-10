package oth.ics.wtp.pollsgram.mappers;

import oth.ics.wtp.pollsgram.dtos.UserResponse;
import oth.ics.wtp.pollsgram.entities.User;

public class UserMapper {

    private UserMapper() {
    }

    // entity to response
    public static UserResponse toResponse(User user) { // converts User into UserResponse
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles()
        );
    }
}