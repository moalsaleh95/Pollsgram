package oth.ics.wtp.pollsgram.mappers;

import oth.ics.wtp.pollsgram.dtos.QuestionCreateRequest;
import oth.ics.wtp.pollsgram.dtos.QuestionResponse;
import oth.ics.wtp.pollsgram.entities.Question;
import oth.ics.wtp.pollsgram.entities.QuestionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionMapperTest {

    @Test
    void shouldMapQuestionCreateRequestToEntity() {
        QuestionCreateRequest request = new QuestionCreateRequest();
        request.setText("Was the food good?");
        request.setType(QuestionType.BOOLEAN);

        Question question = QuestionMapper.toEntity(request);

        assertEquals("Was the food good?", question.getText());
        assertEquals(QuestionType.BOOLEAN, question.getType());
    }

    // checks both directions:
    // QuestionCreateRequest -> Question
    // Question -> QuestionResponse
    @Test
    void shouldMapQuestionEntityToResponse() {
        Question question = new Question(
                "Rate the food",
                QuestionType.NUMBER
        );

        QuestionResponse response = QuestionMapper.toResponse(question);

        assertEquals("Rate the food", response.getText());
        assertEquals(QuestionType.NUMBER, response.getType());
    }
}