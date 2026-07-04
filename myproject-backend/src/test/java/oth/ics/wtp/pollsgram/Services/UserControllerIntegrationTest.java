package oth.ics.wtp.pollsgram.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import oth.ics.wtp.pollsgram.dtos.UserRegisterRequest;
import oth.ics.wtp.pollsgram.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import oth.ics.wtp.pollsgram.repositories.ParticipationRepository;
import oth.ics.wtp.pollsgram.repositories.InvitationRepository;
import oth.ics.wtp.pollsgram.repositories.PollRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private PollRepository pollRepository;

    @BeforeEach
    void cleanDatabase() {
        participationRepository.deleteAll();
        invitationRepository.deleteAll();
        pollRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setUsername("mo");
        request.setPassword("1234");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("mo"))
                .andExpect(jsonPath("$.roles[0]").value("USER"));
    }
}