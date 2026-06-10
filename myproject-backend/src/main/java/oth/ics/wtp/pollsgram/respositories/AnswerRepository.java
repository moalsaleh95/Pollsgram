package oth.ics.wtp.pollsgram.repositories;

import oth.ics.wtp.pollsgram.entities.Answer;
import oth.ics.wtp.pollsgram.entities.Participation;
import oth.ics.wtp.pollsgram.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByParticipation(Participation participation); // returns the answers of a participation

    List<Answer> findByQuestion(Question question); // returns the answers of a question

    Optional<Answer> findByParticipationAndQuestion( // returns of both
            Participation participation,
            Question question
    );
}