package oth.ics.wtp.pollsgram.mappers;

import oth.ics.wtp.pollsgram.dtos.PollResponse;
import oth.ics.wtp.pollsgram.dtos.QuestionResponse;
import oth.ics.wtp.pollsgram.entities.Poll;

import java.util.List;

public class PollMapper {

    private PollMapper() {
    }

    public static PollResponse toResponse(Poll poll) { // converts Poll into a PollResponse
        List<QuestionResponse> questionResponses = poll.getQuestions()
                .stream()
                .map(QuestionMapper::toResponse)
                .toList();

        return new PollResponse(
                poll.getId(),
                poll.getTitle(),
                poll.getDescription(),
                poll.getDueDate(),
                poll.isFinished(),
                poll.getCreator().getUsername(),
                questionResponses
        );
    }
}