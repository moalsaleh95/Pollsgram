package oth.ics.wtp.pollsgram.services;

import oth.ics.wtp.pollsgram.repositories.PollRepository;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }
}