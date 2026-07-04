package oth.ics.wtp.pollsgram.dtos;

public class AnswerResponse {

    private Long questionId;
    private String questionText;
    private String textValue;
    private Boolean booleanValue;
    private Integer numericValue;

    public AnswerResponse() {
    }

    public AnswerResponse(
            Long questionId,
            String questionText,
            String textValue,
            Boolean booleanValue,
            Integer numericValue
    ) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.textValue = textValue;
        this.booleanValue = booleanValue;
        this.numericValue = numericValue;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getTextValue() {
        return textValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public Integer getNumericValue() {
        return numericValue;
    }
}