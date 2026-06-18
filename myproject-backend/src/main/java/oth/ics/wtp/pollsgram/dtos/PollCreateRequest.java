package oth.ics.wtp.pollsgram.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PollCreateRequest {

    private String title;
    private String description;
    private LocalDate dueDate;
    private List<QuestionCreateRequest> questions = new ArrayList<>();

    public PollCreateRequest() {
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

    public List<QuestionCreateRequest> getQuestions() {
        return questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setQuestions(List<QuestionCreateRequest> questions) {
        this.questions = questions;
    }
}