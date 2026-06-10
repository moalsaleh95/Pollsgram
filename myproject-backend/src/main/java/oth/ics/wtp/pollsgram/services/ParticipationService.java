package oth.ics.wtp.pollsgram.services;

import oth.ics.wtp.pollsgram.repositories.ParticipationRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    public ParticipationService(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }
}