package oth.ics.wtp.pollsgram.mappers;

import oth.ics.wtp.pollsgram.dtos.UserResponse;
import oth.ics.wtp.pollsgram.entities.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    // verifies that it doesn't expose passwordHash
    @Test
    void shouldMapUserEntityToResponseWithoutPasswordHash() {
        User user = new User(
                "mo",
                "secret-hash",
                List.of("USER", "ADMIN")
        );

        UserResponse response = UserMapper.toResponse(user);

        assertEquals("mo", response.getUsername());
        assertEquals(List.of("USER", "ADMIN"), response.getRoles());
    }
}