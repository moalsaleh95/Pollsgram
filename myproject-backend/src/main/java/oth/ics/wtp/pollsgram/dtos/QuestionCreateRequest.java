package oth.ics.wtp.pollsgram.dtos;

import oth.ics.wtp.pollsgram.entities.QuestionType;

public class QuestionCreateRequest {

    private String text;
    private QuestionType type;

    public QuestionCreateRequest() {
    }

    public String getText() {
        return text;
    }

    public QuestionType getType() {
        return type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }
}