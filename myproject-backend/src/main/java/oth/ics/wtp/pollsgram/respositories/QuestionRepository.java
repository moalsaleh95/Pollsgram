package oth.ics.wtp.pollsgram.repositories;

import oth.ics.wtp.pollsgram.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByPollId(Long pollId);
}