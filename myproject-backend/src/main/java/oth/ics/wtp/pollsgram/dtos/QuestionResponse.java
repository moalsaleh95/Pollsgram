package oth.ics.wtp.pollsgram.dtos;

import oth.ics.wtp.pollsgram.entities.QuestionType;

public class QuestionResponse {

    private Long id;
    private String text;
    private QuestionType type;

    public QuestionResponse() {
    }

    public QuestionResponse(Long id, String text, QuestionType type) {
        this.id = id;
        this.text = text;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public QuestionType getType() {
        return type;
    }
}