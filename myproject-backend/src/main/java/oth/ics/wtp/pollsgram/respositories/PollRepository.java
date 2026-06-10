package oth.ics.wtp.pollsgram.repositories;

import oth.ics.wtp.pollsgram.entities.Poll;
import oth.ics.wtp.pollsgram.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PollRepository extends JpaRepository<Poll, Long> {

    boolean existsByTitle(String title); // no two polls may have the same title

    List<Poll> findByCreator(User creator); // find all pulls created by a user

    List<Poll> findByFinishedFalse(); // only active polls
}