package oth.ics.wtp.pollsgram.repositories;

import oth.ics.wtp.pollsgram.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    boolean existsByPollAndInvitee(Poll poll, User invitee);

    List<Invitation> findByInvitee(User invitee);

    List<Invitation> findByPoll(Poll poll);
}