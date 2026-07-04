package oth.ics.wtp.pollsgram.mappers;

import oth.ics.wtp.pollsgram.dtos.PollResponse;
import oth.ics.wtp.pollsgram.entities.Poll;
import oth.ics.wtp.pollsgram.entities.Question;
import oth.ics.wtp.pollsgram.entities.QuestionType;
import oth.ics.wtp.pollsgram.entities.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PollMapperTest {
    // checks that PollMapper maps:
    // Poll title, Poll description, finished flag, creator username, questions list
    // into PollResponse.
    @Test
    void shouldMapPollEntityToResponse() {
        User creator = new User(
                "mo",
                "hashed-password",
                List.of("USER")
        );

        Poll poll = new Poll(
                "Lunch Survey",
                "Weekly cafeteria feedback",
                LocalDate.now().plusDays(7),
                creator
        );

        poll.addQuestion(new Question("Was the food good?", QuestionType.BOOLEAN));
        poll.addQuestion(new Question("Rate the food", QuestionType.NUMBER));

        PollResponse response = PollMapper.toResponse(poll);

        assertEquals("Lunch Survey", response.getTitle());
        assertEquals("Weekly cafeteria feedback", response.getDescription());
        assertFalse(response.isFinished());
        assertEquals("mo", response.getCreatorUsername());

        assertEquals(2, response.getQuestions().size());
        assertEquals("Was the food good?", response.getQuestions().get(0).getText());
        assertEquals(QuestionType.BOOLEAN, response.getQuestions().get(0).getType());
        assertEquals("Rate the food", response.getQuestions().get(1).getText());
        assertEquals(QuestionType.NUMBER, response.getQuestions().get(1).getType());
    }
}