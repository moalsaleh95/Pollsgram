package oth.ics.wtp.pollsgram.services;

import oth.ics.wtp.pollsgram.dtos.PollSubmissionRequest;
import oth.ics.wtp.pollsgram.entities.Poll;
import oth.ics.wtp.pollsgram.entities.User;
import oth.ics.wtp.pollsgram.exceptions.BadRequestException;
import oth.ics.wtp.pollsgram.repositories.InvitationRepository;
import oth.ics.wtp.pollsgram.repositories.ParticipationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import oth.ics.wtp.pollsgram.exceptions.ForbiddenException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private PollService pollService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ParticipationService participationService;

    // A user should not answer the same poll twice
    @Test
    void shouldRejectDuplicateSubmission() {
        User creator = new User("creator", "hashed", List.of("USER"));
        User participant = new User("alice", "hashed", List.of("USER"));

        Poll poll = new Poll(
                "Lunch Survey",
                "Feedback",
                LocalDate.now().plusDays(7),
                creator
        );

        PollSubmissionRequest request = new PollSubmissionRequest();
        request.setAnswers(new ArrayList<>());

        when(pollService.findEntityById(1L)).thenReturn(poll);
        when(userService.findEntityByUsername("alice")).thenReturn(participant);
        when(invitationRepository.existsByPollAndInvitee(poll, participant)).thenReturn(true);
        when(participationRepository.existsByPollAndParticipant(poll, participant)).thenReturn(true);

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> participationService.submitPoll(1L, request, "alice")
        );

        assertEquals(
                "You have already submitted this poll",
                exception.getMessage()
        );
    }

    // Only invited users may submit answers
    @Test
    void shouldRejectSubmissionWhenUserIsNotInvited() {
        User creator = new User("creator", "hashed", List.of("USER"));
        User participant = new User("alice", "hashed", List.of("USER"));

        Poll poll = new Poll(
                "Lunch Survey",
                "Feedback",
                LocalDate.now().plusDays(7),
                creator
        );

        PollSubmissionRequest request = new PollSubmissionRequest();
        request.setAnswers(new ArrayList<>());

        when(pollService.findEntityById(1L)).thenReturn(poll);
        when(userService.findEntityByUsername("alice")).thenReturn(participant);
        when(invitationRepository.existsByPollAndInvitee(poll, participant)).thenReturn(false);

        ForbiddenException exception = assertThrows(
                ForbiddenException.class,
                () -> participationService.submitPoll(1L, request, "alice")
        );

        assertEquals(
                "You are not invited to this poll",
                exception.getMessage()
        );
    }
}