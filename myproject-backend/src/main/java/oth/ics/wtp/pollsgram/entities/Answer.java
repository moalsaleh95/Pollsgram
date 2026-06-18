package oth.ics.wtp.pollsgram.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "answers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"participation_id", "question_id"})
        }
)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Participation participation;

    @ManyToOne(optional = false)
    private Question question;

    @Column(length = 2000)
    private String textValue;

    private Boolean booleanValue;

    private Integer numericValue;

    public Answer() {
    }

    public Answer(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public Participation getParticipation() {
        return participation;
    }

    public Question getQuestion() {
        return question;
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

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }

    public void setQuestion(Question question) {
        this.question = question;
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