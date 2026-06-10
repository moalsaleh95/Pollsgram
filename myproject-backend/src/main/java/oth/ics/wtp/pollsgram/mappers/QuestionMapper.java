package oth.ics.wtp.pollsgram.mappers;

import oth.ics.wtp.pollsgram.dtos.QuestionCreateRequest;
import oth.ics.wtp.pollsgram.dtos.QuestionResponse;
import oth.ics.wtp.pollsgram.entities.Question;

public class QuestionMapper {

    private QuestionMapper() {
    }

    public static Question toEntity(QuestionCreateRequest request) { // used when the frontend sends some poll data
        return new Question(
                request.getText(),
                request.getType()
        );
    }

    public static QuestionResponse toResponse(Question question) { // used when sending poll data back to the frontend
        return new QuestionResponse(
                question.getId(),
                question.getText(),
                question.getType()
        );
    }
}