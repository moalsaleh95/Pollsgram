package oth.ics.wtp.pollsgram.dtos;

import java.time.LocalDate;

public class PollUpdateRequest {

    private String title;
    private String description;
    private LocalDate dueDate;

    public PollUpdateRequest() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}