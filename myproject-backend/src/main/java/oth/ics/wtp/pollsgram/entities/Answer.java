package your.package.name.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "answers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"participation_id", "question_id"})
                // UniqueContraints means: the combination of these two columns must be unique
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

    // Poll owns its Question objects, when saving a poll, its questions can be saved with it.
    public void setQuestion(Question question) @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true){
        this.question = question;
    }

    //three value fields: String textValue;
    //Boolean booleanValue;
    //Integer numericValue;
    // Only one should be used depending on the question type. The service layer will validate that
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