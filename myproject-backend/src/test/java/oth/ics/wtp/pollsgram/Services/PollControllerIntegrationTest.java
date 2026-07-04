package oth.ics.wtp.pollsgram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import oth.ics.wtp.pollsgram.dtos.PollCreateRequest;
import oth.ics.wtp.pollsgram.dtos.QuestionCreateRequest;
import oth.ics.wtp.pollsgram.entities.QuestionType;
import oth.ics.wtp.pollsgram.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import oth.ics.wtp.pollsgram.entities.User;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PollControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void cleanDatabase() {
        answerRepository.deleteAll();
        participationRepository.deleteAll();
        invitationRepository.deleteAll();
        pollRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreatePollForAuthenticatedUser() throws Exception {
        User user = new User(
                "mo",
                passwordEncoder.encode("1234"),
                List.of("USER")
        );

        userRepository.save(user);

        QuestionCreateRequest question = new QuestionCreateRequest();
        question.setText("Was the food good?");
        question.setType(QuestionType.BOOLEAN);

        PollCreateRequest request = new PollCreateRequest();
        request.setTitle("Lunch Survey");
        request.setDescription("Weekly cafeteria feedback");
        request.setDueDate(LocalDate.now().plusDays(7));
        request.setQuestions(List.of(question));

        mockMvc.perform(post("/api/polls")
                        .with(httpBasic("mo", "1234"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Lunch Survey"))
                .andExpect(jsonPath("$.creatorUsername").value("mo"))
                .andExpect(jsonPath("$.questions[0].text").value("Was the food good?"))
                .andExpect(jsonPath("$.questions[0].type").value("BOOLEAN"));
    }
}