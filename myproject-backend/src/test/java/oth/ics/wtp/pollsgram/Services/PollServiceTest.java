package oth.ics.wtp.pollsgram.services;

import oth.ics.wtp.pollsgram.dtos.PollCreateRequest;
import oth.ics.wtp.pollsgram.entities.User;
import oth.ics.wtp.pollsgram.exceptions.BadRequestException;
import oth.ics.wtp.pollsgram.repositories.PollRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PollServiceTest {

    @Mock
    private PollRepository pollRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PollService pollService;

    // Due date cannot be in the past
    @Test
    void shouldRejectPastDueDate() {

        PollCreateRequest request =
                new PollCreateRequest();

        request.setTitle("Lunch Survey");
        request.setDescription("Survey");

        request.setDueDate(
                LocalDate.now().minusDays(1)
        );

        BadRequestException exception =
                assertThrows(
                        BadRequestException.class,
                        () -> pollService.createPoll(
                                request,
                                "mo"
                        )
                );

        assertEquals(
                "Due date cannot be in the past",
                exception.getMessage()
        );
    }
}