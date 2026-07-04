package oth.ics.wtp.pollsgram.dtos;

import oth.ics.wtp.pollsgram.entities.QuestionType;

import java.util.List;

public class QuestionResultResponse {

    private Long questionId;
    private String questionText;
    private QuestionType questionType;

    private Long trueCount;
    private Long falseCount;

    private Double averageValue;

    private List<String> textAnswers;

    public QuestionResultResponse() {
    }

    public QuestionResultResponse(
            Long questionId,
            String questionText,
            QuestionType questionType,
            Long trueCount,
            Long falseCount,
            Double averageValue,
            List<String> textAnswers
    ) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.trueCount = trueCount;
        this.falseCount = falseCount;
        this.averageValue = averageValue;
        this.textAnswers = textAnswers;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public Long getTrueCount() {
        return trueCount;
    }

    public Long getFalseCount() {
        return falseCount;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public List<String> getTextAnswers() {
        return textAnswers;
    }
}