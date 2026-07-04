package oth.ics.wtp.pollsgram.entities;

import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"poll_id", "invitee_id"}
                )
        }
)
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Poll poll;

    @ManyToOne(optional = false)
    private User invitee;

    public Invitation() {
    }

    public Invitation(Poll poll, User invitee) {
        this.poll = poll;
        this.invitee = invitee;
    }

    public Long getId() {
        return id;
    }

    public Poll getPoll() {
        return poll;
    }

    public User getInvitee() {
        return invitee;
    }
}