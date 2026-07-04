package oth.ics.wtp.pollsgram.dtos;

public class AnswerSubmissionRequest {

    private Long questionId;
    private String textValue;
    private Boolean booleanValue;
    private Integer numericValue;

    public AnswerSubmissionRequest() {
    }

    public Long getQuestionId() {
        return questionId;
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

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public void setNumericValue(Integer numericValue) {
        this.numericValue = numericValue;
    }
}