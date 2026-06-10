package your.package.name.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "participations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"poll_id", "participant_id"})
        }
)
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Poll poll;

    @ManyToOne(optional = false)
    private User participant;

    private LocalDateTime submittedAt;

    // Participation owns its Answer objects, when a user submits a participation, its answers can be saved together.
    @OneToMany(mappedBy = "participation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    public Participation() {
    }

    public Participation(Poll poll, User participant) {
        this.poll = poll;
        this.participant = participant;
        this.submittedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Poll getPoll() {
        return poll;
    }

    public User getParticipant() {
        return participant;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public void setParticipant(User participant) {
        this.participant = participant;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setParticipation(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setParticipation(null);
    }
}