package oth.ics.wtp.pollsgram.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class ParticipationResponse {

    private Long id;
    private String participantUsername;
    private LocalDateTime submittedAt;
    private List<AnswerResponse> answers;

    public ParticipationResponse() {
    }

    public ParticipationResponse(
            Long id,
            String participantUsername,
            LocalDateTime submittedAt,
            List<AnswerResponse> answers
    ) {
        this.id = id;
        this.participantUsername = participantUsername;
        this.submittedAt = submittedAt;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public String getParticipantUsername() {
        return participantUsername;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public List<AnswerResponse> getAnswers() {
        return answers;
    }
}