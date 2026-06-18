package oth.ics.wtp.pollsgram.repositories;

import oth.ics.wtp.pollsgram.entities.Participation;
import oth.ics.wtp.pollsgram.entities.Poll;
import oth.ics.wtp.pollsgram.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    boolean existsByPollAndParticipant(Poll poll, User participant); // a user can answer a poll only once

    Optional<Participation> findByPollAndParticipant(Poll poll, User participant); // to find the participation record for a specific user and poll
    // Optional: Prevents NullPointerExceptions, forces you to explicitly check if data exists before using it

    List<Participation> findByPoll(Poll poll); // returns all responses for a poll

    List<Participation> findByParticipant(User participant); // returns participation history for a user
}