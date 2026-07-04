package oth.ics.wtp.pollsgram.dtos;

import java.util.ArrayList;
import java.util.List;

public class PollSubmissionRequest {

    private List<AnswerSubmissionRequest> answers = new ArrayList<>();

    public PollSubmissionRequest() {
    }

    public List<AnswerSubmissionRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerSubmissionRequest> answers) {
        this.answers = answers;
    }
}