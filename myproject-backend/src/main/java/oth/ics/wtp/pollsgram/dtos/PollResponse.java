package oth.ics.wtp.pollsgram.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PollResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean finished;
    private String creatorUsername;
    private List<QuestionResponse> questions = new ArrayList<>();

    public PollResponse() {
    }

    public PollResponse(
            Long id,
            String title,
            String description,
            LocalDate dueDate,
            boolean finished,
            String creatorUsername,
            List<QuestionResponse> questions
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.finished = finished;
        this.creatorUsername = creatorUsername;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public List<QuestionResponse> getQuestions() {
        return questions;
    }
}