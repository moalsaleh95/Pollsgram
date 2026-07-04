package oth.ics.wtp.pollsgram.services;

import oth.ics.wtp.pollsgram.dtos.InvitationRequest;
import oth.ics.wtp.pollsgram.entities.Poll;
import oth.ics.wtp.pollsgram.entities.User;
import oth.ics.wtp.pollsgram.exceptions.BadRequestException;
import oth.ics.wtp.pollsgram.repositories.InvitationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvitationServiceTest {

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private PollService pollService;

    @Mock
    private UserService userService;

    @InjectMocks
    private InvitationService invitationService;

    // because creator and invitee are the same user, the service should reject the request
    @Test
    void shouldRejectInvitingYourself() {
        User creator = new User(
                "mo",
                "hashed-password",
                List.of("USER")
        );

        Poll poll = new Poll(
                "Lunch Survey",
                "Feedback",
                LocalDate.now().plusDays(7),
                creator
        );

        InvitationRequest request = new InvitationRequest();
        request.setUsername("mo");

        when(pollService.findEntityById(1L)).thenReturn(poll);
        when(userService.findEntityByUsername("mo")).thenReturn(creator);

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> invitationService.inviteUser(1L, request, "mo")
        );

        assertEquals(
                "You cannot invite yourself",
                exception.getMessage()
        );
    }
}