package oth.ics.wtp.pollsgram.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type;

    @ManyToOne(optional = false)
    private Poll poll;

    public Question() {
    }

    public Question(String text, QuestionType type) {
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

    public Poll getPoll() {
        return poll;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}