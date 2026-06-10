package your.package.name.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polls")
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean finished = false;

    @ManyToOne(optional = false) // optional false means every poll must have a creator
    private User creator; // Many polls can belong to one user

    @OneToMany(mappedBy = "poll",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public Poll() {
    }

    public Poll(String title, String description, LocalDate dueDate, User creator) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.creator = creator;
    }

    public Long getId() {
        return id;
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

    public boolean isFinished() {
        return finished;
    }

    public User getCreator() {
        return creator;
    }

    public List<Question> getQuestions() {
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

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void addQuestion(Question question) {
        questions.add(question);
        question.setPoll(this);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setPoll(null);
    }
}